package com.lsxy.app.oc.rest.config;

import com.lsxy.app.oc.base.AbstractRestController;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.framework.web.rest.RestResponse;
import com.lsxy.yunhuni.api.config.model.LineGateway;
import com.lsxy.yunhuni.api.config.service.LineGatewayService;
import com.lsxy.yunhuni.api.resourceTelenum.service.ResourceTelenumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangxb on 2016/10/26.
 */
@Api(value = "号码管理", description = "配置中心相关的接口" )
@RequestMapping("/config/telnum")
@RestController
public class ResourceTelenumController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceTelenumController.class);
    @Autowired
    ResourceTelenumService resourceTelenumService;
    @Autowired
    LineGatewayService lineGatewayService;
    @RequestMapping(value = "/plist",method = RequestMethod.GET)
    @ApiOperation(value = "获取分页数据")
    public RestResponse pList(
            @ApiParam(name = "pageNo",value = "第几页")  @RequestParam(defaultValue = "1")Integer pageNo,
            @ApiParam(name = "pageSize",value = "每页记录数")  @RequestParam(defaultValue = "20")Integer pageSize,
            @ApiParam(name = "operator",value = "运营商") @RequestParam(required = false)String operator,
            @ApiParam(name = "number",value = "手机号码")@RequestParam(required = false) String number
    ){
        Page page= resourceTelenumService.getPage(pageNo,pageSize,operator,number);
        return RestResponse.success(page);
    }
    @RequestMapping(value = "/notline/plist/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取可透传号码")
    public RestResponse pListByLine(
            @ApiParam(name = "id",value = "线路id")  @PathVariable String id,
            @ApiParam(name = "pageNo",value = "第几页")  @RequestParam(defaultValue = "1")Integer pageNo,
            @ApiParam(name = "pageSize",value = "每页记录数")  @RequestParam(defaultValue = "20")Integer pageSize,
            @ApiParam(name = "operator",value = "运营商") @RequestParam(required = false)String operator,
            @ApiParam(name = "number",value = "手机号码")@RequestParam(required = false) String number
    ){
        LineGateway lineGateway = lineGatewayService.findById(id);
        if(lineGateway==null|| StringUtils.isEmpty(lineGateway.getId())){
            return RestResponse.failed("0000","线路不存在");
        }
        Page page= resourceTelenumService.getPageByNotLine(id,lineGateway.getAreaCode(),pageNo,pageSize,operator,number);
        return RestResponse.success(page);
    }
}
