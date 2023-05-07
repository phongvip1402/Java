package com.poly.service.impl;

import java.util.List;

import com.poly.dao.VideoDao;
import com.poly.dao.impl.VideoDaoImpl;
import com.poly.entity.Video;
import com.poly.service.VideoSevice;

//Theo nguyên tắc service gọi xuống Dao
public class VideoServiceImpl implements VideoSevice{

	private VideoDao dao;
	
	
	public VideoServiceImpl() {
		dao = new VideoDaoImpl();
	}

	@Override
	public Video findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Video findByHref(String href) {
		return dao.findByHref(href);
	}

	@Override
	public List<Video> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Video> findAll(int pageNumber, int pageSize) {
		return dao.findAll(pageNumber,pageSize);
	}

	@Override
	public Video create(Video entity) {
		entity.setIsActive(Boolean.TRUE);
		entity.setViews(0);
		entity.setShare(0);
		return dao.create(entity);
	}

	@Override
	public Video update(Video entity) {
		return  dao.update(entity);
	}

	@Override
	public Video delete(String href) {
		Video entity =dao.findByHref(href);
		entity.setIsActive(Boolean.FALSE);
		return dao.update(entity); //Không xóa trực tiếp mà chỉ cần cần set nó không tồn tại
	}

}
