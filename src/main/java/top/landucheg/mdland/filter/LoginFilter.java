package top.landucheg.mdland.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "loginFilter", value = {"/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String httpMethod = request.getMethod();
        String url = request.getRequestURI();
        String user = (String) session.getAttribute("user");
        if(url.contains("/mdland/static")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if("GET".equals(httpMethod.toUpperCase()) && isLoginUrl(url)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if("/mdland/editor".equals(url) || "/mdland/editor/".equals(url)){
            if("POST".equals(httpMethod.toUpperCase())){
                String userName = request.getParameter("user");
                String password = request.getParameter("upwd");
                if(userName.equals("wooyeemdland") && password.equals("mdland.wooyee!")){
                    session.setAttribute("user", userName + "@" + password + ".com");
                    response.sendRedirect("/mdland/editor");
                    return;
                } else {
                    response.sendRedirect("/mdland/login");
                    return;
                }
            }
            if("GET".equals(httpMethod.toUpperCase())){
                if(user == null || user == ""){
                    response.sendRedirect("/mdland/login");
                    return;
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        if(user == null || user == ""){
            response.sendRedirect("/mdland/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isLoginUrl(String url){
        return "/mdland/login".equals(url) ||
                "/mdland/login/".equals(url) ||
                "/mdland".equals(url) ||
                "/mdland/".equals(url);
    }
}
