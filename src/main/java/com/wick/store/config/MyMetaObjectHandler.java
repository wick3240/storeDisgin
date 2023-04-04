package com.wick.store.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author hongjuzhu
 * @date 2022/3/9
 */
@Configuration
@MapperScan("com.wick.store.repository")
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "isDeleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //每次更新强制修改更新日期，只适用于updateById有效
        this.setFieldValByName("updateDate", new Date(), metaObject);
    }
}
