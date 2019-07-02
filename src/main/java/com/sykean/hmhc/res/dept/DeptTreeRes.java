package com.sykean.hmhc.res.dept;

import lombok.Data;

/**
 * @auther litong
 * @description 部门树
 * @date 2019-03-11
 */
@Data 
public class DeptTreeRes {

	//id
    private String id;

    //名称
    private String name;

    //父级id
    private String parentId;

}
