package com.poly.service;

import java.util.List;

import com.poly.entity.User;

public interface UserService {
	User findById(Integer id);

	User findByEmail(String email);

	User findByUserName(String username);

	User login(String username, String password);
	
	User resetPassword(String email);
	
	List<User> findAll();

	List<User> findAll(int pageNumber, int pageSize);

	User register(String username, String password, String email);

	User update(User entity);

	User delete(String username);
}
