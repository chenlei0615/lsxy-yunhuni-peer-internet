package com.lsxy.call.center.api.opensips.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by liups on 2016/11/24.
 */
@Entity
@Table(schema="opensips",name = "subscriber")
public class Subscriber {
    private Integer id;
    private String username;
    private String domain;
    private String password;
    private String emailAddress;
    private String ha1;
    private String ha1b;
    private String rpid;

    @Id
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name="ha1")
    public String getHa1() {
        return ha1;
    }

    public void setHa1(String ha1) {
        this.ha1 = ha1;
    }

    @Column(name="ha1b")
    public String getHa1b() {
        return ha1b;
    }

    public void setHa1b(String ha1b) {
        this.ha1b = ha1b;
    }

    @Column(name="rpid")
    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }
}
