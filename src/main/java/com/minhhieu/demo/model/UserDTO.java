package com.minhhieu.demo.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
	
	private int id;
	@NotBlank(message = "{not.blank}")
	private String name;
	@Min(value = 0)
	private int age;
	private String avatarUrl;
	@Size(min = 5, max = 20, message = "{size.msg}")
	@NotBlank(message = "{not.blank}")
	private String username;
	private String password;
	private MultipartFile uploadFile;
	
	private DepartmentDTO departmentDTO;
}