package com.mikes.nolorry.service.basic.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mikes.nolorry.common.RedisKeyPrefix;
import com.mikes.nolorry.common.ServiceException;
import com.mikes.nolorry.mapper.basic.DictMapper;
import com.mikes.nolorry.model.basic.Dict;
import com.mikes.nolorry.service.basic.IDictService;

@Service("DictService")
public class DictServiceImpl implements IDictService {

	private static final Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private RedisTemplate<Object, Object> objectRedisTemplate;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void create(Dict dict) throws ServiceException {

		if (dictMapper.existDict(dict) > 0) {
			throw new ServiceException("该字典已存在,请检查字典码和字典值");
		}

		dictMapper.create(dict);

		objectRedisTemplate.delete(RedisKeyPrefix.DICT_TYPE + dict.getTypeId());

		logger.info("dict id {}", dict.getDictId());
	}

	@Override
	public Dict findById(Integer dictId) {
		return dictMapper.findById(dictId);
	}

	@Override
	public List<Dict> findByTypeId(Integer typeId) {
		ListOperations<Object, Object> listOperations = objectRedisTemplate.opsForList();

		List dicts = listOperations.range(RedisKeyPrefix.DICT_TYPE + typeId, 0, -1);
		if (dicts == null || dicts.size() == 0) {

			dicts = dictMapper.findByTypeId(typeId);

			if (dicts != null && dicts.size() > 0) {
				listOperations.rightPushAll(RedisKeyPrefix.DICT_TYPE + typeId, dicts);
			}
		}

		return dicts;
	}

}
