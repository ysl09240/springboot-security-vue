package com.sykean.hmhc.req.DictData;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import com.sykean.hmhc.req.common.PageReq;
@Data
@ApiModel("数据字典查询")
public class DictDataSearch extends PageReq {

	
	private String id;

    private String name;

    private String value;

    private String type;


}
