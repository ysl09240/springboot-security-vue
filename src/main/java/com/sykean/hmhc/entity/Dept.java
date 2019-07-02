package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门表
 */
@Data
@TableName("bus_dept")
public class Dept implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2658640904544317808L;
	
	//机构ID
	@TableId
	private String code;
	//公安机构代码
	private String zzcode;
	//机构中文拼音
	private String codeSpelling;
	//机构名称
	private String codeName;
	//机构名称描述
	private String codeAbr;
	//机构地址
	private String address;
	//联系电话
	private String contract;
	//手机号
	private String phone;
	//父级机构ID
	private String supCode;
	//有效标志
	private String invalid;
	//排序ID
	private String sortid;
	//扩展字段1
	private String ext1;
	//扩展字段2
	private String ext2;
	//创建人
	private String createUserId;
	//创建时间
	private Date createTime;
	//更新人
	private String updateUserId;
	//更新时间
	private Date updateTime;
	//组织机构类型
	private String type;
	//省
	private String provinceId;
	//市
	private String cityId;
	//区
	private String areaId;
	//经度
    private BigDecimal longitude;
    //纬度
    private BigDecimal latitude;
	
	
}
