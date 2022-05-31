package CapProject.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;

public class UpdateSettedTemperatureController implements CommandHandler{
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String houseName = request.getParameter("houseName");
		String settedTemperature = request.getParameter("settedTemperature");
		House house = new House();
		house.setUserId(userId);
		house.setHouseName(houseName);
		house.setSettedTemperature(settedTemperature);
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		houseMapperRepository.updateSettedTemperature(house);
		return true;
	}
}
