package com.lsxy.yunhuni.api.resourceTelenum.service;

import com.lsxy.framework.api.base.BaseService;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.yunhuni.api.resourceTelenum.model.ResourceTelenum;

import java.util.Date;
import java.util.List;

/**
 * 全局号码资源service
 * Created by zhangxb on 2016/7/1.
 */
public interface ResourceTelenumService extends BaseService<ResourceTelenum> {
    /**
     * 从号码池中获取闲置的号码
     * @param count
     * @param areaId 区域ID
     * @return
     */
    List<String> getFreeTeleNum(int count,String areaId);

    /**
     * 根据号码从号码池中获取号资源
     * @param telNumber
     * @return
     */
    ResourceTelenum findByTelNumber(String telNumber);
    /**
     * 清除过期的号码资源和租户的关系
     * @param expireTime 过期时间
     */
    void cleanExpireResourceTelnum(Date expireTime);

    /**
     * 根据区域获取一个空闲的，没有绑定租户的号码
     * @return
     */
    ResourceTelenum findOneFreeNumber(String areaId);

    /**
     * 根据呼叫URI查找号码
     * @param uri
     * @return
     */
    String findNumByCallUri(String uri);

    /**
     * 获取分页信息
     * @param pageNo
     * @param pageSize
     * @param operator
     * @param number
     * @return
     */
    Page<ResourceTelenum> getPage(Integer pageNo, Integer pageSize, String operator, String number);
    /**
     * 获取分页信息
     * @return
     */
    Page<ResourceTelenum> getPageByNotLine(String id,Integer pageNo, Integer pageSize, String operator, String number);
}
