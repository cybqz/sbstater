package com.cyb.common.tips;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共返回结果封装
 * @author CYB
 */
@Data
@NoArgsConstructor
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

	public static Tips success(Object data, String msg){
		return new Tips(msg, true, data);
	}

	public static Tips fail(Object data, String msg){
		return new Tips(msg, false, data);
	}

	public Tips(String msg, Integer code, boolean show, boolean validate) {
		super();
		this.msg = msg;
		this.code = code;
		this.show = show;
		this.validate = validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
		if(this.validate){
			this.code = HttpServletResponse.SC_OK;
		}else {
			this.code = HttpServletResponse.SC_BAD_REQUEST;
		}
	}
}
