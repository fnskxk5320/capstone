package CapProject.model.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;

public class InsertHouseController implements CommandHandler {
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int houseId = Integer.parseInt(request.getParameter("houseId"));
		String houseName = request.getParameter("houseName");
		String userId = request.getParameter("userId");
		House house = new House();
		house.setHouseId(houseId);
		house.setHouseName(houseName);
		house.setTemperature("Null");
		house.setNatrium("Null");
		house.setPhosphorus("Null");
		house.setKalium("Null");
		house.setOpenerOrder(-1);
		house.setUserId(userId);
		house.setSettedTemperature("Null");
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		houseMapperRepository.insertHouse(house);
		return true;
	}
}
