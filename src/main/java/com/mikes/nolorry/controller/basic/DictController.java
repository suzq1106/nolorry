package com.mikes.nolorry.controller.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mikes.nolorry.common.BasicLog;
import com.mikes.nolorry.common.ExcludeAuth;
import com.mikes.nolorry.common.OptType;
import com.mikes.nolorry.model.basic.Dict;
import com.mikes.nolorry.service.basic.IDictService;

@RestController
@RequestMapping("/basic/dict")
public class DictController {

	private static final Logger logger = LoggerFactory.getLogger(DictController.class);

	@Resource(name = "DictService")
	private IDictService dictService;

	@GetMapping(value = "/{dictId}")
	public ResponseEntity<Dict> get(@PathVariable Integer dictId) {
		Dict dict = dictService.findById(dictId);
		logger.info(dict.toString());
		return ResponseEntity.ok(dict);
	}

	@PostMapping
	@BasicLog(optType = OptType.ADD, action = "字典-创建", actionDesc = "添加字典信息")
	public ResponseEntity<Map<String, Object>> post(@RequestBody Dict dict) {
		Map<String, Object> result = new HashMap<String, Object>();

		logger.info("text={}", dict.getText());
		dictService.create(dict);

		result.put("msg", "字典添加成功!");
		return ResponseEntity.ok(result);
	}

	@ExcludeAuth
	@GetMapping("/findByTypeId/{typeId}")
	@BasicLog(optType = OptType.QUERY, action = "字典-查询", actionDesc = "根据字典类型查询字典")
	public ResponseEntity<List<Dict>> findByTypeId(@PathVariable Integer typeId) throws Exception {
		List<Dict> dicts = dictService.findByTypeId(typeId);

		return ResponseEntity.ok(dicts);
	}

}
