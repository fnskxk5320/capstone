package CapProject.repository.mapper;

import java.util.ArrayList;
import CapProject.model.vo.House;
import CapProject.model.vo.UpdateHouse;

public interface HouseMapper {
	public int insertHouse(House house);
	public ArrayList<String> getHouseNames(String UserId);
	public House getHouse(House house);
	public House getHouseByHouseId(String houseId);
	public String getOrder(String houseId);
	public String getSettedTemperature(String houseId);
	public int updateStatus(House house);
	public int updateOrder(House house);
	public int updateHouseName(UpdateHouse updateHouse);
	public int updateSettedTemperature(House house);
}
