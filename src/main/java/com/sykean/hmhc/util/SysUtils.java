package com.sykean.hmhc.util;

import java.util.UUID;

public class SysUtils {
	// 生成UUID
	public static String GetUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
