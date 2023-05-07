package com.poly.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.poly.dao.HistoryDao;
import com.poly.dao.impl.HistoryDaoImpl;
import com.poly.entity.History;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.HistoryService;
import com.poly.service.VideoSevice;

public class HistoryServiceImpl implements HistoryService{

	private HistoryDao dao;
	private VideoSevice videoService = new VideoServiceImpl();
	
	public HistoryServiceImpl() {
		dao = new HistoryDaoImpl();
	}

	@Override
	public List<History> findByUser(String username) {
		return dao.findByUser(username);
	}

	@Override
	public List<History> findByUserAndIsLiked(String username) {
		return dao.findByUserAndIsLiked(username);
	}

	@Override
	public History findByUserIdAndVideoId(Integer userId, Integer videoId) {
		return dao.findByUserIdAndVideoId(userId, videoId);
	}

	@Override
	public History create(User user, Video video) {
		History existHistory = dao.findByUserIdAndVideoId(user.getId(), video.getId()); //kiểm tra history nó đã tồn tại chưa
		if(existHistory == null) { //nếu chưa tồn tại 
			existHistory = new History();
			existHistory.setUsers(user);
			existHistory.setVideo(video);
			existHistory.setViewedDate(new Timestamp(System.currentTimeMillis()));
			existHistory.setIsLiked(Boolean.FALSE);
			return dao.create(existHistory);
		}
		return existHistory; //nếu tồn tại rồi thì return cái đã tồn tại
	}

	@Override
	public boolean updateLikeOrUnlike(User user, String hrefVideo) {
		Video video = videoService.findByHref(hrefVideo);
		History existHistory = findByUserIdAndVideoId(user.getId(), video.getId());
		
		if(existHistory.getIsLiked() == Boolean.FALSE) {
			existHistory.setIsLiked(Boolean.TRUE);
			existHistory.setLikedDate(new Timestamp(System.currentTimeMillis()));
		}else {
			existHistory.setIsLiked(Boolean.TRUE);
			existHistory.setLikedDate(null);
		}
		History updatedHistory =dao.update(existHistory);
		return updatedHistory != null ? true :false;
	}

}
