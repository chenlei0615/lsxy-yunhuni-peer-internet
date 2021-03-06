package com.lsxy.yunhuni.api.apicertificate.service;

import com.lsxy.framework.api.base.BaseService;
import com.lsxy.yunhuni.api.apicertificate.model.CertAccountQuota;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by liups on 2017/2/15.
 */
public interface CertAccountQuotaService extends BaseService<CertAccountQuota> {
    String QUOTA_DAY_PREFIX = "quota_day_used"; //当天配额key

    void updateQuotas(String certAccountId,List<CertAccountQuota> quotas);

    List<CertAccountQuota> findByCertAccountId(String id);

    List<CertAccountQuota> findByCertAccountIds(Collection<String> ids);
    List<CertAccountQuota> findByAppId(String appId);
    /**
     * redis中的增量增加
     * @param certAccountId 鉴权账号Id
     * @param date 日期
     * @param incV 时长（秒）或数量（条数）
     * @param type 类型（配额类型）
     */
    void incQuotaUsed(String certAccountId, Date date, Long incV, String type);

    /**
     * 获取实时配额
     * @param certAccountId 鉴权账号Id
     * @param type 类型（配额类型）
     */
    CertAccountQuota getCurrentQuota(String certAccountId , String type);

    /**
     * 获取实时配额
     * @param quota
     * @return
     */
    CertAccountQuota getCurrentQuota(CertAccountQuota quota);

    /**
     * 获取配额使用量
     * @param quota
     * @return
     */
    Long getQuotaUsed(CertAccountQuota quota);

    /**
     * 配额使用量每天统计
     * @return
     */
    void dayStatics(Date date);

    /**
     * 配额是否充足
     * @param certAccountId
     * @return
     */
    boolean isCallQuotaEnough(String certAccountId);

    boolean isQuotaEnough(String certAccountId, String quotaType, Long need);
}
