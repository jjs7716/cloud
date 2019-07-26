package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录过滤器
 */

public class LoginFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.强制类型转换
        HttpServletRequest request= (HttpServletRequest) req;
        //2.判断是否是登录页面
        String uri = request.getRequestURI();
        if(uri.contains("index")){
            chain.doFilter(req, resp);
        }else {
            Object loginUser = request.getSession().getAttribute("user");
            if(loginUser!=null||uri.contains("/login")||uri.contains("/code")||uri.contains("/fonts")||uri.contains(".js")||uri.contains("/static")||uri.contains(".css")||uri.contains("/regist")){
                chain.doFilter(req, resp);
            }else{
                request.getSession().setAttribute("error","未登录,请先登录!");
                request.getRequestDispatcher("/index.jsp").forward(req,resp);
                return;
            }
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
