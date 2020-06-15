package com.mikes.nolorry.mapper.basic;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mikes.nolorry.model.basic.Dict;

@Mapper
public interface DictMapper {

	void create(Dict dict);

	Dict findById(Integer dictId);

	List<Dict> findByTypeId(Integer typeId);
	
	int existDict(Dict dict);

}
