package com.cyb.authority.domain;

import lombok.Data;

@Data
public class User {

    private String id;

    private String name;

    private String userName;

    private String password;

    private String phone;

    private String email;

    private int sex;

    private String address;
    
    private String image;
    
    private String introduce;

}