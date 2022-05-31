package CapProject.model.command;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CapProject.model.vo.House;
import CapProject.repository.HouseMapperRepository;
import CapProject.repository.UserMapperRepository;

public class GetHouseNamesController implements CommandHandler{
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("하우스 이름 부르는중");
		String userId = request.getParameter("userId");
		UserMapperRepository userMapperRepository = new UserMapperRepository();
		ArrayList<String> houseNameList = new ArrayList<String>();
		HouseMapperRepository houseMapperRepository = new HouseMapperRepository();
		houseNameList = houseMapperRepository.getHouseNames(userId);
		String result = "";
		Iterator<String> it = houseNameList.iterator();
		while(it.hasNext()) {
			result = result + it.next() + "/";
		}
		response.addHeader("result", result);
		return true;
	}
}
