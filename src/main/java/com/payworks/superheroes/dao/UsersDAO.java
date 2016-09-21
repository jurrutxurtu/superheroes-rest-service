package com.payworks.superheroes.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jon.urrutxurtu
 *
 */
public class UsersDAO {

	private static Map<String, String> usersMap;
	private static Map<String, List<String>> rolesMap;

	public static boolean userAuthorized(String user, String password,
			List<String> allowedRoles) {
		if (password.equals(usersMap.get(user))) {
			List<String> userRoles = rolesMap.get(user);
			for (String role : userRoles) {
				if (allowedRoles.contains(role)) {
					return true;
				}
			}
		}
		return false;
	}

	static {
		usersMap = new HashMap<String, String>();
		rolesMap = new HashMap<String, List<String>>();

		List<String> rolesWithAdmin = new ArrayList<String>();
		rolesWithAdmin.add("admin");
		rolesWithAdmin.add("board_member");
		List<String> rolesWithouAdmin = new ArrayList<String>();
		rolesWithouAdmin.add("customer");
		rolesWithouAdmin.add("publisher");

		usersMap.put("adminUser", "adminPassword");
		rolesMap.put("adminUser", rolesWithAdmin);

		usersMap.put("notAdminUser", "notAdminPassword");
		rolesMap.put("notAdminUser", rolesWithouAdmin);

	}

}
