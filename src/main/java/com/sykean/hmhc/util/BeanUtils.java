package com.sykean.hmhc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 05/08/2017.
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * copy集合
     *
     * @param sourceList
     * @param tClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sourceList, Class<T> tClass) {
        try {
            List<T> targetList = new ArrayList<T>(sourceList.size());
            if (sourceList.size() > 0) {
                for (S source : sourceList) {
                    T target = null;
                    try {
                        target = tClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    org.springframework.beans.BeanUtils.copyProperties(source, target);
                    targetList.add(target);
                }
            }
            return targetList;
        } catch (Exception e) {
            logger.error("复制集合出错", e);
            throw new RuntimeException("复制集合出错:" + e);
        }
    }

    /**
     * copy对象
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static <T> T copyPropertiesByClass(Object source, Class<T> tClass, String... ignoreProperties) {
        T target = null;
        try {
            target = tClass.newInstance();
        } catch (Exception e) {
            logger.error("复制集合出错", e);
            throw new RuntimeException("复制集合出错:" + e);
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }
}
