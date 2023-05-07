package com.poly.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poly.constant.SessionAttr;
import com.poly.entity.History;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.HistoryService;
import com.poly.service.VideoSevice;
import com.poly.service.impl.HistoryServiceImpl;
import com.poly.service.impl.VideoServiceImpl;

/**
 * Servlet implementation class VideoController
 */
@WebServlet("/video")
public class VideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private VideoSevice videoService = new VideoServiceImpl();
       private HistoryService historyService = new HistoryServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionParam =request.getParameter("action");
		String href = request.getParameter("id");
		HttpSession session = request.getSession();
		
		switch (actionParam) {
		case "watch": 
			doGetWatch(session,href,request,response);
			break;
		case "like": 
			doGetLike(session,href,request,response);
			break;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	//localhost:8080/Java-Servlet-ASM/video?action=watch&id={href}
	private void doGetWatch(HttpSession session, String href, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Video video = videoService.findByHref(href);
		request.setAttribute("video", video);
		User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
		if(currentUser != null) {
			History history = historyService.create(currentUser, video);
			request.setAttribute("flagLikeBtn", history.getIsLiked()); //cờ để đánh dấu true or false
		}
		request.getRequestDispatcher("/views/user/video-detail.jsp").forward(request, response);
	}
	
	
	//localhost:8080/Java-Servlet-ASM/video?action=like&id={href}
	private void doGetLike(HttpSession session, String href, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json"); // Ajax trả về data có dạng json
		User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
		boolean result =historyService.updateLikeOrUnlike(currentUser, href);
		if(result == true) {
			response.setStatus(204); //succeed but no response data 
		}else {
			response.setStatus(400);
		}
	}
	
}
