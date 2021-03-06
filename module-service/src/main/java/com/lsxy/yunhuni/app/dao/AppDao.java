package com.lsxy.yunhuni.app.dao;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.yunhuni.api.app.model.App;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用查询dao
 * Created by liups on 2016/6/29.
 */
public interface AppDao extends BaseDaoInterface<App, Serializable> {
    int countByDeletedAndCreateTimeBetween(boolean deleted, Date d1, Date d2);
    long countByTenantIdAndName(String tenantId,String name);
}
