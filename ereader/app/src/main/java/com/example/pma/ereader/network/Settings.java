package com.example.pma.ereader.network;


import androidx.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Settings {

	@Nullable
	private Long id;
	@Nullable
	private Long userId;
	private Long fontSize;
	private Long brightnessLevel;

}
