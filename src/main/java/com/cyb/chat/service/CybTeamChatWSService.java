package com.cyb.chat.service;

import com.cyb.common.R.R;

import java.util.List;

public interface CybTeamChatWSService {

    /**
     * 初始化注册
     *
     * @param team
     * @param userIdList
     * @param userNameList
     * @return
     */
    public R initRegister(String team, List<String> userIdList, List<String> userNameList);

    /**
     * 添加注册
     *
     * @param team
     * @param userId
     * @param userName
     * @return
     */
    public R addRegister(String team, String userId, String userName);
}
