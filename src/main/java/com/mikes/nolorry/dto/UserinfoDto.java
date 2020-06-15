package com.mikes.nolorry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserinfoDto {

	private String account;
	private String accName;
	private String password;
	private String token;

}
