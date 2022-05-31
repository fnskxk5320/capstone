package CapProject.repository;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import CapProject.model.vo.House;
import CapProject.model.vo.UpdateHouse;
import CapProject.repository.mapper.HouseMapper;

public class HouseMapperRepository extends AbstractRepository{
	public void insertHouse(House house) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			sqlSession.getMapper(HouseMapper.class).insertHouse(house);
			sqlSession.commit();
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
	}
	
	public ArrayList<String> getHouseNames(String userId) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		ArrayList<String> result = new ArrayList<>();
		try {
			result = sqlSession.getMapper(HouseMapper.class).getHouseNames(userId);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
		return result;
	}
	
	public House getHouse(House house) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		House result = new House();
		try {
			result = sqlSession.getMapper(HouseMapper.class).getHouse(house);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
		return result;
	}
	public int getOrder(String houseId) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		int result = -1;
		try {
			result = Integer.parseInt(sqlSession.getMapper(HouseMapper.class).getOrder(houseId));
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
		return result;
	}
	public void updateStatus(House house) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			sqlSession.getMapper(HouseMapper.class).updateStatus(house);
			sqlSession.commit();
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
	}
	public void updateOrder(House house) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			sqlSession.getMapper(HouseMapper.class).updateOrder(house);
			sqlSession.commit();
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
	}
	public void updateHouseName(UpdateHouse updateHouse) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			sqlSession.getMapper(HouseMapper.class).updateHouseName(updateHouse);
			sqlSession.commit();
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
	}
	public void updateSettedTemperature(House house) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			sqlSession.getMapper(HouseMapper.class).updateSettedTemperature(house);
			sqlSession.commit();
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
	}

	public House getHouseByHouseId(String houseId) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		House result = new House();
		try {
			result = sqlSession.getMapper(HouseMapper.class).getHouseByHouseId(houseId);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
		return result;
	}
	public String getSettedTemperature(String houseId) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		String result = "";
		try {
			result = sqlSession.getMapper(HouseMapper.class).getSettedTemperature(houseId);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
		return result;
	}
}
