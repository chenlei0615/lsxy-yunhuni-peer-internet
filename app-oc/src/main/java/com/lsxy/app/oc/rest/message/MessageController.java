package com.lsxy.app.oc.rest.message;

import com.lsxy.app.oc.base.AbstractRestController;
import com.lsxy.framework.api.message.model.AccountMessage;
import com.lsxy.framework.api.message.model.Message;
import com.lsxy.framework.api.message.service.AccountMessageService;
import com.lsxy.framework.api.message.service.MessageService;
import com.lsxy.framework.api.tenant.model.Account;
import com.lsxy.framework.api.tenant.service.AccountService;
import com.lsxy.framework.core.utils.EntityUtils;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.framework.core.utils.StringUtil;
import com.lsxy.framework.web.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhangxb on 2016/8/10.
 */
@RequestMapping("/message")
@RestController
public class MessageController extends AbstractRestController {
    @Autowired
    MessageService messageService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountMessageService accountMessageService;

    /**
     *  根据日期和类型查询消息列表信息
     * @param type 0用户消息 1活动消息
     * @param status 类型 0未上线 1上线 -1下线
     * @param startTime 开始时间 yyyy-MM-dd
     * @param endTime 结束时间
     * @param pageNo 第几页
     * @param pageSize 每页几条数据
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RestResponse pageList(Integer status,@RequestParam(defaultValue = "1")Integer type, String startTime, String endTime, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "20")Integer pageSize){
        RestResponse restResponse=null;
        if(StringUtil.isEmpty(startTime)||StringUtil.isEmpty(endTime)){
            restResponse = RestResponse.failed("0","参数错误");
        }else {
            Page page = messageService.pageList(type,status, startTime, endTime, pageNo, pageSize);
            restResponse = RestResponse.success(page);
        }
        return restResponse;
    }

    /**
     * 根据消息查新消息
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    public RestResponse detail(@PathVariable String id){
        Message message = messageService.findById(id);
        return RestResponse.success(message);
    }
    /**
     * 修改消息 type：1表示活动消息，不需要通知用户 0表示用户消息，status为1是发送消息给用户
     * @param message
     * @return
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public RestResponse modify(@PathVariable String id,Message message){
        Message message1 = messageService.findById(id);
        boolean isSendMsg = false;
        if(message.getStatus()!=null&&message1.getStatus()!=message.getStatus()){
            if(message.getType()==Message.MESSAGE_ACCOUNT&&message.getStatus()==Message.ONLINE) {
                isSendMsg = true;
            }
        }
        RestResponse restResponse = null;
        try {
            EntityUtils.copyProperties(message1, message);
            messageService.save(message1);
            if(isSendMsg){
                sendMessage(message1);
            }
        }catch (Exception e){
            restResponse = RestResponse.failed("0","转换对象失败");
        }
        restResponse = RestResponse.success(message);
        return restResponse;
    }


    /**
     * 发送消息给状态正常的用户
     * @param message
     */
    private  void sendMessage(Message message){
        List<Account> list = accountService.list();
        accountMessageService.insertMultiple(list,message);
    }

}
