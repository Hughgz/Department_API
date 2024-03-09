package com.minhhieu.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minhhieu.demo.model.DepartmentDTO;
import com.minhhieu.demo.model.PageDTO;
import com.minhhieu.demo.model.SearchDTO;
import com.minhhieu.demo.model.UserDTO;
import com.minhhieu.demo.service.DepartmentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/department")
public class DepartmantController {
	@Autowired
	DepartmentService service;
	
	@GetMapping("/new")
	public String addDepartment(Model model) {
		model.addAttribute("department",new DepartmentDTO());
		return "new-department.html";
	}
	@PostMapping("/new")
	public String addNewDepartment(@Valid @ModelAttribute("department") DepartmentDTO departmentDTO,
			 BindingResult bindingResult) throws IllegalStateException, IOException {
		if(bindingResult.hasErrors()) {
			return "new-department.html";
		}
		service.create(departmentDTO);
		return "redirect:/department/search";
	}
	@GetMapping("/delete")
	public String deleteDepartment(@RequestParam("id") int id) {
		service.delete(id);
		return "redirect:/department/search	";
	}
	
	@GetMapping("/search")
	public String departmentList(
			@Valid @ModelAttribute("searchDTO") SearchDTO searchDTO, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
	        return "department-list.html";
	    }
		PageDTO<List<DepartmentDTO>> pageUsers = service.search(searchDTO);
		model.addAttribute("departmentList", pageUsers.getData());
		model.addAttribute("totalPage", pageUsers.getTotalPages());
		model.addAttribute("totalElement", pageUsers.getTotalElements());
		model.addAttribute("searchdto", searchDTO);
			
        return "department-list.html";
	}
	
	@GetMapping("/edit")
	public String editDepartment(@RequestParam("id") int id, 
			HttpServletRequest rq) {
		DepartmentDTO departmentDTO = service.findById(id);
		rq.setAttribute("department", departmentDTO);
		return "department-edit.html";
	}
	@PostMapping("/edit")
	public String edit(@ModelAttribute DepartmentDTO departmentDTO) {
		service.update(departmentDTO);
		return "redirect:/department/search";
	}
}
