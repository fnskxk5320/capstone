package CapProject.model.vo;

public class House {
	private int houseId;
	private String houseName;
	private String temperature;
	private String natrium;
	private String phosphorus;
	private String kalium;
	private int openerOrder;
	private String userId;
	private String settedTemperature;
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getNatrium() {
		return natrium;
	}
	public void setNatrium(String natrium) {
		this.natrium = natrium;
	}
	public String getPhosphorus() {
		return phosphorus;
	}
	public void setPhosphorus(String phosphorus) {
		this.phosphorus = phosphorus;
	}
	public String getKalium() {
		return kalium;
	}
	public void setKalium(String kalium) {
		this.kalium = kalium;
	}
	public int getOpenerOrder() {
		return openerOrder;
	}
	public void setOpenerOrder(int openerOrder) {
		this.openerOrder = openerOrder;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSettedTemperature() {
		return settedTemperature;
	}
	public void setSettedTemperature(String settedTemperature) {
		this.settedTemperature = settedTemperature;
	}
}
