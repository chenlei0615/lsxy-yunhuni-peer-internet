package com.lsxy.app.oc.rest.account;

import com.lsxy.app.oc.base.AbstractRestController;
import com.lsxy.framework.api.tenant.model.RealnameCorp;
import com.lsxy.framework.api.tenant.model.RealnamePrivate;
import com.lsxy.framework.api.tenant.model.Tenant;
import com.lsxy.framework.api.tenant.service.RealnameCorpService;
import com.lsxy.framework.api.tenant.service.RealnamePrivateService;
import com.lsxy.framework.api.tenant.service.TenantService;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.framework.web.rest.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实名认证处理类
 * Created by zhangxb on 2016/8/11.
 */
@Api(value = "实名认证", description = "审核管理相关接口" )
@RequestMapping("/demand")
@RestController
public class AuthController extends AbstractRestController {
    @Autowired
    RealnamePrivateService realnamePrivateService;
    @Autowired
    RealnameCorpService realnameCorpService;
    @Autowired
    TenantService tenantService;
    /**
     * 查找用户下的分页信息
     * @param authStatus await|auditing|unauth
     * @param startTime 开始时间 yyyy-MM-dd
     * @param endTime 结束时间
     * @param type 0个人认证 1企业认证
     * @param search 会员名
     * @param pageNo 第几页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "查找用户下的分页信息")
    @RequestMapping(value = "/member/{authStatus}/list",method = RequestMethod.GET)
    public RestResponse pageList(
            @ApiParam(name = "authStatus",value = "await|auditing|unauth")
            @PathVariable String authStatus,
            @ApiParam(name = "startTime",value = "yyyy-MM-dd")
            @RequestParam(required=false)String startTime,
            @ApiParam(name = "endTime",value = "yyyy-MM-dd")
            @RequestParam(required=false)String endTime,
            @ApiParam(name = "type",value = "0个人认证 1企业认证")
            @RequestParam(required=false)Integer type,
            @ApiParam(name = "search",value = "搜索的会员名")
            @RequestParam(required=false)String search,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "20")Integer pageSize){
        Integer status = null;
        if("await".equals(authStatus)){
            status = 0;
        }else if("auditing".equals(authStatus)){
            status = 1;
        }else if("unauth".equals(authStatus)){
            status = -1;
        }
        Page page = null;
        if(status!=null) {
            page = realnamePrivateService.pageListAuthInfo(status,startTime,endTime,type,search,pageNo,pageSize);
        }
        return RestResponse.success(page);
    }

    /**
     * 根据记录id和认证类型获取详情页面
     * @param id 记录的id
     * @param type 认证类型 0个人1企业
     * @return
     */
    @ApiOperation(value = "根据记录id和认证类型获取详情页面")
    @RequestMapping(value = "/member/detail/{id}",method = RequestMethod.GET)
    public RestResponse pageList(
            @ApiParam(name = "id",value = "消息id")
            @PathVariable String id,
            @ApiParam(name = "type",value = "0个人认证 1实名认证 必填")
            @RequestParam Integer type
    ){
        RestResponse restResponse = null;
        Map map = new HashMap();
        if(Tenant.AUTH_ONESELF==type){//个人
            RealnamePrivate realnamePrivate = null;
            try {
                realnamePrivate = realnamePrivateService.findById(id);
                if(realnamePrivate.getTenant()!=null){
                    List<RealnamePrivate> list = realnamePrivateService.findByTenantId(realnamePrivate.getTenant().getId());
                    map.put("list",list);
                }
            }catch (Exception e){
            }
            map.put("realname",realnamePrivate);
        }else if(Tenant.AUTH_COMPANY==type){//企业
            RealnameCorp realnameCorp = null;
            try{
                realnameCorp = realnameCorpService.findById(id);
                if(realnameCorp.getTenant()!=null){
                    List<RealnameCorp> list = realnameCorpService.findByTenantId(realnameCorp.getTenant().getId());
                    map.put("list",list);
                }
            }catch (Exception e){}
            map.put("realname",realnameCorp);
        }else{
            restResponse = RestResponse.failed("0","类型不存");
        }
        if(restResponse==null){
            restResponse = RestResponse.success(map);
        }
        return restResponse;
    }
    /**
     * 根据实名认证记录id，租户id，实名认证类型，修改后的结果来修改实名认证结果
     * @return
     */
    @ApiOperation(value = "根据实名认证记录id，租户id，实名认证类型，修改后的结果来修改实名认证结果")
    @RequestMapping(value = "/member/edit/{id}",method = RequestMethod.PUT)
    public RestResponse modifyAuthStatus(
            @ApiParam(name = "id",value = "记录id")
             @PathVariable  String id,
             @RequestBody AuthVo authVo){
        Tenant tenant = null;
        RestResponse restResponse = null;
        Integer statusT = null;
        Integer type = authVo.getType();
        Integer status = authVo.getStatus();
        String reason = authVo.getReason();
        if(Tenant.AUTH_ONESELF==type&&(Tenant.AUTH_ONESELF_FAIL==status||Tenant.AUTH_ONESELF_SUCCESS==status)){//个人
            RealnamePrivate realnamePrivate = null;
            try {
                realnamePrivate = realnamePrivateService.findById(id);
                tenant = realnamePrivate.getTenant();
                if(tenant!=null){
                    realnamePrivate.setStatus(status);
                    tenant.setIsRealAuth(status);
                    tenant.setTenantName(realnamePrivate.getName());
                    realnamePrivate.setTenant(tenant);
                    realnamePrivate.setReason(reason);
                    realnamePrivateService.save(realnamePrivate);
                    tenant = tenantService.save(tenant);
                }else{
                    restResponse = RestResponse.failed("0","租户不存在");
                }
            }catch (Exception e){
            }
        }else if(Tenant.AUTH_COMPANY==type&&(Tenant.AUTH_COMPANY_SUCCESS==status||Tenant.AUTH_COMPANY_FAIL==status)){//企业
            RealnameCorp realnameCorp = null;
            try{
                realnameCorp = realnameCorpService.findById(id);
                tenant = realnameCorp.getTenant();
                if(tenant!=null){
                    statusT = tenant.getIsRealAuth();
                    realnameCorp.setStatus(status);
                    if(statusT==Tenant.AUTH_ONESELF_SUCCESS||statusT==Tenant.AUTH_UPGRADE_WAIT||statusT==Tenant.AUTH_UPGRADE_FAIL){
                        if(status==Tenant.AUTH_COMPANY_FAIL){
                            status=Tenant.AUTH_UPGRADE_FAIL;
                        }else if(status==Tenant.AUTH_COMPANY_SUCCESS){
                            status=Tenant.AUTH_UPGRADE_SUCCESS;
                        }
                    }
                    tenant.setTenantName(realnameCorp.getName());
                    tenant.setIsRealAuth(status);
                    realnameCorp.setReason(reason);
                    realnameCorp.setTenant(tenant);
                    realnameCorpService.save(realnameCorp);
                    tenant = tenantService.save(tenant);
                }else{
                    restResponse = RestResponse.failed("0","租户不存在");
                }
            }catch (Exception e){}
        }else{
            restResponse = RestResponse.failed("0","参数值不对");
        }
        if(restResponse==null){
            restResponse = RestResponse.success(tenant.getIsRealAuth());
        }
        return restResponse;
    }
}
