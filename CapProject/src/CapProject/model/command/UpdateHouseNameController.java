package CapProject.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.model.vo.UpdateHouse;
import CapProject.repository.HouseMapperRepository;

public class UpdateHouseNameController implements CommandHandler{
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String houseName = request.getParameter("houseName");
		String updateHouseName = request.getParameter("updateHouseName");
		UpdateHouse updateHouse = new UpdateHouse();
		updateHouse.setUserId(userId);
		updateHouse.setHouseName(houseName);
		updateHouse.setUpdateHouseName(updateHouseName);
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		houseMapperRepository.updateHouseName(updateHouse);
		return true;
	}
}
