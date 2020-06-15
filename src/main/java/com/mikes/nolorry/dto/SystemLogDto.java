package com.mikes.nolorry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.mikes.nolorry.common.BaseDto;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemLogDto extends BaseDto {

	private String optTimeBegin;
	private String optTimeEnd;
	private Integer optType;

}
