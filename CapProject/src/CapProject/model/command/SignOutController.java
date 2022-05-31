package CapProject.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.User;
import CapProject.repository.UserMapperRepository;

public class SignOutController implements CommandHandler {

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		UserMapperRepository userMapperRepository = new UserMapperRepository();
		userMapperRepository.SignOut(id);
		response.addHeader("result", "success");
		return true;
	}

}
