package com.cyb.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 事务辅助类
 * @author CYB
 */
@Component
public class TransactionUtil {

    /**
     * 回滚当前事务
     */
    public void rollback(){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
