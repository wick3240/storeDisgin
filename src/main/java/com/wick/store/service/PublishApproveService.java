package com.wick.store.service;

import com.wick.store.domain.Dto.PublishApproveDto;
import com.wick.store.domain.Dto.PublishWorkflowApproveDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductPublishApproveVo;

public interface PublishApproveService {
    PageVO<ProductPublishApproveVo> selectPublisherApprovePage(PublishApproveDto requestDto);

    void workflowPublishApprove(PublishWorkflowApproveDto publishWorkflowApproveDto);
}
