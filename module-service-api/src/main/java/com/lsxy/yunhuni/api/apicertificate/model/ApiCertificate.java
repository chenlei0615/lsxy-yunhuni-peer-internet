package com.lsxy.yunhuni.api.apicertificate.model;

import com.lsxy.framework.api.base.IdEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * api凭证
 * Created by liups on 2016/6/29.
 */
@Entity
@Where(clause = "deleted=0")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema="db_lsxy_bi_yunhuni",name = "tb_bi_api_cert")
public class ApiCertificate extends IdEntity {
    public static int TYPE_PRIMARY_ACCOUNT = 1;
    public static int TYPE_SUBACCOUNT = 2;

    private String tenantId;      //租户
    private Integer type;   //类型：1、主账号，2、子账号
    private String certId;      //凭证ID
    private String secretKey;   //凭证密钥
    @Transient
    private List<CertAccountQuota> quotas;

    @Column(name = "tenant_id")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Column(name = "cert_id")
    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    @Column(name = "secret_key")
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Transient
    public List<CertAccountQuota> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<CertAccountQuota> quotas) {
        this.quotas = quotas;
    }
}
