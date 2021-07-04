package com.cyb.common.pagination;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 分页封装类
 * @author CYB
 */
public class Pagination<Entity> {

	private int limit;
	
	private int pageCount;

	private int pageIndex = 1;

	private List<Entity> data;
	
	private long total;

	public boolean isFirstPage = false;
	public boolean isLasePage = false;

	public Pagination() {
		this.limit = 5;
	}

	public Pagination(int limit, int pageIndex) {
		if(limit > 0 && limit <= 50) {
			this.limit = limit;
		}else{
			this.limit = 5;
		}
		if(pageIndex <= 1) {
			this.pageIndex = 1;
		}else{
			this.pageIndex = pageIndex;
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
		if(pageIndex <= 1){
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
		this.isFirstPage = this.pageIndex == 1;
		this.isLasePage = this.pageIndex == this.pageCount;
		this.pageCount = (int) (this.limit == 0 ? 1 : (total + this.limit - 1) / this.limit);
	}

	public static IPage toIPage(Pagination pagination){
		if(null == pagination){
			pagination = new Pagination<>();
		}
		return new Page<>(pagination.getPageIndex(), pagination.getLimit());
	}
}
