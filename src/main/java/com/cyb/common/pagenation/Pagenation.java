package com.cyb.common.pagenation;

public class Pagenation {

	private Integer pageSize = 5;
	
	private Integer pageIndex = 0;
	
	private int pageCount;
	
	private Object pageDatas;
	
	private int offset = 0;
	
	private long dataCount;
	
	public boolean searcha = true;

	public Pagenation(Integer pageSize, Integer pageIndex) {
		if(null != pageSize){
			this.pageSize = pageSize;
		}
		if(null != pageIndex){
			this.pageIndex = pageIndex;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize <= 0 || pageSize >= 50) {
			pageSize = 10;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getPageIndex() {

		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		if(pageIndex <= 0) {
			pageIndex = 0;
		}
		this.pageIndex = pageIndex;
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
		offset = (pageIndex-1)*pageSize;
		return offset;
	}

	public long getDataCount() {
		return dataCount;
	}

	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
		if(dataCount%pageSize==0) {
			this.pageCount = (int) (dataCount/pageSize);
		}else {
			this.pageCount = (int) (dataCount/pageSize)+1;
		}
		searcha = pageIndex <= pageCount;
	}
}
