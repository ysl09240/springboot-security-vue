package com.sykean.hmhc.req.DictData;

import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableName;
import com.sykean.hmhc.req.common.PageReq;

import lombok.Data;
@TableName("bus_dict_data")
@Data
@ApiModel("字典列表展示model")
public class DictDataReq extends PageReq implements Comparable<DictDataReq> {
	
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
    
    private List<DictDataReq> list;

    
	@Override
	public int compareTo(DictDataReq o) {
		if(orderNum!=o.getOrderNum()){
            return orderNum-o.getOrderNum();
        }
		return orderNum-o.getOrderNum();
	}
}
