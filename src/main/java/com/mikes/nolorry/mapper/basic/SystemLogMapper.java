package com.mikes.nolorry.mapper.basic;

import org.apache.ibatis.annotations.Mapper;

import com.mikes.nolorry.model.basic.SystemLog;

@Mapper
public interface SystemLogMapper {

	void create(SystemLog log);

}
