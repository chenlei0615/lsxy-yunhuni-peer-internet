package com.lsxy.framework.tenant.service;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.framework.api.tenant.model.RealnameCorp;
import com.lsxy.framework.api.tenant.service.RealnameCorpService;
import com.lsxy.framework.base.AbstractService;
import com.lsxy.framework.tenant.dao.RealnameCorpDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxb on 2016/6/29.
 * 租户实现类
 */
@Service
public class RealnameRocpServiceImpl extends AbstractService<RealnameCorp> implements RealnameCorpService {
    private static final Logger logger = LoggerFactory.getLogger(RealnameRocpServiceImpl.class);
    @Autowired
    private RealnameCorpDao realnameCorpDao;

    @Override
    public BaseDaoInterface<RealnameCorp, Serializable> getDao() {
        return realnameCorpDao;
    }


    @Override
    public List<RealnameCorp> findByTenantId(String tenantId) {
        try {
            return realnameCorpDao.findByTenantId(tenantId);
        }catch(Exception e){
            logger.error("RealnameRocpServiceImpl.findByTenantId:{}",e);
            return null;
        }
    }

    @Override
    public List<RealnameCorp> list(String tenantId, int status) {
        String hql = "FROM RealnameCorp obj WHERE obj.tenant.id=?1 and obj.status=?2 ORDER BY obj.lastTime  DESC";
        List<RealnameCorp> list = this.findByCustomWithParams(hql, tenantId,status);
        return list;
    }

    @Override
    public RealnameCorp findByTenantIdAndStatus(String tenantId, int status) {
        try {
            return realnameCorpDao.findByTenantIdAndStatus(tenantId,status);
        }catch(Exception e){
            logger.error("RealnameRocpServiceImpl.findByTenantIdAndStatus:{}",e);
            return null;
        }
    }

    @Override
    public RealnameCorp findByTenantIdNewest(String tenantId) {
        String hql = "from RealnameCorp obj where obj.tenant.id=?1 order by obj.createTime desc";
        List<RealnameCorp> ps = this.findByCustomWithParams(hql, tenantId);
        if(ps != null && ps.size()>0){
            return ps.get(0);
        }
        return null;
    }
}
