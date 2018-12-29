package com.smart.service;

import com.smart.bean.AccessRecord;
import com.smart.dao.UtilDao;
import com.smart.redis.DateUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Component
public class SystemLogoutFilter extends LogoutFilter {

    @Autowired
    private UtilDao utilDao;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据
        Subject subject=getSubject(request,response);
        Session session = subject.getSession();
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setSessionId(session.getId().toString());
        System.out.println(session.getId().toString());
        String redirectUrl=getRedirectUrl(request,response,subject);
        ServletContext context= request.getServletContext();
        try {
            subject.logout();
            accessRecord.setEndTime(DateUtil.getDate());
            utilDao.logoutTime(accessRecord);
            context.removeAttribute("error");
        }catch (SessionException e){
            e.printStackTrace();
        }
        issueRedirect(request,response,redirectUrl);
        return false;
    }
}
