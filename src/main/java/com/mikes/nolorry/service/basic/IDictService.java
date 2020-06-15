package com.mikes.nolorry.service.basic;

import java.util.List;

import com.mikes.nolorry.common.ServiceException;
import com.mikes.nolorry.model.basic.Dict;

public interface IDictService {

	void create(Dict dict) throws ServiceException;

	Dict findById(Integer dictId);

	List<Dict> findByTypeId(Integer typeId);

}
