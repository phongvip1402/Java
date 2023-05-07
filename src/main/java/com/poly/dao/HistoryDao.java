package com.poly.dao;

import java.util.List;

import com.poly.entity.History;
import com.poly.entity.User;

//Mục đích: Khai báo các query tương tác với entity
public interface HistoryDao {

	List<History> findByUser(String username);
	List<History> findByUserAndIsLiked(String username);
	History findByUserIdAndVideoId(Integer userId, Integer videoId );
	History create(History entity);
	History update(History entity);
	History delete(History entity);
}
