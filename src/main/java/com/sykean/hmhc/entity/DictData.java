package com.sykean.hmhc.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 数据字典
 */
@TableName("bus_dict_data")
@Data
public class DictData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6783545781101010546L;

	/**
     * 数据字典主键ID
     */
	@TableId
    private String id;

    /**
     * 数据字典名称
     */
    private String name;

    /**
     * 数据值
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    /**
     * 未删除0，已删除1
     */
    private Byte delFlag;
    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 序号
     */
    private Integer orderNum;
    
    /**
     * 备注
     */
    private String remark;




}
