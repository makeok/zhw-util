package com.zhw.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class MyHttpSessionBindingListener implements HttpSessionBindingListener{

    //所谓对session进行数据绑定，就是调用session.setAttribute()
	@Override
    public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();

        // 第一次使用前，需要初始化    

    }
	//所谓对session进行数据绑定，就是调用session.removeAttribute()
	@Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();

     
    }
}

