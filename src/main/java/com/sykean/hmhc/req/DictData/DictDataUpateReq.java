package com.sykean.hmhc.req.DictData;

import java.util.Date;

import lombok.Data;
@Data
public class DictDataUpateReq {

	    private String id;
	    
	    private String name;
	    
	    private String value;
	 
	    private String type;

	    private Integer orderNum;
	    
	    private Byte delFlag;

	    private String createUserId;

	    private Date createTime;

	    private String updateUserId;

	    private Date updateTime;
	    
	    private String remark;
	    //字典类型名字
	    private String typeName;

}
