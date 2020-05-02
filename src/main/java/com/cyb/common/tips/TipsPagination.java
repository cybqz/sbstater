package com.cyb.common.tips;

import com.cyb.common.pagination.Pagination;
import lombok.Getter;
import javax.servlet.http.HttpServletResponse;

/**
 * 分页公共返回结果封装
 */
@Getter
public class TipsPagination<E> {

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
	private Pagination<E> pagination;

	public TipsPagination() {}

	public TipsPagination(String msg, Integer code, boolean validate, Pagination<E> pagination) {
		super();
		this.msg = msg;
		this.code = code;
		this.validate = validate;
		this.pagination = pagination;
		if(validate){
			this.code = HttpServletResponse.SC_OK;
		}
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

	public void setPagination(Pagination<E> pagination) {
		this.pagination = pagination;
	}

	public void convertFromTips(Tips tips){
		this.setMsg(tips.getMsg());
		this.setCode(tips.getCode());
		this.setValidate(tips.isValidate());
	}
}
