package com.garoto.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User implements Serializable, UserDetails {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_USER = "USER";
	public static final String ROLE_SUPERVISOR = "CLIENT";

	@Id @GeneratedValue	
	private Long id;
		
	@Column(length=150, nullable = false)
	private String name;
			
	@Column(length=50, nullable = false)
	private String email;
	
	@Column(length=25, nullable = false)
	private String username;	
	
	private String password;
	
	private String role = ROLE_USER; 
	
		
	public User(String name, String email, String username, String password, String role) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.role = role;
	}
	
	public User () {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role.toUpperCase();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new Roles(this.role));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		System.out.println("Senha 'e " +password);
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

class Roles implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String role = "ROLE_USER";
	
	Roles (String r){
		this.role = r;
	}
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.getRole();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
