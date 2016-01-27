package com.dmcliver.donateme.domain;

import static java.util.Arrays.asList;

public enum Role {
	
	ADMIN(1),
	USER(2);

	private int weight;

	private Role(int weight) {
		this.weight = weight;
	}

	public int privilege() {
		return weight;
	}
	
	public static Role parse(int role) {
		
		Role[] values = Role.values();
		
		return asList(values).stream().filter(r -> r.privilege() == role)
									  .findFirst()
									  .orElse(null);
	}
}
