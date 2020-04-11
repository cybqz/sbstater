package com.cyb.chat.service;

import com.cyb.chat.server.CybTeamChatWSServer;
import com.cyb.common.r.R;
import java.util.List;

public class CybTeamChatWSServiceImpl implements CybTeamChatWSService {

    private CybTeamChatWSServer cybTeamChatWSServer;

    public CybTeamChatWSServiceImpl (CybTeamChatWSServer cybTeamChatWSServer){
        this.cybTeamChatWSServer = cybTeamChatWSServer;
    }

    @Override
    public R initRegister(String team, List<String> userIdList, List<String> userNameList) {
        return cybTeamChatWSServer.initRegister(team, userIdList, userNameList);
    }

    @Override
    public R addRegister(String team, String userId, String userName) {
        return cybTeamChatWSServer.addRegister(team, userId, userName);
    }
}
