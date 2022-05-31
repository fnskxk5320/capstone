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

public class SignUpController implements CommandHandler {

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		User userParam = new User();
		userParam.setUserId(userId);
		userParam.setPassword(password);
		userParam.setUserName(userName);
		UserMapperRepository userMapperRepository = new UserMapperRepository();
		if(userMapperRepository.isSignUp(userId) == 1) {
			response.addHeader("result", "failure");
			return false;
		}
		userMapperRepository.SignUp(userParam);
		response.addHeader("result", "success");
		return true;
	}
}
