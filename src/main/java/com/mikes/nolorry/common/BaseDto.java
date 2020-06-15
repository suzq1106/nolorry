package com.mikes.nolorry.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {

	private int pageIndex = 0;
	private int pageSize = 10;

}
