package com.cyb.authority.domain;

import lombok.Data;

@Data
public class UserRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.id
     *
     * @mbg.generated Sat May 04 15:10:29 CST 2019
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.user_id
     *
     * @mbg.generated Sat May 04 15:10:29 CST 2019
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.role_id
     *
     * @mbg.generated Sat May 04 15:10:29 CST 2019
     */
    private String roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.remarks
     *
     * @mbg.generated Sat May 04 15:10:29 CST 2019
     */
    private String remarks;

}