package CapProject.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;
import CapProject.repository.UserMapperRepository;

public class GetOrderController implements CommandHandler{
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String houseId = request.getParameter("HouseId");
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		int order  = houseMapperRepository.getOrder(houseId);
		response.addHeader("result", "success");
		response.addIntHeader("order",order);
		return true;
	}
}
