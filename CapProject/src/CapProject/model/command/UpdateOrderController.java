package CapProject.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;

public class UpdateOrderController implements CommandHandler{
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String houseName = request.getParameter("houseName");
		int order = Integer.parseInt(request.getParameter("order"));
		System.out.println("변경"+order);
		House house = new House();
		house.setUserId(userId);
		house.setHouseName(houseName);
		house.setOpenerOrder(order);
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		houseMapperRepository.updateOrder(house);
		return true;
	}
}
