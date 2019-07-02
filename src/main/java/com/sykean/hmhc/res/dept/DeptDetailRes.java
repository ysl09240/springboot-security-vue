package com.sykean.hmhc.res.dept;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @auther litong
 * @description 部门详情VO
 * @date 2019-03-11
 */
@Data
public class DeptDetailRes {
	
	//机构ID
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
	//父级机构ID
	private String supName;
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
	//机构类型铭文
	private String typeName;
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

    //所有上级部门ids(方便前端编辑页面回显数据)
    private List<String> treeIds;
}
