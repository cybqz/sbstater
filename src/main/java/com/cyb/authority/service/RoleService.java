package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.RoleMapper;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author 陈迎博
 * @Description 角色服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleService userRoleService;

    public int deleteById(String id) {
        return roleMapper.deleteById(id);
    }

    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    public Role selectById(String id) {
        return roleMapper.selectById(id);
    }

    public List<Role> selectListByIds(List<String> idList){
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<Role>().lambda().in(Role::getId, idList));
        return roleList;
    }

    public List<Role> selectListByUserId(String userId){

        //查询用户角色信息
        List<UserRole> userRoleList = userRoleService.selectByUserId(userId);
        if(!CollectionUtils.isEmpty(userRoleList)){

            List<String> idList = new ArrayList<String>(userRoleList.size());
            for(UserRole ur : userRoleList){
                idList.add(ur.getId());
            }

            //查询角色信息并返回结果
            List<Role> roleList = selectListByIds(idList);
            return roleList;
        }

        return null;
    }
}
