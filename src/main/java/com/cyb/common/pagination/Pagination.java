package com.cyb.common.pagination;

/**
 * 公用分页类
 */
public class Pagination {

	private int pageSize;
	
	private int pageIndex;
	
	private int pageCount;
	
	private Object pageDatas;
	
	private int offset = 0;
	
	private long dataCount;

	public boolean isFirstPage = false;
	public boolean isLasePage = false;

	public Pagination() {
		this.pageSize = 5;
		this.pageIndex = 0;
	}

	public Pagination(int pageSize, int pageIndex) {
		if(pageSize > 0 && pageSize <= 50) {
			this.pageSize = pageSize;
		}else{
			this.pageSize = 5;
		}
		if(pageIndex <= 0) {
			this.pageIndex = 0;
		}else{
			this.pageIndex = pageIndex;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > 0 && pageSize <= 50) {
			this.pageSize = pageSize;
		}else{
			this.pageSize = 5;
		}
		this.offset = this.pageIndex*this.pageSize;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if(pageIndex <= 0) {
			this.pageIndex = 0;
		}else{
			this.pageIndex = pageIndex;
		}
		this.offset = (pageIndex-1)*this.pageSize;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Object getPageDatas() {
		return pageDatas;
	}

	public void setPageDatas(Object pageDatas) {
		this.pageDatas = pageDatas;
	}

	public int getOffset() {
		return offset;
	}

	public long getDataCount() {
		return dataCount;
	}

	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
		if(dataCount%this.pageSize==0) {
			this.pageCount = (int) (dataCount/this.pageSize);
		}else {
			this.pageCount = (int) (dataCount/this.pageSize)+1;
		}
		this.isFirstPage = this.pageIndex == 0;
		this.isLasePage = this.pageIndex + 1 == this.pageCount;
		this.offset = this.pageIndex*this.pageSize;
	}
}
