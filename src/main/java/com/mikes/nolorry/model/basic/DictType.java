package com.mikes.nolorry.model.basic;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictType implements Serializable {

	private static final long serialVersionUID = 5175299364750523418L;
	private Long typeId;
	private String name;

}
