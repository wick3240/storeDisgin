package com.wick.store.domain.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PageVO<T> {
    /**
     * 页码
     */
    private Long page;
    /**
     * 每页条数
     */
    private Long pageSize;
    /**
     * 总条数
     */
    private Long total;
    private List<T> list;

    public PageVO(Page<T> page) {
        this.page = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.list = CollectionUtils.isEmpty(page.getRecords()) ? new ArrayList<>() : page.getRecords();
    }
}
