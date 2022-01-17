package com.foxfxb.interviewee.response;

import com.foxfxb.interviewee.entity.base.BaseObj;
import lombok.Data;

import java.util.List;

@Data
public class PageQueryResponse<T> extends BaseObj {
    private Long pageNum = 1L;

    private Long pageSize = 10L;

    private Long total;

    private Long totalPages;

    private List<T> result;

    private long draw;
    private long recordsTotal;
    private long recordsFiltered;


    public PageQueryResponse(Long pageNum, Long pageSize, Long total, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.result = data;
        this.totalPages = total / pageSize + 1;
        this.recordsTotal = total;
        this.recordsFiltered = total;

    }
}
