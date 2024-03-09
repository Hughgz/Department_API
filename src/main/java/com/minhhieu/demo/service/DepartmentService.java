package com.minhhieu.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.minhhieu.demo.entity.Department;
import com.minhhieu.demo.model.DepartmentDTO;
import com.minhhieu.demo.model.PageDTO;
import com.minhhieu.demo.model.SearchDTO;
import com.minhhieu.demo.reponsitory.DepartmentRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface DepartmentService {

	void create(DepartmentDTO departmentDTO);

	void update(DepartmentDTO departmentDTO);

	void delete(int id);

	DepartmentDTO findById(int id);

	PageDTO<List<DepartmentDTO>> search(SearchDTO searchDTO);
}

@Service
class DepartmentServiceImp implements DepartmentService {
	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public void create(DepartmentDTO departmentDTO) {
		Department department = new ModelMapper().map(departmentDTO, Department.class);
		// TODO Auto-generated method stub
		departmentRepo.save(department);

	}

	@Override
	@Transactional
	public void update(DepartmentDTO departmentDTO) {
		// TODO Auto-generated method stub
		Department currentDepartment = departmentRepo.findById(departmentDTO.getId()).orElse(null);
		if (currentDepartment != null) {
			currentDepartment.setName(departmentDTO.getName());
			departmentRepo.save(currentDepartment);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		departmentRepo.deleteById(id);
	}

	@Override
	public DepartmentDTO findById(int id) {
		Department department = departmentRepo.findById(id).orElseThrow(NoResultException::new);
		// TODO Auto-generated method stub
		if (department != null) {
			convert(department);
		}
		return null;
	}

	@Override
	public PageDTO<List<DepartmentDTO>> search(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by("name").ascending();
		if(StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		if(searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		if(searchDTO.getSize() == null) {
			searchDTO.setSize(2);
		}
		if(searchDTO.getName() == null) {
			searchDTO.setName("");
		}
		PageRequest pr= PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Department> page = departmentRepo.searchName("%" + searchDTO.getName() + "%", pr);
		PageDTO<List<DepartmentDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		
		List<DepartmentDTO> departmentDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		pageDTO.setData(departmentDTOs);
		return pageDTO;
	}

	public DepartmentDTO convert(Department department) {
		return new ModelMapper().map(department, DepartmentDTO.class);
	}
}