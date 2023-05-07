package com.poly.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory factory;

	public static EntityManager getEntityManager() {
		if(factory == null || factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("asmjava4"); //nạp persistence.xml và tạo EntityManagerFactory
		}
		return factory.createEntityManager(); //Tạo EntityManager và chuẩn bị lập trình CSDL
	}
	
	public static void shutDown() {
		if(factory != null && factory.isOpen()) {
			factory.close();
		}
		factory=null;
	}
}
