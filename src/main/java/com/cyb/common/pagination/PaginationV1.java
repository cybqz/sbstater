package com.cyb.common.pagination;

import java.util.List;

/**
 * 分页封装类
 */
public class PaginationV1<Entity> {

	private int limit;

	private int start;

	private int pageCount;

	private int pageIndex;

	private List<Entity> data;

	private long total;

	public boolean isFirstPage = false;
	public boolean isLasePage = false;

	public PaginationV1() {
		this.limit = 5;
		this.start = 0;
	}

	public PaginationV1(int limit, int start) {
		if(limit > 0 && limit <= 50) {
			this.limit = limit;
		}else{
			this.limit = 5;
		}
		if(start <= 0) {
			this.start = 0;
		}else{
			this.start = start;
		}
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if(limit > 0 && limit <= 50) {
			this.limit = limit;
		}else{
			this.limit = 5;
		}
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		if(start <= 0) {
			this.start = 0;
		}else{
			this.start = start;
		}
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if(pageIndex <= 0){
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public List<Entity> getDatas() {
		return data;
	}

	public void setDatas(List<Entity> data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		if(total%this.limit==0) {
			this.pageCount = (int) (total/this.limit);
		}else {
			this.pageCount = (int) (total/this.limit)+1;
		}
		this.isFirstPage = this.start == 0;
		this.pageIndex = (start / limit) + 1;
		this.isLasePage = this.start + 1 == this.pageCount;
		this.pageCount = (int) (this.limit == 0 ? 1 : (total + this.limit - 1) / this.limit);
	}
}
