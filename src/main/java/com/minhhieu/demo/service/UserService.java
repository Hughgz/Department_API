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

import com.minhhieu.demo.model.PageDTO;
import com.minhhieu.demo.model.SearchDTO;
import com.minhhieu.demo.model.UserDTO;
import com.minhhieu.demo.entity.User;
import com.minhhieu.demo.reponsitory.UserRepo;
import jakarta.transaction.Transactional;


@Service
public class UserService {
	@Autowired
	UserRepo repo;
	
	@Transactional
	public void create(UserDTO userDTO) {
		// copy từ user sang userdto sử dụng thư viện modelmaper cài ở ngoài vào file pom
		User user = new ModelMapper().map(userDTO,User.class);
		repo.save(user);
	}
	public List<UserDTO> getAll() {
		List<User> users = repo.findAll();
		
		// java 8. Chuyển đổi 1 list sang 1 list mới.
		return users.stream().map(u -> convert(u)).collect(Collectors.toList());
	}
	public void delete(int id) {
		repo.deleteById(id);
	}
	public void update(UserDTO u) {
		User currentUser = repo.findById(u.getId()).orElse(null);
		if(currentUser != null) {
			currentUser.setName(u.getName());
			currentUser.setAge(u.getAge());
			
			repo.save(currentUser);
		}
	}
	
	public UserDTO findById(int id) {
		User user = repo.findById(id).orElse(null);
		if(user != null) {
			return convert(user);
		}
		return null;
	}
	public PageDTO<List<UserDTO>> search(SearchDTO searchDTO) {
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
		PageRequest pr= PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<User> page = repo.searchByName("%" + searchDTO.getName() + "%", pr);
		PageDTO<List<UserDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		
		List<UserDTO> userDto = page.get().map(u -> convert(u)).collect(Collectors.toList());
		pageDTO.setData(userDto);
		return pageDTO;
		
	}
	public UserDTO convert(User user) {
			return new ModelMapper().map(user, UserDTO.class);
	}
}
