package com.example.pma.ereader.network;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ServerItem {

	private Long id;
	private String title;
	private String base64EncodedImage;

}
