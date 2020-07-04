package com.example.pma.ereader.network;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserItem {

	private Long userId;
	private String itemTitle;

}