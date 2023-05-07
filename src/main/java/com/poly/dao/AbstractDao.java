package com.poly.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.poly.util.JpaUtil;
//Mục đích: Chứa các hàm xử lý query
public class AbstractDao<T> {

	public static final EntityManager entityManager = JpaUtil.getEntityManager();
	
	@SuppressWarnings("deprecation")
	@Override
	protected void finalize() throws Throwable {
		entityManager.close();
		super.finalize();
	}
	
	public T findById(Class<T> clazz, Integer id) {
		return entityManager.find(clazz, id);
	}
	
	public List<T> findAll(Class<T> clazz, boolean existIsActive) {
		String entityName = clazz.getSimpleName(); // Lấy được tên class
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o FROM ").append(entityName).append(" o");
		if((existIsActive == true)) {
			sql.append(" WHERE isActive = 1");
		}
		TypedQuery<T> query = entityManager.createQuery(sql.toString(),clazz);
		return query.getResultList();  //getResultList() để truy vấn danh sách thực thể
	}
	
	public List<T> findAll(Class<T> clazz, boolean existIsActive, int pageNumber, int pageSize) {
		String entityName = clazz.getSimpleName(); // Lấy được tên class
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o FROM ").append(entityName).append(" o ");
		if((existIsActive == true)) {
			sql.append(" WHERE isActive = 1");
		}
		TypedQuery<T> query = entityManager.createQuery(sql.toString(),clazz);
		 query.setFirstResult((pageNumber - 1) * pageSize);     //vị trí bắt đầu
		query.setMaxResults(pageSize);//số lượng thực thể tối đa
		return query.getResultList();  //getResultList() để truy vấn danh sách thực thể
	}
	
	public T findOne(Class <T> clazz, String sql, Object ... params) { //Object ... param : tham số có độ dài biến đổi
		TypedQuery<T> query =entityManager.createQuery(sql,clazz);
		for(int i = 0; i< params.length ; i++) {
			query.setParameter(i, params[i]);
		}
		List<T> result = query.getResultList(); // Khi trả về null sẽ không bị lỗi so với dùng single list
		if(result.isEmpty()) {
			return null;
		}
		return result.get(0); // nếu tìm thấy thì phần tử thường nằm ở vị trí đầu tiên
	}
	
	public List<T> findMany(Class <T> clazz, String sql, Object ... params) { //Object ... param : tham số có độ dài biến đổi
		TypedQuery<T> query =entityManager.createQuery(sql,clazz);
		for(int i = 0; i< params.length ; i++) {
			query.setParameter(i, params[i]);
		}
			return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findManyByNativeQuery(Class <T> clazz, String sql, Object ... params) { //Object ... param : tham số có độ dài biến đổi
		Query query =entityManager.createNativeQuery(sql,clazz);
		for(int i = 0; i< params.length ; i++) {
			query.setParameter(i, params[i]);
		}
			return query.getResultList();
	}
	
	public T create (T entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			System.out.println("Create succesed!!");
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();//Nếu lỗi
			System.out.println("Cannot insert entity "+ entity.getClass().getSimpleName() + " toDB");
			throw new RuntimeException();
		}
	}
	
	public T update (T entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(entity);
			entityManager.getTransaction().commit();
			System.out.println("Update succesed!!");
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();//Nếu lỗi
			System.out.println("Cannot update entity "+ entity.getClass().getSimpleName() + " toDB");
			throw new RuntimeException();
		}
	}
	public T delete (T entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
			System.out.println("Delete succesed!!");
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();//Nếu lỗi
			System.out.println("Cannot delete entity "+ entity.getClass().getSimpleName() + " toDB");
			throw new RuntimeException();
		}
	}
	
}
