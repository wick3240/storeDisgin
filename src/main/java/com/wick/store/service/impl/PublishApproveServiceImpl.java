package com.wick.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.PublishApproveDto;
import com.wick.store.domain.Dto.PublishWorkflowApproveDto;
import com.wick.store.domain.Dto.WorkflowHandleNodeDto;
import com.wick.store.domain.entiey.ProductInfoEntity;
import com.wick.store.domain.entiey.PublishWorkflowApproveEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductPublishApproveVo;
import com.wick.store.repository.PublishApproveMapper;
import com.wick.store.repository.PublishWorkflowApproveMapper;
import com.wick.store.service.PublishApproveService;
import com.wick.store.util.WorkflowJsonListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PublishApproveServiceImpl implements PublishApproveService {
    @Autowired
    private PublishApproveMapper approveMapper;
    @Autowired
    private PublishWorkflowApproveMapper publishWorkflowApproveMapper;


    @Override
    public PageVO<ProductPublishApproveVo> selectPublisherApprovePage(PublishApproveDto requestDto) {
            log.info("进入审批发布信息分页查询逻辑 selectPublisherApprovePage, param==={}", JSON.toJSONString(requestDto));
            PageVO<ProductPublishApproveVo> data = new PageVO();
            //默认分页数据是第一页，每页10条
            int current = requestDto.getPage() == null ? 1 : requestDto.getPage();
            int size = requestDto.getRow() == null ? 10 : requestDto.getRow();
            Page<ProductPublishApproveVo> page = new Page<>(current, size);
            if (requestDto.getStatusList() != null && requestDto.getStatusList().size() == 1) {
                requestDto.setStatus(0);
            }
            List<Integer> nodeStatusList = new ArrayList<>();
            if (requestDto.getStatusList().size()==1){
                int queryStatus = requestDto.getStatusList().get(0);
                if (queryStatus==0){
                    nodeStatusList.add(0);
                }else if (queryStatus==1){
                    requestDto.getStatusList().add(0);
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

            Page<ProductPublishApproveVo> pages=approveMapper.selectPublisherApproveNodeData(page,requestDto.getApprover(),
                    requestDto.getStatusList(),nodeStatusList,requestDto);

            data.setList(pages.getRecords());
            data.setPage(pages.getCurrent());
            data.setPageSize(pages.getSize());
            data.setTotal(pages.getTotal());
            return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workflowPublishApprove(PublishWorkflowApproveDto publishWorkflowApproveDto) {
        log.info("进入批量审批逻辑=====param{}::", JSON.toJSONString(publishWorkflowApproveDto));

        int nodeHadApproveCount = 0; // 已经被approve的node个数
        int nodeHadRejectCount = 0;  // 已经被reject的node个数
        int nodeHandledCount = 0;   //节点的数量
        int wfHandleFinish = 0;    //节点完成的数据

        publishWorkflowApproveDto.setApprovalTime(new Date());
        for (PublishWorkflowApproveDto.PublishApproveNode publishApproveNode : publishWorkflowApproveDto.getPublishApproveNodes()
        ) {

            String pubCode = publishApproveNode.getPubCode();
            String nodeId = publishApproveNode.getWfNodeId();
            String cid = publishApproveNode.getCid();
            Date approveTime = publishWorkflowApproveDto.getApprovalTime();
            String approveUser = publishWorkflowApproveDto.getNodeApprover();
            Set<String> pubCodeSet = new HashSet<>();
            pubCodeSet.add(pubCode);
            List<String> productIds = approveMapper.getProductIdsByPubCode(pubCodeSet);
            // get wf_node_status
            List<PublishWorkflowApproveEntity> handledNodeList = publishWorkflowApproveMapper.selectByPubCodeAndUserList(pubCode);
            log.info("查看数据====>" + handledNodeList);
            String wfMasterId = null;



//            String wfMasterId = handledNodeList.get(0).getWorkflowMasterId();
//            long wfVersionNum = handledNodeList.get(0).getWfVersionNumber();

            // 通过pub_code 拿所有handled_node 数据。新增一个list拿全部数据
            Set<String> handledApprovalNodeIdsSet = new HashSet<>();
            Boolean hasRejectNode = false;
            boolean currentNodeApproved = false;
            for (PublishWorkflowApproveEntity workflowApprove : handledNodeList) {
                if (workflowApprove.getNodeApproveStatus() == 2) {
                    hasRejectNode = true;
                    break;
                }
                if (workflowApprove.getNodeId().equals(publishApproveNode.getWfNodeId()) && workflowApprove.getCid().equals(cid)
                        && workflowApprove.getNodeApproveStatus() == 1) {
                    currentNodeApproved = true;
                    break;
                }
                if (workflowApprove.getNodeApproveStatus() == 1 && cid.equals(workflowApprove.getCid())) {
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
            if (publishWorkflowApproveDto.getNodeApproveStatus() == 2) {
                publishWorkflowApproveMapper.updatePublishStatus(2, approveUser, approveTime, pubCode, nodeId, cid);
                approveMapper.updateByApproveStatus(2, approveUser, approveTime, pubCode);
                //todo 更新产品的状态，拒绝就是没有上架
                approveMapper.updateBatchProductStatus(productIds, ProductStatusType.UN_APPROVAL.getCode());
                nodeHandledCount++;
                wfHandleFinish++;

                continue;
            }

            //
            publishWorkflowApproveMapper.updatePublishStatus(1, approveUser, approveTime, pubCode, nodeId, cid);

            handledApprovalNodeIdsSet.add(nodeId);

            WorkflowJsonListener listener = new WorkflowJsonListener(approvalWorkflowVoBaseResponse.getData().getWorkflowFormula(), true);
            List<WorkflowHandleNodeDto> nextPendingHandleNodeList = listener.nextPendingHandleNode(handledApprovalNodeIdsSet);
            if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty(nextPendingHandleNodeList)) {
                Set<String> nodeIdSet = new HashSet<>();
                for (WorkflowHandleNodeDto workflowHandleNodeDto : nextPendingHandleNodeList) {
                    if (!handledNodeList.stream().map(PublishWorkflowApproveEntity::getNodeId)
                            .collect(Collectors.toSet()).contains(workflowHandleNodeDto.getNodeId())) {
                        // 同名nodeId节点去重处理
                        if (nodeIdSet.contains(workflowHandleNodeDto.getNodeId())) {
                            continue;
                        }
                        nodeIdSet.add(workflowHandleNodeDto.getNodeId());

                        List<String> wfHandleUserList = workflowHandleNodeDto.getUserIdList();
                        PublishWorkflowApproveEntity publishWorkflowApprove = new PublishWorkflowApproveEntity();
                        publishWorkflowApprove.setNodeApproveStatus(0);
                        publishWorkflowApprove.setCid(cid);
                        publishWorkflowApprove.setWorkflowId(wfMasterId);
                        publishWorkflowApprove.setPubCode(pubCode);
                        publishWorkflowApprove.setNodeId(workflowHandleNodeDto.getNodeId());
                        publishWorkflowApprove.setUserList(JSON.toJSONString(wfHandleUserList));
                        publishWorkflowApproveMapper.insert(publishWorkflowApprove);


                    }
                }
                //添加新的pendingnode，需要 判断 新pending node 是否在handled_node
                //保存padding状态
            } else {
                int countPendingStatus = productClassificationMapper.selectCountPendingStatus(pubCode);
                if(countPendingStatus == 0){
                    //TODO:用sql去判断唯一订单是否还有pending状态，有的话就继续，没有就执行更新操作
                    approveMapper.updateByApproveStatus(1, approveUser, approveTime, pubCode);
                    // TODO: 2023/3/25 如果没有找到下一个节点，直接更新状态和产品的状态
                    approveMapper.updateBatchProductStatus(productIds, ProductStatusType.PUBLISHED.getCode());
                    wfHandleFinish++;

                }
            }

        }
    }
}
