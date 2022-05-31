package CapProject.repository.mapper;

import CapProject.model.vo.User;

public interface UserMapper {
	public int isSignUp(String userId);
	public void signUp(User user);
	public User login(User user);
	public void signOut(String userId);
}
