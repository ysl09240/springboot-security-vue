package com.sykean.hmhc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SpringContextUtil implements ApplicationContextAware {

	public static ApplicationContext applicationContext = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name) {
		Assert.notNull(name);
		return applicationContext.getBean(name);
	}

}
