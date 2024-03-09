package com.minhhieu.demo.model;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentDTO {
	private int id;
	//@NotBlank(message = "{not.blank}")
	private String name;
	
	private Date createAt;
	
	private Date updateAt;
}
