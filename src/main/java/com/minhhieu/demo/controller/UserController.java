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
import org.springframework.web.bind.annotation.RequestParam;
import com.minhhieu.demo.model.PageDTO;
import com.minhhieu.demo.model.SearchDTO;
import com.minhhieu.demo.model.UserDTO;
import com.minhhieu.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/user/list")
	public String userList(Model model) {
		List<UserDTO> userDTOs = userService.getAll();
		model.addAttribute("userList", userDTOs);
		model.addAttribute("searchDTO", new SearchDTO());
		return "user-list.html";
	}
	@GetMapping("/user/new")
	public String addUser(Model model) {
		model.addAttribute("user",new UserDTO());
		return "user.html";
	}
	@PostMapping("/user/new")
	public String addNewUser(@Valid @ModelAttribute("user") UserDTO userDTO,
			 BindingResult bindingResult) throws IllegalStateException, IOException {
		if(bindingResult.hasErrors()) {
			return "user.html";
		}
		if(!userDTO.getUploadFile().isEmpty()) {
			String fileName = userDTO.getUploadFile().getOriginalFilename();
			File saveFile  = new File("D:/" + fileName);
			userDTO.getUploadFile().transferTo(saveFile);
			userDTO.setAvatarUrl(fileName);
		}
		userService.create(userDTO);
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/delete")
	public String deleteUser(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/search")
	public String userList(
			@Valid @ModelAttribute("searchDTO") SearchDTO searchDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
	        return "user-list.html";
	    }
		PageDTO<List<UserDTO>> pageUsers = userService.search(searchDTO);
		model.addAttribute("userList", pageUsers.getData());
		model.addAttribute("totalPage", pageUsers.getTotalPages());
		model.addAttribute("totalElement", pageUsers.getTotalElements());
		model.addAttribute("searchdto", searchDTO);
			
		return "user-list.html";
	}
	
	@GetMapping("/user/edit")
	public String editUser(@RequestParam("id") int id, 
			HttpServletRequest rq) {
		UserDTO user = userService.findById(id);
		rq.setAttribute("user", user);
		return "user-edit.html";
	}
	@PostMapping("/user/edit")
	public String edit(@ModelAttribute UserDTO userDTO) {
		userService.update(userDTO);
		return "redirect:/user/list";
	}
}
