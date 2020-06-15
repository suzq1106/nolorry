package com.mikes.nolorry.service.basic.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mikes.nolorry.dto.SystemLogDto;
import com.mikes.nolorry.model.basic.SystemLog;
import com.mikes.nolorry.service.basic.ISystemLogService;

@Service("SystemLogService")
public class SystemLogServiceImpl implements ISystemLogService {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogServiceImpl.class);

	// @Autowired
	// private SystemLogMapper systemLogMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Async
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(SystemLog log) {
		mongoTemplate.insert(log);
		// systemLogMapper.create(log);
		logger.info(log.toString());
	}

	@Override
	public List<SystemLog> findByCondition(SystemLogDto condition) {

		Query query = Query.query(Criteria.where("optType").is(condition.getOptType()));

		Pageable pageable = PageRequest.of(condition.getPageIndex(), condition.getPageSize());

		query.with(pageable);

		query.with(new Sort(Direction.DESC, "optTime"));

		long count = mongoTemplate.count(query, SystemLog.class);
		List<SystemLog> list = mongoTemplate.find(query, SystemLog.class);

		logger.info("count={}", count);

		return list;
	}

	// @Async
	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public Future<Boolean> save(SystemLog log) {
	//
	// // try { // 这个方法需要调用5000毫秒
	// // Thread.sleep(5000);
	// // } catch (InterruptedException e) {
	// // e.printStackTrace();
	// // } // 消息汇总
	// systemLogMapper.save(log);
	//
	// logger.info("logId={}", log.getLogId());
	// return new AsyncResult<>(Boolean.TRUE);
	// }

}
