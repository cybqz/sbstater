package com.cyb.common.tips;

import com.cyb.common.pagination.Pagination;
import lombok.Getter;
import javax.servlet.http.HttpServletResponse;

@Getter
public class Tips {

	/**
	 * 消息
	 */
	private String msg;

	/**
	 * 页面是否提示
	 */
	private boolean show = true;

	/**
	 * 验证结果
	 */
	private boolean validate;

	/**
	 * 结果代码
	 */
	private Integer code = HttpServletResponse.SC_BAD_REQUEST;

	/**
	 * 分页信息
	 */
	private Pagination pagination;

	/**
	 * 返回数据
	 */
	private Object data;
	
	public Tips(String msg, boolean validate) {
		super();
		this.msg = msg;
		this.validate = validate;
		if(validate){
			this.code = HttpServletResponse.SC_OK;
		}
	}
	
	public Tips(String msg, boolean validate, Object data) {
		super();
		this.msg = msg;
		this.data = data;
		this.validate = validate;
		if(validate){
			this.code = HttpServletResponse.SC_OK;
		}
	}
	
	public Tips(String msg, boolean show, boolean validate) {
		super();
		this.msg = msg;
		this.show = show;
		this.validate = validate;
		if(validate){
			this.code = HttpServletResponse.SC_OK;
		}
	}

	public Tips(String msg, Integer code, boolean show, boolean validate) {
		super();
		this.msg = msg;
		this.code = code;
		this.show = show;
		this.validate = validate;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
		if(this.validate){
			this.code = HttpServletResponse.SC_OK;
		}else {
			this.code = HttpServletResponse.SC_BAD_REQUEST;
		}
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
