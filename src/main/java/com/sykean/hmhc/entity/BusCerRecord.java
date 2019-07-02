package com.sykean.hmhc.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@TableName("bus_cer_record")
@Data
public class BusCerRecord {

  private String id;
  private String ciId;
  private String sName;
  private String sGender;
  private String sCountry;
  private String sNation;
  private Date sBirth;
  private String cerType;
  private String cerNo;
  private String cerAuth;
  private String cerValid;
  private String addr1Code;
  private String addr1;
  private String addr2Code;
  private String addr2;
  private String tel1;
  private String tel2;
  private String fId;
  private String opTeller;
  private String opTime;


}
