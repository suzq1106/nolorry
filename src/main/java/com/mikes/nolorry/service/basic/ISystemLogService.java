package com.mikes.nolorry.service.basic;

import java.util.List;

import com.mikes.nolorry.dto.SystemLogDto;
import com.mikes.nolorry.model.basic.SystemLog;

public interface ISystemLogService {

	void create(SystemLog log);

	List<SystemLog> findByCondition(SystemLogDto condition);
}
