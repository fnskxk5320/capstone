package CapProject.model.command;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;
import CapProject.repository.UserMapperRepository;

public class GetSettedTemperatureController implements CommandHandler {
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String houseId = request.getParameter("houseId");
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		String result = houseMapperRepository.getSettedTemperature(houseId);
		if(result.equals("")) {
			response.addHeader("result", "failure");
			return true;
		}
		System.out.println("설정온도 읽는중");
		response.addHeader("result", "success");
		response.addHeader("settedTemperature",result);
		return true;
	}

}
