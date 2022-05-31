package CapProject.model.command;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.User;
import CapProject.repository.UserMapperRepository;

public class LoginController implements CommandHandler {
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		User userParam = new User();
		userParam.setUserId(userId);
		userParam.setPassword(password);
		UserMapperRepository userMapperRepository = new UserMapperRepository();
		User user = userMapperRepository.login(userParam);
		if(user == null) {
			System.out.println("실패"+userId+" " + password);
			session.invalidate();
			response.addHeader("result", "failure");
			return false;
		}else {
			System.out.println("성공"+userId+ " " + password);
			session.setAttribute("login", userId);
			session.setAttribute("user", user);
			response.addHeader("result", "success");
			response.addHeader("userId", userId);
			response.addHeader("userName", user.getUserName());	
			System.out.println(user.getUserName());
			return true;
		}
	}

}
