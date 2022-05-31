package CapProject.repository;

import org.apache.ibatis.session.SqlSession;

import CapProject.model.vo.User;
import CapProject.repository.mapper.UserMapper;

public class UserMapperRepository extends AbstractRepository{

	public User login(User user) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		User result = new User();
		try {
			result = sqlSession.getMapper(UserMapper.class).login(user);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
			
		}finally {
			sqlSession.close();
		}
		return result;
	}
	public int isSignUp(String userId) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		int result = 0;
		try {
			result = sqlSession.getMapper(UserMapper.class).isSignUp(userId);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
		}finally {
			sqlSession.close();
		}
		return result;
	}
	public void SignUp(User user) {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			sqlSession.getMapper(UserMapper.class).signUp(user);;
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
		}finally {
			sqlSession.close();
		}
		
	}
	public void SignOut(String userId) {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			sqlSession.getMapper(UserMapper.class).signOut(userId);
		}catch(Exception e){
			System.err.println("예외 발생 : " + e.getMessage());
		}finally {
			sqlSession.close();
		}
	}
}
