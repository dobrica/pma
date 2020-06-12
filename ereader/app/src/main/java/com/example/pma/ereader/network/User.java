package com.example.pma.ereader.network;

import androidx.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {

	@Nullable
	private Long id;
	private String fullName;
	private String username;
	@Nullable
	private String password;

}

