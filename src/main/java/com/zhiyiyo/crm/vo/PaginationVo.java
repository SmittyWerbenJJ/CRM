package com.zhiyiyo.crm.vo;

import java.util.List;

public class PaginationVo<T> {
    private Integer count;
    private List<T> dataList;

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count == null ? 0 : count;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
