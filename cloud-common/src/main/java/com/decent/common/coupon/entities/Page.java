package com.decent.common.coupon.entities;


import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author 唐全成
 * @date 2018/3/30
 */
@SuppressWarnings("unused")
@Data
public class Page<T> {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     */
    private int pageNumber;
    /**
     * 每页的数量
     */
    private int pageSize;
    /**
     * 当前页的数量
     */
    private int size;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 结果集
     */
    private List<T> list;

    public Page() {
    }

    /**
     * 包装Page对象
     *
     * @param list 数据集合
     */
    public Page(List<T> list) {
        this(list, 8);
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public Page(List<T> list, int navigatePages) {
        Objects.requireNonNull(list);
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page<T> page = (com.github.pagehelper.Page<T>) list;
            this.pageNumber = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.list = page.getResult();
            this.size = page.size();
            this.total = page.getTotal();

        } else {
            this.pageNumber = 1;
            this.pageSize = list.size();
            this.pages = 1;
            this.list = list;
            this.size = list.size();
            this.total = list.size();
        }

    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @JsonGetter("rows")
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                '}';
    }
}
