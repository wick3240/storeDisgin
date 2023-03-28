package com.wick.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.Dto.WorkflowHandleNodeDto;
import com.wick.store.domain.entiey.ProductInfoEntity;
import com.wick.store.domain.entiey.PublishApproveEntity;
import com.wick.store.domain.entiey.PublishWorkflowApproveEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductInfoVo;
import com.wick.store.domain.vo.WorkFlowVo;
import com.wick.store.repository.ProductCategoryMapper;
import com.wick.store.repository.ProductInfoMapper;
import com.wick.store.repository.PublishApproveMapper;
import com.wick.store.repository.PublishWorkflowApproveMapper;
import com.wick.store.service.ProductInfoService;
import com.wick.store.util.WorkflowJsonListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfoEntity>
        implements ProductInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private PublishWorkflowApproveMapper publishWorkflowApproveMapper;
    @Autowired
    private PublishApproveMapper publishApproveMapper;

    @Override
    public ProductInfoVo save(ProductInfoDto productInfoDto) {

        ProductInfoEntity productInfoEntity=new ProductInfoEntity();
        BeanUtils.copyProperties(productInfoDto,productInfoEntity);
        productInfoMapper.insert(productInfoEntity);
        ProductInfoVo productInfoVo=new ProductInfoVo();
        BeanUtils.copyProperties(productInfoVo,productInfoDto);
        return productInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductInfoDto productInfoDto) {
        ProductInfoEntity productInfoEntity=new ProductInfoEntity();
        BeanUtils.copyProperties(productInfoDto,productInfoEntity);
        productInfoMapper.updateById(productInfoEntity);
    }

    @Override
    public PageVO<ProductInfoVo> queryPage(ProductInfoDto productInfoDto) {
        PageVO<ProductInfoVo> data=new PageVO();
        int current = productInfoDto.getPage() == null ? 1 : productInfoDto.getPage();
        int size = productInfoDto.getRow() == null ? 5 : productInfoDto.getRow();
        Page<ProductInfoVo> page=new Page<>(current,size);
        Page<ProductInfoVo> pages=productInfoMapper.queryPage(page,productInfoDto);
        data.setList(pages.getRecords());
        data.setPage(pages.getCurrent());
        data.setPageSize(pages.getSize());
        data.setTotal(pages.getTotal());
        return data;
    }

    @Override
    public ProductInfoVo findById(String productId) {
        ProductInfoVo productInfoVo=productInfoMapper.selectByProduct(productId);
        return productInfoVo;
    }

    @Override
    public void delete(String productId) {
        log.info("删除产品：id===>{}", productId);
        ProductInfoEntity productInfoEntity = getOne(new QueryWrapper<ProductInfoEntity>().eq("id", productId).eq("is_deleted", 0).in("status", Arrays.asList(2,3,4)));
        productInfoMapper.deleteRemovedProduct(productInfoEntity.getId());
    }

    @Override
    public void updateByStatus(String productId) {
        log.info("下架产品=====>",productId);
        productInfoMapper.updateByStatus(productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchPublish(List<ProductInfoDto> productInfoDto) {
        singleClassificationProcess(productInfoDto);
    }
    //将相应的字段set进表中
    private void singleClassificationProcess(List<ProductInfoDto> productInfoDto){
        String cid = productInfoDto.get(0).getCid();
        List<String> productIds = productInfoDto.stream().map(ProductInfoDto::getId).collect(Collectors.toList());
        List<ProductInfoEntity> productInfoEntities = list(new QueryWrapper<ProductInfoEntity>().in("id", productIds).eq("is_deleted", 0));
        SecureRandom i =new SecureRandom();
        int number=i.nextInt(10);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        String pubCode="FWD"+dateFormat.format(new Date())+number;
        for (ProductInfoEntity productInfoEntity:productInfoEntities
             ) {
            productInfoEntity.setPubCode(pubCode);
            productInfoEntity.setStatus(0);
            productInfoMapper.updateById(productInfoEntity);
        }
        saveApproveRecord(productInfoEntities);
        Set<String> handledNodeIdsSet = new HashSet<>();
        calcAndSaveNextApproval(cid,handledNodeIdsSet,pubCode);
        }
        private void calcAndSaveNextApproval(String cid,Set<String> handleNodeIdsSet,String pubCode){
            String workflowFormula=productCategoryMapper.selectByWorkflow(cid);
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
                publishWorkflowApproveMapper.insert(publishWorkflowApproveEntity);

            }

        }

    private void saveApproveRecord(List<ProductInfoEntity> productList) {

        for (ProductInfoEntity entity : productList) {
            PublishApproveEntity publishApproveEntity = new PublishApproveEntity();
            publishApproveEntity.setProductId(entity.getId());
            publishApproveEntity.setPubCode(entity.getPubCode());
            publishApproveEntity.setUserId(entity.getCreatedUser());
            publishApproveEntity.setApproveStatus(0);
            publishApproveEntity.setPubCode(entity.getPubCode());
            publishApproveMapper.insert(publishApproveEntity);
        }
    }


}

