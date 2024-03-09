package com.minhhieu.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SearchDTO {
	//@NotBlank(message = "{not.blank}")
	//@Size(min = 2, max = 10, message = "{size.msg}")
	private String name;		
	
	private Integer currentPage;

	private Integer size;
	
	private String sortedField;
}
