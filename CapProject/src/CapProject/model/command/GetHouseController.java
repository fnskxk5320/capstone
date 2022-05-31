package CapProject.model.command;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;
import CapProject.repository.UserMapperRepository;

public class GetHouseController implements CommandHandler {
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String houseName = request.getParameter("houseName");
		House house = new House();
		house.setUserId(userId);
		house.setHouseName(houseName);
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		house = houseMapperRepository.getHouse(house);
		if(house == null) {
			response.addHeader("result", "failure");
			return true;
		}
		System.out.println("하우스 정보 읽는중");
		System.out.println("오더"+house.getOpenerOrder());
		response.addHeader("result", "success");
		response.addIntHeader("houseId",house.getHouseId());
		response.addHeader("houseName",house.getHouseName());
		response.addHeader("temperature",house.getTemperature());
		response.addHeader("natrium",house.getNatrium());
		response.addHeader("phosphorus",house.getPhosphorus());
		response.addHeader("kalium",house.getKalium());
		response.addIntHeader("order",house.getOpenerOrder());
		response.addHeader("settedTemperature", house.getSettedTemperature());
		return true;
	}

}
