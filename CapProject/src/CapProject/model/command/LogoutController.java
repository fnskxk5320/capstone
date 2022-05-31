package CapProject.model.command;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.repository.HouseMapperRepository;

public class LogoutController implements CommandHandler{

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		String result = "success";
		response.addHeader("result", result);
		return true;
	}
}
