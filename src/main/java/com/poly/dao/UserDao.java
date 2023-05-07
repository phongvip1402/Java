package com.poly.dao;

//Mục đích: tạo các phương thức chỉ có trong User
import java.util.List;

import com.poly.entity.User;

public interface UserDao {
	User findById(Integer id);

	User findByEmail(String email);

	User findByUserName(String username);

	User findByUsernameAndPassword(String username, String password);

	List<User> findAll();

	List<User> findAll(int pageNumber, int pageSize);

	User create(User entity);

	User update(User entity);

	User delete(User entity);
}
