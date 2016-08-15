package com.lsxy.app.oc.rest.stastistic;


import com.lsxy.app.oc.base.AbstractRestController;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.framework.core.utils.StringUtil;
import com.lsxy.framework.web.rest.RestResponse;
import com.lsxy.yunhuni.api.session.model.CallSession;
import com.lsxy.yunhuni.api.session.service.VoiceCdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 详单查询
 * Created by zhangxb on 2016/7/6.
 */
@RequestMapping("/tenant")
@RestController
public class BillDetailController extends AbstractRestController {
    @Autowired
    VoiceCdrService voiceCdrService;
    /**
     * 会话详单查询
     * @param pageNo 第几页
     * @param pageSize 每页记录数
     * @param time 时间
     * @param appId 应用id
     * @param type 选择类型
     * @return total 总计数，page分页数
     */
    @RequestMapping(value = "/{uid}/session" ,method = RequestMethod.GET)
    @ResponseBody
    public RestResponse call(@PathVariable String uid,@RequestParam Integer type,@RequestParam String time, @RequestParam(required=false)String appId,
                             @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize
                             ){
        Map re = new HashMap();
        RestResponse restResponse = null;
        if(StringUtil.isNotEmpty(time)&&StringUtil.isNotEmpty(appId)&&type!=0){
            //获取分页数据
            Page page = voiceCdrService.pageList(pageNo,pageSize,type,uid,time,appId);
            re.put("page",page);
            if(CallSession.TYPE_VOICE_VOICECODE == type){//语音验证码
                re.put("total",page.getTotalCount());
            }else{
                Map map = voiceCdrService.sumCost(type,uid,time,appId);
                if(CallSession.TYPE_VOICE_RECORDING == type) {//录音
                    re.put("total",map.get("size"));
                }else{
                    re.put("total",map.get("cost"));
                }
            }
            restResponse = RestResponse.success(re);
        }else{
            restResponse = RestResponse.failed("0","上传参数错误");
        }
        return restResponse;
    }

}
