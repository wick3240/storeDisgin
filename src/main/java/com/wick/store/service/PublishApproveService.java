package com.wick.store.service;

public interface PublishApproveService {
    PageVO<ProductPublishApproveVo> selectPublisherApprovePage(SubscribeRecordRequstDto recordRequestDto);

    void batchUpdateApprove(List<ProductPublishApproveDto> publishApproveDtos);

    void workflowPublishApprove(PublishWorkflowApproveDTO publishWorkflowApproveDTO);
}
