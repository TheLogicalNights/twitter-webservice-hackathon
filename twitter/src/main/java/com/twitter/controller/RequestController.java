package com.twitter.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.model.RegisterRequestModel;
import com.twitter.model.ResponseModel;
import com.twitter.model.users;
import com.twitter.repository.UserRepository;
import com.twitter.utils.Utils;

@RestController
@RequestMapping("/")
public class RequestController {

	@Autowired
	private Utils util;
	@Autowired
	private ResponseModel response;
	@Autowired
	private users userData;
	@Autowired
	private UserRepository userRepo;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseModel> getRequest(@RequestBody RegisterRequestModel requestData,
			@RequestHeader(name = "Authorization") String jwt) {
		if (util.validateRequest(jwt)) {
			switch (requestData.getCommand()) {
			case "register": {
				
				userData.setId(UUID.randomUUID().toString());
				userData.setFullname(requestData.getFullname());
				userData.setUsername(requestData.getUsername());
				userData.setEmail(requestData.getEmail());
				userData.setPhone(requestData.getPhoneNum());
				userData.setPassword(requestData.getPassword());
				userData.setBirthdate(requestData.getBirthdate());
				
				userRepo.save(userData);
				
				response.setResponseMessage("Register is called");
				response.setResponseCode(200);
			}
			case "login": {
				response.setResponseMessage("login is called");
				response.setResponseCode(200);
			}
			}
		}
		else {
			response.setResponseMessage("Invalid request");
			response.setResponseCode(400);
		}
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
	}
}
