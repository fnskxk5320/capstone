package CapProject.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;

public class UpdateStatusController implements CommandHandler{
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int houseId = Integer.parseInt(request.getParameter("houseId"));
		String temperature = request.getParameter("temperature");
		String natrium = request.getParameter("natrium");
		String phosphorus = request.getParameter("phosphorus");
		String kalium = request.getParameter("kalium");
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		House house = new House();
		house.setHouseId(houseId);
		house.setTemperature(temperature);
		house.setNatrium(natrium);
		house.setPhosphorus(phosphorus);
		house.setKalium(kalium);
		houseMapperRepository.updateStatus(house);
		return true;
	}
}
