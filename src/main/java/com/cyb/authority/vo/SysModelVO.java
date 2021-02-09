package com.cyb.authority.vo;

import com.cyb.authority.domain.SysModel;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 系统模块对象VO
 * @Date 2021/2/8
 */
@Getter
@Setter
public class SysModelVO {

    private String title;

    private String navbar;

    private String url;

    private String iconClass;

    private List<SysModel> children;
}
