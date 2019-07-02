package com.sykean.hmhc.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 省市县表
 */
@TableName("bus_city")
@Data
public class City {

    //城市id
    @TableId(type = IdType.INPUT)
    private String cityId;
    //父id
    private String parentId;
    //城市码
    private String cityCode;
    //城市名
    private String city;
    //层级 1 省级 2 市级 3 区级
    private Byte level;
    //创建人
    private String createUserId;
    //修改人
    private String updateUserId;
    //墨迹id
    private String mjId;
    //上传使用城市id
    private String uploadCityId;

}
