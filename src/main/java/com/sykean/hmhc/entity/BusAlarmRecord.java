package com.sykean.hmhc.entity;


import lombok.Data;

import java.util.Date;

@Data
public class BusAlarmRecord {

  private String id;
  private String ciId;
  private String areaCode;
  private String stId;
  private String chanId;
  private String rsnAlarm;
  private String rsnDesc;
  private String sName;
  private String sGender;
  private String cerType;
  private String cerNo;
  private String sCountry;
  private String sNation;
  private String opTeller;
  private String opTime;
  
 private String createUserId;
 private Date createTime;
 private String updateUserId;
 private Date updateTime;


}
