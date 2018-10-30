package com.garoto.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garoto.model.User;
import com.garoto.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@ResponseBody
	@RequestMapping(value = "/api/listUsers/{role}", method = RequestMethod.GET)
	public List<User> listUsers (@PathVariable String role) {
		
		
		List<User> users = new ArrayList<>();
		try {
			users = userRepo.findByRole(role);			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return users;
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/createNewUser", method = RequestMethod.POST)
	public String createNewUser (@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("role") String role) {

		User newUser = new User(name, email, username, password, role);
		User checkUser;
		try {
			checkUser = userRepo.findOneByUsername(username);
			if (checkUser != null) {
				return "user_name_exists";
			}
			
			checkUser = userRepo.findOneByUsername(email);
			if (checkUser != null) {
				return "user_email_exists";
			}
			
			userRepo.save(newUser);			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "user_database_error";
		}
		
		return "created";
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/deleteNewUser", method = RequestMethod.POST)
	public String deleteUser (@RequestParam("username") String username, @RequestParam("email") String email) {
		
		User user;
		try {
			user = userRepo.findOneByUsername(username);
			if (user == null) {
				return "user_not_found";
			}
			
			user = userRepo.findOneByUsername(email);
			if (user == null) {
				return "user_not_found";
			}
			
			userRepo.delete(user);			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "user_database_error";
		}
		
		return "deleted";
	}
}
