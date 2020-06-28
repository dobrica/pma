package com.example.pma.ereader.model.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoggedInUser {

	private String userId;
	private String displayName;
	private String token;

}
