package com.wick.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.Dto.CreateSubscribeRecordDto;
import com.wick.store.domain.Dto.SubscribeRecordRequstDto;
import com.wick.store.domain.Dto.SubscribeWorkflowApproveDto;
import com.wick.store.domain.Dto.WorkflowHandleNodeDto;
import com.wick.store.domain.entiey.PublishWorkflowApproveEntity;
import com.wick.store.domain.entiey.SubscribeApproveEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.SubscribeRecordAndProductVO;
import com.wick.store.domain.vo.SubscribeRecordVO;
import com.wick.store.domain.vo.WorkFlowVo;
import com.wick.store.repository.ProductCategoryMapper;
import com.wick.store.repository.SubscribeApproveMapper;
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

        Page<SubscribeRecordAndProductVO> pages=subscribeRecordMapper.selectApproveNodePageData(page,requstDto.getApprover(),requstDto.getApproveStatusList()
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
        for (SubscribeWorkflowApproveDto.subscribeApproveNode subscribeApproveNode:subscribeWorkflowApproveDto.getSubscribeApproveNodes()
        ) {
            //邮件审核的参数

            List<SubscribeRecord> subscribeRecords = subscribeRecordMapper.listSubscribeRecordByCode(subscribeApproveNode.getSubCode());
            log.info("subscribeRecord====>"+ subscribeRecords);
            Set<String> subscribeRecordIDs = subscribeRecords.stream().map(SubscribeRecord::getId).collect(Collectors.toSet());
            log.info("subId====>"+subscribeRecordIDs);
            String subscribeCode=subscribeApproveNode.getSubCode();
            String nodeId=subscribeApproveNode.getWfNodeId();
            String cid=subscribeApproveNode.getCid();
            Date approvalTime=subscribeWorkflowApproveDto.getApprovalTime();
            String approveUser=subscribeWorkflowApproveDto.getApprover();
            String approvalReason=subscribeWorkflowApproveDto.getApprovedMessage();
            String wfMasterId = null;
            long wfVersionNum = 0;
            boolean foundCid=false;
            // get wf_node_status
            List<SubscribeWorkflowApprove> handledNodeList= subscribeWorkflowApproveMapper.selectBySubCodesAndNodeId(subscribeCode);
            log.info("查看数据====>"+handledNodeList);
            //todo 通过拿对应的cid来拿到 wfMasterId和num，不是拿第一个了
            for (SubscribeWorkflowApprove workflowApproveCid :handledNodeList) {
                if (cid.equals(workflowApproveCid.getCid())){
                    wfMasterId=workflowApproveCid.getWorkflowMasterId();
                    wfVersionNum=workflowApproveCid.getWfVersionNumber();
                    foundCid=true;
                }
            }
            if (!foundCid){
                throw new BussinessException("cid not found");
            }

            // 通过subscirbe_code 拿所有handled_node 数据。新增一个list拿全部数据
            Set<String> handledApprovalNodeIdsSet = new HashSet<>();
            Boolean hasRejectNode = false;
            boolean currentNodeApproved = false;
            for (SubscribeWorkflowApprove workflowApprove :handledNodeList){
                if (workflowApprove.getNodeApprovalStatus()==2){
                    hasRejectNode=true;
                    break;
                }
                if (workflowApprove.getWfHandleNodeId().equals(subscribeApproveNode.getWfNodeId())
                        && workflowApprove.getCid().equals(cid)
                        && workflowApprove.getNodeApprovalStatus() == 1){
                    currentNodeApproved = true;
                    break;
                }
                // 之前审批同意的需要加到已经审批同意列表
                if (workflowApprove.getNodeApprovalStatus()==1 && cid.equals(workflowApprove.getCid())){
                    handledApprovalNodeIdsSet.add(workflowApprove.getWfHandleNodeId());
                }
            }
            if (hasRejectNode){
                // 终止这次产品的循环
                nodeHadRejectCount ++;
                continue;
            }
            if (currentNodeApproved){
                nodeHadApproveCount ++;
                continue;
            }

            if (subscribeWorkflowApproveDto.getApproveStatus()==2){
                // reject handle
                subscribeWorkflowApproveMapper.updateSubscribeStatus(2,approvalTime,approveUser,approvalReason,subscribeCode,nodeId,cid);
                subscribeApproveRecordMapper.updateByApproveStatus(2,subscribeCode,approvalTime,approveUser,approvalReason);
                nodeHandledCount ++;
                wfHandleFinish ++;
                // TODO message & email
                //发送失败的邮件给订阅者
                SubscribeUserVO subscribeUserVO= messageMapper.selectUserById(subscribeRecords.get(0).getUserId());
                List<Map<String, String>> listMessage = packSubApproveFailMessage(subscribeRecordIDs, subscribeUserVO.getNickname());
                messageService.saveMessage(MESSAGE_TEMPLATE_CODE2_2, listMessage);
                List<Map<String, Object>> msgList = packSubApproveFailEmailData(subscribeRecordIDs, approvalTime, subscribeUserVO.getEmail(), subscribeWorkflowApproveDto.getApprovedMessage());
                messageService.sendApproveFailEmail(msgList);
                // 终止这次产品的循环
                continue;
            }

            subscribeWorkflowApproveMapper.updateSubscribeStatus(1,approvalTime,approveUser,approvalReason,subscribeCode,nodeId,cid);
            // 加入当前node到审批同意列表
            handledApprovalNodeIdsSet.add(nodeId);
            // check the handled_node list (1.have reject )
            // fetch approvel note add to handledApprovalNodeIdsSet
            // update，根据nodeid和subcode
            // save handle status
            // 将当前节点加到已经处理的列表 handledApprovalNodeIdsSet.add()
            //String workflowSample1="eyJwYXJhbGxlbE5vZGUiOlt7InNlcmlhbE5vZGUiOlt7ImhhbmRsZU5vZGUiOnsibm9kZUlkIjoiQSIsInVzZXJJZExpc3QiOlsiMGEzZjk0ODc0MWU2NDdkY2I3YTBjZmNiNGY4MDQ1NjYiLCIwYTQ1NGNlNWZjOTQ0NjFjYjJmYThjZTIyMGE4YjAzNSJdfX0seyJwYXJhbGxlbE5vZGUiOlt7ImhhbmRsZU5vZGUiOnsibm9kZUlkIjoiQiIsInVzZXJJZExpc3QiOlsiMGEzZjk0ODc0MWU2NDdkY2I3YTBjZmNiNGY4MDQ1NjYiLCIwYTQ1NGNlNWZjOTQ0NjFjYjJmYThjZTIyMGE4YjAzNSJdfX0seyJoYW5kbGVOb2RlIjp7Im5vZGVJZCI6IkMiLCJ1c2VySWRMaXN0IjpbIjBhM2Y5NDg3NDFlNjQ3ZGNiN2EwY2ZjYjRmODA0NTY2IiwiMGE0NTRjZTVmYzk0NDYxY2IyZmE4Y2UyMjBhOGIwMzUiXX19XX0seyJoYW5kbGVOb2RlIjp7Im5vZGVJZCI6IkQiLCJ1c2VySWRMaXN0IjpbIjBhM2Y5NDg3NDFlNjQ3ZGNiN2EwY2ZjYjRmODA0NTY2IiwiMGE0NTRjZTVmYzk0NDYxY2IyZmE4Y2UyMjBhOGIwMzUiXX19LHsicGFyYWxsZWxOb2RlIjpbeyJzZXJpYWxOb2RlIjpbeyJoYW5kbGVOb2RlIjp7Im5vZGVJZCI6IkUiLCJ1c2VySWRMaXN0IjpbIjBhM2Y5NDg3NDFlNjQ3ZGNiN2EwY2ZjYjRmODA0NTY2IiwiMGE0NTRjZTVmYzk0NDYxY2IyZmE4Y2UyMjBhOGIwMzUiXX19LHsiaGFuZGxlTm9kZSI6eyJub2RlSWQiOiJGIiwidXNlcklkTGlzdCI6WyIwYTNmOTQ4NzQxZTY0N2RjYjdhMGNmY2I0ZjgwNDU2NiIsIjBhNDU0Y2U1ZmM5NDQ2MWNiMmZhOGNlMjIwYThiMDM1Il19fV19LHsic2VyaWFsTm9kZSI6W3siaGFuZGxlTm9kZSI6eyJub2RlSWQiOiJHIiwidXNlcklkTGlzdCI6WyIwYTNmOTQ4NzQxZTY0N2RjYjdhMGNmY2I0ZjgwNDU2NiIsIjBhNDU0Y2U1ZmM5NDQ2MWNiMmZhOGNlMjIwYThiMDM1Il19fSx7ImhhbmRsZU5vZGUiOnsibm9kZUlkIjoiSCIsInVzZXJJZExpc3QiOlsiMGEzZjk0ODc0MWU2NDdkY2I3YTBjZmNiNGY4MDQ1NjYiLCIwYTQ1NGNlNWZjOTQ0NjFjYjJmYThjZTIyMGE4YjAzNSJdfX1dfV19LHsiaGFuZGxlTm9kZSI6eyJub2RlSWQiOiJJIiwidXNlcklkTGlzdCI6WyIwYTNmOTQ4NzQxZTY0N2RjYjdhMGNmY2I0ZjgwNDU2NiIsIjBhNDU0Y2U1ZmM5NDQ2MWNiMmZhOGNlMjIwYThiMDM1Il19fV19XX0";
            BaseResponse<ApprovalWorkflowVo> approvalWorkflowVoBaseResponse= approvalWorkflowApi.get(wfMasterId,(int)wfVersionNum);
            AsserUtils.isSuccess(approvalWorkflowVoBaseResponse,"workflow request fail.");
            WorkflowJsonListener listener = new WorkflowJsonListener(approvalWorkflowVoBaseResponse.getData().getWorkflowFormula(),true);
            List<WorkflowHandleNodeDto> nextPendingHandleNodeList = listener.nextPendingHandleNode(handledApprovalNodeIdsSet);
            if (CollectionUtils.isNotEmpty(nextPendingHandleNodeList)){
                for (WorkflowHandleNodeDto workflowHandleNodeDto:nextPendingHandleNodeList){
                    Set<String> nodeIdSet = new HashSet<>();
                    if (!handledNodeList.stream().map(SubscribeWorkflowApprove::getWfHandleNodeId)
                            .collect(Collectors.toSet()).contains(workflowHandleNodeDto.getNodeId())){
                        // 同名nodeId节点去重处理
                        if (nodeIdSet.contains(workflowHandleNodeDto.getNodeId())){
                            continue;
                        }
                        nodeIdSet.add(workflowHandleNodeDto.getNodeId());

                        List<String> wfHandleUserList = workflowHandleNodeDto.getUserIdList();
                        SubscribeWorkflowApprove subscribeWorkflowApprove=new SubscribeWorkflowApprove();
                        subscribeWorkflowApprove.setNodeApprovalStatus(0);
                        subscribeWorkflowApprove.setCid(cid);
                        subscribeWorkflowApprove.setWorkflowMasterId(wfMasterId);
                        subscribeWorkflowApprove.setSubscribeCode(subscribeCode);
                        subscribeWorkflowApprove.setWfVersionNumber(wfVersionNum);
                        subscribeWorkflowApprove.setWfHandleNodeId(workflowHandleNodeDto.getNodeId());
                        subscribeWorkflowApprove.setWfHandleUserList(JSON.toJSONString(wfHandleUserList));
                        subscribeWorkflowApproveMapper.insert(subscribeWorkflowApprove);
                        // TODO message & email
                        // 组装信息然后发送订阅邮件给产品拥有者
                        Date effectiveStartDate = subscribeRecords.get(0).getEffectiveStartDate();
                        Date effectiveEndDate = subscribeRecords.get(0).getEffectiveEndDate();
                        String subscribeUsage = subscribeRecords.get(0).getSubscribeUsage();
                        String roleId = messageMapper.getRoleId(1);

                        List<SubscribeApproveMailVO> subscribeApproveMailVOS =
                                messageMapper.getSubscribeApproveMailDataWf(new ArrayList(subscribeRecordIDs));
                        for (String pendingUserId: wfHandleUserList) {
                            SubscribeUserVO subscribeUserVO= messageMapper.selectUserById(subscribeRecords.get(0).getUserId());
                            SubscribeUserVO pendingUser = messageMapper.selectUserById(pendingUserId);
                            //传入user列表
                            //approve handle
                            for (SubscribeApproveMailVO subscribeApproveMailVO:
                                    subscribeApproveMailVOS) {
                                subscribeApproveMailVO.setOwner(pendingUser.getNickname());
                                subscribeApproveMailVO.setUserId(pendingUserId);
                                subscribeApproveMailVO.setOwnerEmail(pendingUser.getEmail());
                            }
                            log.info("subscribeApproveMailVOS====>"+subscribeApproveMailVOS);
                            log.info("subscribeUserVO====>"+subscribeUserVO);
                            log.info("pendingUser=====>"+pendingUser);
                            sendSubApproveEmail(cid,effectiveStartDate, effectiveEndDate, subscribeUsage,
                                    roleId, subscribeUserVO.getEmail(), subscribeApproveMailVOS,approvalTime,workflowHandleNodeDto.getNodeId());
                        }
                    }
                    //添加新的pendingnode，需要 判断 新pending node 是否在handled_node
                    //保存padding状态
                }
            }
            else {
                int nodeApproveStatusCount=subscribeWorkflowApproveMapper.selectWfNodeApproveStatusCount(subscribeCode);
                if (nodeApproveStatusCount==0) {
                    //根据单号查一下还有没有node节点有没有pending的状态，如果有就还不会走总表的状态
                    //再查询一次handledNodeList，判断有没有pending状态，没有pending就更新总表状态
                    //当查到下一个nodeid为空的时候更新总表的状态为1
                    subscribeApproveRecordMapper.updateByApproveStatus(1, subscribeCode, approvalTime, approveUser, approvalReason);
                    // 审批通过，创建用户group,用于kong上绑定service和consumer(先解绑，后绑定)
                    kongBindConsumerGroup(subscribeRecordIDs, 1);
                    wfHandleFinish++;
                    //发送成功的邮件给订阅者
                    SubscribeUserVO userInfo = messageMapper.selectUserById(subscribeRecords.get(0).getUserId());
                    List<Map<String, String>> listMessage = packSubApprovePassMessage(subscribeRecordIDs, userInfo.getNickname());
                    messageService.saveMessage(MESSAGE_TEMPLATE_CODE1_1, listMessage);
                    List<Map<String, Object>> msgList = packSubApprovePassEmailData(subscribeRecordIDs);
                    messageService.sendSubApprovePassEmail(msgList);
                }
                // TODO message & email
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
    private void calcAndSaveNextApproval(String cid,Set<String> handleNodeIdsSet,String pubCode){
        WorkFlowVo workFlowVos=productCategoryMapper.selectByWorkflow(cid);
        String workflowFormula=workFlowVos.getWorkflowFormula();
        WorkflowJsonListener listener = new WorkflowJsonListener(workflowFormula,true);
        List<WorkflowHandleNodeDto> nextPendingHandleNodeList = listener.nextPendingHandleNode(handleNodeIdsSet);
        Set<String> nodeIdSet = new HashSet<>();
        for (WorkflowHandleNodeDto workflowHandleNodeDto : nextPendingHandleNodeList) {
            nodeIdSet.add(workflowHandleNodeDto.getNodeId());
            List<String> wfHandleUserList = workflowHandleNodeDto.getUserIdList();
            PublishWorkflowApproveEntity publishWorkflowApproveEntity=new PublishWorkflowApproveEntity();
            publishWorkflowApproveEntity.setCid(cid);
            publishWorkflowApproveEntity.setNodeId(workflowHandleNodeDto.getNodeId());
            publishWorkflowApproveEntity.setNodeApproveStatus(0);
            publishWorkflowApproveEntity.setUserList(JSON.toJSONString(wfHandleUserList));
            publishWorkflowApproveEntity.setPubCode(pubCode);
            publishWorkflowApproveEntity.setWorkflowId(workFlowVos.getWorkflowId());
            publishWorkflowApproveMapper.insert(publishWorkflowApproveEntity);

        }

    }


}
