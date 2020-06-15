package com.mikes.nolorry.model.basic;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("log")
public class SystemLog implements Serializable {

	private static final long serialVersionUID = -7129001869577053003L;

	private String _id;
	private Date optTime;// 操作时间
	private Integer optType;// 操作类型
	private String account;// 账号
	private String accName;// 账号名称
	private String action;// 行为
	private String actionDesc;// 行为描述
	private String addr;// ip地址
	private String tmnl;// 终端

}
