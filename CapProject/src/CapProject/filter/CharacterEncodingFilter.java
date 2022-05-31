package CapProject.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
// 이 필터는 모든 경로에 작동되어 서블릿마다 일일이 request.setCharacterEncoding을 할 필요가 없게해준다.
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			System.out.println("인코딩 필터 작동");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			chain.doFilter(request, response);
	}
}
