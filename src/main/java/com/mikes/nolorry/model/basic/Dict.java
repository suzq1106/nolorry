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
public class Dict implements Serializable {

	private static final long serialVersionUID = -1248747286479970713L;

	private Integer dictId;
	private Integer typeId;
	private String code;
	private String text;

}
