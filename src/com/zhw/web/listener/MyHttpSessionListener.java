package com.zhw.web.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zhw.core.log.Syslog;

public class MyHttpSessionListener implements HttpSessionListener {

	//创建会话
	@Override
    public void sessionCreated(HttpSessionEvent event) {
		Syslog.info("sessionCreated..."+event.getSession().getId()+"..."+new Date());
    }

    // 结束会话
	@Override
    public void sessionDestroyed(HttpSessionEvent event) {
		Syslog.info("sessionDestroyed..."+event.getSession().getId()+"..."+new Date());
    }
  
}

