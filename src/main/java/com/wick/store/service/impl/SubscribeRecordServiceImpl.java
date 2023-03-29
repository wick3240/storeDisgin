package com.wick.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.Dto.CreateSubscribeRecordDto;
import com.wick.store.domain.Dto.SubscribeRecordRequstDto;
import com.wick.store.domain.Dto.SubscribeWorkflowApproveDto;
import com.wick.store.domain.Dto.WorkflowHandleNodeDto;
import com.wick.store.domain.entity.PublishWorkflowApproveEntity;
import com.wick.store.domain.entity.SubscribeApproveEntity;
import com.wick.store.domain.entity.SubscribeWorkflowApproveEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.SubscribeRecordAndProductVO;
import com.wick.store.domain.vo.SubscribeRecordVO;
import com.wick.store.repository.ProductCategoryMapper;
import com.wick.store.repository.SubscribeApproveMapper;
import com.wick.store.repository.SubscribeWorkflowApproveMapper;
import com.wick.store.service.SubscribeRecordService;
import com.wick.store.util.WorkflowJsonListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubscribeRecordServiceImpl extends ServiceImpl<SubscribeApproveMapper, SubscribeApproveEntity>
        implements SubscribeRecordService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private SubscribeApproveMapper subscribeApproveMapper;
    @Autowired
    private SubscribeWorkflowApproveMapper subscribeWorkflowApproveMapper;
    @Override
    public PageVO<SubscribeRecordAndProductVO> selectPageBySubscribeUser(SubscribeRecordRequstDto requstDto) {
        log.info("进入产品订阅信息分页查询逻辑 selectPageData, param==={}", JSON.toJSONString(requstDto));
        PageVO<SubscribeRecordAndProductVO> data = new PageVO();
        int current = requstDto.getPage() == null ? 1 : requstDto.getPage();
        int size = requstDto.getRow() == null ? 10 : requstDto.getRow();
        Page<SubscribeRecordVO> page = new Page<>(current, size);
        Page<SubscribeRecordAndProductVO> pages=subscribeApproveMapper.selectPageData(page,requstDto);
        data.setList(pages.getRecords());
        data.setPage(pages.getCurrent());
        data.setPageSize(pages.getSize());
        data.setTotal(pages.getTotal());
        return data;
    }

    @Override
    public PageVO<SubscribeRecordAndProductVO> selectApprovePageData(SubscribeRecordRequstDto requstDto) {
        log.info("进入审批订阅信息分页查询逻辑 selectApprovePageData, param==={}", JSON.toJSONString(requstDto));
        PageVO<SubscribeRecordAndProductVO> data = new PageVO();
        int current = requstDto.getPage() == null ? 1 : requstDto.getPage();
        int size = requstDto.getRow() == null ? 10 : requstDto.getRow();
        Page<SubscribeRecordVO> page = new Page<>(current, size);
        List<Integer> nodeStatusList = new ArrayList<>();
        if (requstDto.getApproveStatusList().size()==1){
            int queryStatus = requstDto.getApproveStatusList().get(0);
            if (queryStatus==0){
                nodeStatusList.add(0);
            }else if (queryStatus==1){
                requstDto.getApproveStatusList().add(0);
                nodeStatusList.add(1);
            }else if (queryStatus==2){
                nodeStatusList.add(0);
                nodeStatusList.add(1);
                nodeStatusList.add(2);
            }
        } else {
            nodeStatusList.add(0);
            nodeStatusList.add(1);
            nodeStatusList.add(2);
        }

        Page<SubscribeRecordAndProductVO> pages=subscribeApproveMapper.selectApproveNodePageData(page,requstDto.getApprover(),requstDto.getApproveStatusList()
                ,nodeStatusList,requstDto);
        data.setList(pages.getRecords());
        data.setPage(pages.getCurrent());
        data.setPageSize(pages.getSize());
        data.setTotal(pages.getTotal());
        return data;
    }

    @Override
    public void workflowUpdateApprove(SubscribeWorkflowApproveDto subscribeWorkflowApproveDto) {
        log.info("进入批量审批逻辑=====param{}::", JSON.toJSONString(subscribeWorkflowApproveDto));

        int nodeHadApproveCount = 0; // 已经被approve的node个数
        int nodeHadRejectCount = 0;  // 已经被reject的node个数
        int nodeHandledCount = 0;  //已经approve的node个数
        int wfHandleFinish = 0;    //已经审核的node的个数

        subscribeWorkflowApproveDto.setApprovalTime(new Date());
        for (SubscribeWorkflowApproveDto.subscribeApproveNode subscribeApproveNode : subscribeWorkflowApproveDto.getSubscribeApproveNodes()
        ) {

            String subscribeCode = subscribeApproveNode.getSubCode();
            String nodeId = subscribeApproveNode.getWfNodeId();
            String cid = subscribeApproveNode.getCid();
            Date approvalTime = subscribeWorkflowApproveDto.getApprovalTime();
            String approveUser = subscribeWorkflowApproveDto.getNodeApprover();
            // get wf_node_status
            List<SubscribeWorkflowApproveEntity> handledNodeList = subscribeWorkflowApproveMapper.selectBySubCodesAndNodeId(subscribeCode);
            log.info("查看数据====>" + handledNodeList);

            // 通过subscirbe_code 拿所有handled_node 数据。新增一个list拿全部数据
            Set<String> handledApprovalNodeIdsSet = new HashSet<>();
            Boolean hasRejectNode = false;
            boolean currentNodeApproved = false;
            for (SubscribeWorkflowApproveEntity workflowApprove : handledNodeList) {
                if (workflowApprove.getNodeApprovalStatus() == 2) {
                    hasRejectNode = true;
                    break;
                }
                if (workflowApprove.getNodeId().equals(subscribeApproveNode.getWfNodeId())
                        && workflowApprove.getCid().equals(cid)
                        && workflowApprove.getNodeApprovalStatus() == 1) {
                    currentNodeApproved = true;
                    break;
                }
                // 之前审批同意的需要加到已经审批同意列表
                if (workflowApprove.getNodeApprovalStatus() == 1 && cid.equals(workflowApprove.getCid())) {
                    handledApprovalNodeIdsSet.add(workflowApprove.getNodeId());
                }
            }
            if (hasRejectNode) {
                // 终止这次产品的循环
                nodeHadRejectCount++;
                continue;
            }
            if (currentNodeApproved) {
                nodeHadApproveCount++;
                continue;
            }

            if (subscribeWorkflowApproveDto.getNodeApproveStatus() == 2) {
                // reject handle
                subscribeWorkflowApproveMapper.updateSubscribeStatus(2, approvalTime, approveUser, subscribeCode, nodeId, cid);
                subscribeApproveMapper.updateByApproveStatus(2, subscribeCode, approvalTime, approveUser);
                nodeHandledCount++;
                wfHandleFinish++;
                // 终止这次产品的循环
                continue;
            }

            subscribeWorkflowApproveMapper.updateSubscribeStatus(1, approvalTime, approveUser, subscribeCode, nodeId, cid);
            // 加入当前node到审批同意列表
            handledApprovalNodeIdsSet.add(nodeId);
            // check the handled_node list (1.have reject )
            // fetch approvel note add to handledApprovalNodeIdsSet
            // update，根据nodeid和subcode
            // save handle status
            // 将当前节点加到已经处理的列表 handledApprovalNodeIdsSet.add()
            String workflowFormula= productCategoryMapper.selectByWorkflow(cid);
            WorkflowJsonListener listener = new WorkflowJsonListener(workflowFormula, true);
            List<WorkflowHandleNodeDto> nextPendingHandleNodeList = listener.nextPendingHandleNode(handledApprovalNodeIdsSet);
            if (CollectionUtils.isNotEmpty(nextPendingHandleNodeList)) {
                for (WorkflowHandleNodeDto workflowHandleNodeDto : nextPendingHandleNodeList) {
                    Set<String> nodeIdSet = new HashSet<>();
                    if (!handledNodeList.stream().map(SubscribeWorkflowApproveEntity::getNodeId)
                            .collect(Collectors.toSet()).contains(workflowHandleNodeDto.getNodeId())) {
                        // 同名nodeId节点去重处理
                        if (nodeIdSet.contains(workflowHandleNodeDto.getNodeId())) {
                            continue;
                        }
                        nodeIdSet.add(workflowHandleNodeDto.getNodeId());

                        List<String> wfHandleUserList = workflowHandleNodeDto.getUserIdList();
                        SubscribeWorkflowApproveEntity subscribeWorkflowApproveEntity = new SubscribeWorkflowApproveEntity();
                        subscribeWorkflowApproveEntity.setNodeApprovalStatus(0);
                        subscribeWorkflowApproveEntity.setCid(cid);
                        subscribeWorkflowApproveEntity.setSubCode(subscribeCode);
                        subscribeWorkflowApproveEntity.setNodeId(workflowHandleNodeDto.getNodeId());
                        subscribeWorkflowApproveEntity.setUserList(JSON.toJSONString(wfHandleUserList));
                        subscribeWorkflowApproveMapper.insert(subscribeWorkflowApproveEntity);
                    }
                }
                //添加新的pendingnode，需要 判断 新pending node 是否在handled_node
                //保存padding状态
            } else {
                    //根据单号查一下还有没有node节点有没有pending的状态，如果有就还不会走总表的状态
                    //再查询一次handledNodeList，判断有没有pending状态，没有pending就更新总表状态
                    //当查到下一个nodeid为空的时候更新总表的状态为1
                    subscribeApproveMapper.updateByApproveStatus(1, subscribeCode, approvalTime, approveUser);
                    wfHandleFinish++;
            }
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<CreateSubscribeRecordDto> createSubscribeRecordDtos) {
        List<SubscribeApproveEntity> subscribeApproveRecords = new ArrayList<>();
        log.info("进入批量订阅信息逻辑batchSave=====param{}::", JSON.toJSONString(createSubscribeRecordDtos));
        assembleRecordSecretsApprove(createSubscribeRecordDtos.get(0).getUserId(),createSubscribeRecordDtos,
                subscribeApproveRecords);
        this.saveBatch(subscribeApproveRecords);

    }



    private void assembleRecordSecretsApprove(String userId,List<CreateSubscribeRecordDto> createSubscribeRecordDtos,List<SubscribeApproveEntity> subscribeApproveRecords){
        for (CreateSubscribeRecordDto createSubscribeRecordDto : createSubscribeRecordDtos) {
            SecureRandom r = new SecureRandom();
            int number = r.nextInt(10);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String subCode = "FWD" + sdf.format(new Date()) + number;
            SubscribeApproveEntity subscribeApprove = new SubscribeApproveEntity();
            subscribeApprove.setProductId(createSubscribeRecordDto.getProductId());
            subscribeApprove.setApproveStatus(0);
            subscribeApprove.setSubCode(subCode);
            subscribeApprove.setUserId(userId);
            subscribeApprove.setProductName(createSubscribeRecordDto.getProductName());
            subscribeApprove.setCid(createSubscribeRecordDto.getCid());
            subscribeApproveRecords.add(subscribeApprove);
            Set<String> handledNodeIdsSet = new HashSet<>();
            calcAndSaveNextApproval(createSubscribeRecordDto.getCid(), handledNodeIdsSet, subCode);
        }
    }
    private void calcAndSaveNextApproval(String cid,Set<String> handleNodeIdsSet,String subCode){
        String workflowFormula= productCategoryMapper.selectByWorkflow(cid);
        WorkflowJsonListener listener = new WorkflowJsonListener(workflowFormula,true);
        List<WorkflowHandleNodeDto> nextPendingHandleNodeList = listener.nextPendingHandleNode(handleNodeIdsSet);
        Set<String> nodeIdSet = new HashSet<>();
        for (WorkflowHandleNodeDto workflowHandleNodeDto : nextPendingHandleNodeList) {
            nodeIdSet.add(workflowHandleNodeDto.getNodeId());
            List<String> wfHandleUserList = workflowHandleNodeDto.getUserIdList();
            SubscribeWorkflowApproveEntity subscribeWorkflowApproveEntity=new SubscribeWorkflowApproveEntity();
            subscribeWorkflowApproveEntity.setCid(cid);
            subscribeWorkflowApproveEntity.setNodeId(workflowHandleNodeDto.getNodeId());
            subscribeWorkflowApproveEntity.setNodeApprovalStatus(0);
            subscribeWorkflowApproveEntity.setUserList(JSON.toJSONString(wfHandleUserList));
            subscribeWorkflowApproveEntity.setSubCode(subCode);
            subscribeWorkflowApproveMapper.insert(subscribeWorkflowApproveEntity);

        }

    }


}
