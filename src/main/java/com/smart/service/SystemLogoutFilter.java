
package com.smart.service;

import com.smart.bean.AccessRecord;
import com.smart.dao.UtilDao;
import com.smart.redis.DateUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemLogoutFilter extends LogoutFilter {
    @Autowired
    private UtilDao utilDao;

    public SystemLogoutFilter() {
    }

    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        Session session = subject.getSession();
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setSessionId(session.getId().toString());
        System.out.println(session.getId().toString());
        String redirectUrl = this.getRedirectUrl(request, response, subject);
        ServletContext context = request.getServletContext();

        try {
            subject.logout();
            accessRecord.setEndTime(DateUtil.getDate());
            this.utilDao.logoutTime(accessRecord);
            context.removeAttribute("error");
        } catch (SessionException var9) {
            var9.printStackTrace();
        }

        this.issueRedirect(request, response, redirectUrl);
        return false;
    }
}
