package com.sykean.hmhc.req.dept;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

/**
 * @auther litong
 * @description 部门新增请求体
 * @date 2019-03-07
 */
@Data
public class DeptSaveReq  {
	
    //机构ID
    @ApiModelProperty("机构ID")
    @NotBlank(message = "机构ID不能为空")
    @Length(max = 50, message = "机构ID最大长度为50")
  	private String code;
  	//公安机构代码
    @ApiModelProperty("公安机构代码")
    @NotBlank(message = "公安机构代码不能为空")
    @Length(max = 12, message = "机构ID最大长度为12")
  	private String zzcode;
  	//机构中文拼音
    @ApiModelProperty("机构中文拼音")
    @NotBlank(message = "机构中文拼音不能为空")
    @Length(max = 100, message = "机构中文拼音最大长度为100")
  	private String codeSpelling;
  	//机构名称
    @ApiModelProperty("机构名称")
    @NotBlank(message = "机构名称不能为空")
    @Length(max = 100, message = "机构名称最大长度为100")
  	private String codeName;
  	//机构名称描述
    @ApiModelProperty("机构名称描述")
    @NotBlank(message = "机构名称描述不能为空")
    @Length(max = 100, message = "机构名称描述最大长度为100")
  	private String codeAbr;
  	//机构地址
    @ApiModelProperty("机构地址")
    @NotBlank(message = "机构地址不能为空")
    @Length(max = 100, message = "机构地址最大长度为100")
  	private String address;
  	//联系电话
    @ApiModelProperty("联系电话")
    @NotBlank(message = "联系电话不能为空")
    @Length(min=1,max = 50, message = "联系电话最大长度为50")
  	private String contract;
  	//手机号
    @ApiModelProperty("手机号")
    @Length(min=1,max = 50, message = "手机号最大长度为50")
  	private String phone;
  	//父级机构ID
    @ApiModelProperty("父级机构ID")
    @NotBlank(message = "父级机构ID不能为空")
    @Length(max = 50, message = "父级机构ID最大长度为50")
  	private String supCode;
  	//有效标志
    @ApiModelProperty("有效标志")
    @NotBlank(message = "有效标志不能为空")
    @Length(max = 2, message = "有效标志最大长度为2")
  	private String invalid;
  	//排序ID
    @ApiModelProperty("排序ID")
    @NotBlank(message = "排序ID不能为空")
    @Length(max = 8, message = "排序ID最大长度为8")
  	private String sortid;
  	//组织机构类型
    @ApiModelProperty("组织机构类型")
    @NotBlank(message = "组织机构类型")
    @Length(max = 10, message = "组织机构类型最大长度为10")
  	private String type;
  	//省
    @ApiModelProperty("省")
    @NotBlank(message = "省")
    @Length(max = 36, message = "省最大长度为36")
  	private String provinceId;
  	//市
    @ApiModelProperty("市")
    @NotBlank(message = "市")
    @Length(max = 36, message = "市最大长度为36")
  	private String cityId;
  	//区
    @ApiModelProperty("区")
    @NotBlank(message = "区")
    @Length(max = 36, message = "区最大长度为36")
  	private String areaId;

    //经度
    @ApiModelProperty("经度")
    private BigDecimal longitude;
    //纬度
    @ApiModelProperty("纬度")
    private BigDecimal latitude;
}
