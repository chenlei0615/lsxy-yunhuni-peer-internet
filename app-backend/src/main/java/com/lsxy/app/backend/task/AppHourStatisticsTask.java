package com.lsxy.app.backend.task;

import com.lsxy.framework.api.statistics.service.AppHourService;
import com.lsxy.framework.core.utils.DateUtils;
import com.lsxy.yunhuni.api.app.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * App小时统计
 * Created by zhangxb on 2016/7/29.
 */
@Component
public class AppHourStatisticsTask {
    private static final Logger logger = LoggerFactory.getLogger(AppHourStatisticsTask.class);
    @Autowired
    AppHourService appHourService;
    @Scheduled(cron="0/10 0 * * * ?")
    public void app(){
        long startTime = System.currentTimeMillis();
        logger.info("应用指标小时统计任务开启，当前时间" + DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        //租户 应用 运营商 地区 业务类型 应用上线个数增量/总量 应用未上线个数增量/总量 应用总个数增量/总量
        String partten = "yyyy-MM-dd";
        Date date=new Date();
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        int hour24 = cale.get(Calendar.HOUR_OF_DAY);
        Date date1 = DateUtils.parseDate(DateUtils.getDate(date,partten),partten);
        appHourService.hourStatistics(date1,hour24);
        long endTime = System.currentTimeMillis();
        logger.info("应用指标小时统计任务结束，当前时间" + DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")+" ,花费时间为："+(endTime-startTime)+"毫秒");
    }
}
