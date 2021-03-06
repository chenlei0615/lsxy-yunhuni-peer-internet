package com.lsxy.yunhuni.statistics.dao;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.yunhuni.api.statistics.model.VoiceCdrDay;

import java.io.Serializable;
import java.util.Date;

/**
 * 通话记录统计（session统计）日统计DAO
 * Created by zhangxb on 2016/8/1.
 */
public interface VoiceCdrDayDao extends BaseDaoInterface<VoiceCdrDay,Serializable> {
    VoiceCdrDay findFirstByAppIdAndDtAndTenantIdIsNullAndTypeIsNull(String appId, Date currentDay);
}
