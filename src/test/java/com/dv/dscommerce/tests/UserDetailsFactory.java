package com.dv.dscommerce.tests;

import java.util.ArrayList;
import java.util.List;

import com.dv.dscommerce.projections.UserDetailsProjection;

public class UserDetailsFactory {

	public static List<UserDetailsProjection> createCustomClientUser(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 1L, "ROLE_CLIENT"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminUser(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 2L, "ROLE_ADMIN"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminAndClientUser(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 1L, "ROLE_CLIENT"));
		list.add(new UserDetailsImpl(username, "123456", 2L, "ROLE_ADMIN"));
		return list;
	}
}


class UserDetailsImpl implements UserDetailsProjection {
	
	private String username;
	private String password;
	private Long roleId;
	private String authorirty;
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(String username, String password, Long roleId, String authorirty) {
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.authorirty = authorirty;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Long getRoleId() {
		return roleId;
	}

	@Override
	public String getAuthority() {
		return authorirty;
	}
}