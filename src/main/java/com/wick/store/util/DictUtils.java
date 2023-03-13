package com.wick.store.util;

import com.wick.store.repository.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Component
public class DictUtils {
    private static ProductCategoryMapper productCategoryMapper;

    @Autowired
    public void setProductCategoryMapper(ProductCategoryMapper productCategoryMapper) {
        DictUtils.productCategoryMapper = productCategoryMapper;
    }

    public static Map<String, String> getProductTypeDict(){
        List<DictVo> productTypeDict = productCategoryMapper.getByDictType("PRODUCT_TYPE");
        Map<String, String> dictMap = new LinkedHashMap<>();
        productTypeDict.forEach(d -> dictMap.put(d.getValue(), d.getLabel()));
        return dictMap;
    }

    public static List<DictVo> getProductTypeDictVo(){
        return productCategoryMapper.getByDictType("PRODUCT_TYPE");
    }
    public static List<DictVo> getAddProductDictVo(){
        return productCategoryMapper.getByDictType("ADD_PRODUCT");
    }
}
