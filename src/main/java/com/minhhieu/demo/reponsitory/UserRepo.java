package com.minhhieu.demo.reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minhhieu.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	//tìm theo username
	//= select user where username = username;
	User findByUsername(String username);
	
	// where name = :s
	// phân trang với Pageable
	Page<User> findByName(String s, Pageable page);
	
	@Query("SELECT user FROM User user WHERE user.name LIKE :x")
	Page<User> searchByName(@Param("x")String x, Pageable page);
	
	
	@Query("SELECT user FROM User user WHERE user.name LIKE :x" + " OR user.username LIKE :x")
	List<User> searchByNameAndUsername(@Param("x")String x);
	
	
	
	@Modifying
	@Query("DELETE FROM User u WHERE u.username = :x")
	int deleteUser(@Param("x") String username);
}
