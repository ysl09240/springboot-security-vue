package com.sykean.hmhc.res.menu;

import lombok.Data;

import com.sykean.hmhc.res.dept.DeptTreeRes;

@Data
public class MenuTreeRes {
	  //id
    private String id;

    //名称
    private String name;

    //父级id
    private String parentId;
}
