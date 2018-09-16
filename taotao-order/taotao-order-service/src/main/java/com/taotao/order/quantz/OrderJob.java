package com.taotao.order.quantz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 李壮壮 on 2018/9/13.
 */
public class OrderJob{

    @Autowired

    public void execute(){
        //System.out.println("订单已失效");
    }
}
