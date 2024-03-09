package com.minhhieu.demo.reponsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minhhieu.demo.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer>{
	@Query("SELECT department FROM Department department WHERE department.name LIKE :x")
	Page<Department> searchName(@Param("x") String name, Pageable pageable);
}
