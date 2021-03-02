package com.shiro;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义退出过滤器
 *
 * @author GC
 * @date 2020年 01月19日 13:26:52
 */
public class SystemLogoutFilter extends LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据
        Subject subject=getSubject(request,response);
        String redirectUrl=getRedirectUrl(request,response,subject);
        ServletContext context= ContextLoader.getCurrentWebApplicationContext().getServletContext();
        try {
            subject.logout();
            context.removeAttribute("error");
        }catch (SessionException e){
            e.printStackTrace();
        }
        issueRedirect(request,response,redirectUrl);
        return false;
    }
}
