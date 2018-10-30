package com.garoto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.garoto.model.User;


@Component
@RepositoryEventHandler
public class UserEventHandler {
	@Autowired
	private PasswordEncoder encoder;
	
	/**
	 * Encodes the user password right before saving it to the database.
	 * @param user the user being saved
	 */
	@HandleBeforeCreate @HandleBeforeSave
	public void handleUserSave(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
	}
}
