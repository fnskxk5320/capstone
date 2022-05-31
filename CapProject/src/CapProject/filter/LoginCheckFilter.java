package CapProject.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
//@WebFilter("*.do")
public class LoginCheckFilter implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpSession session = httpServletRequest.getSession();
		String url = ((HttpServletRequest) request).getRequestURI();
		if(url.contains("updateStatus.do") || url.contains("signUp.do") || url.contains("login.do")||url.contains("getOrder.do")||url.contains("getOrder.do")||url.contains("getSettedTemperature.do")) {
			chain.doFilter(request, response);
		}else {
			String isLogin = request.getParameter("userId");
			System.out.println("로그인 필터 작동" + isLogin);
			if(isLogin !=null && !(isLogin.equals(""))) {
				chain.doFilter(request, response);
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.do");
				dispatcher.forward(request, response);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
