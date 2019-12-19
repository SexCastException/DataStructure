package com.huazai.learning.annotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author pyh
 * @email pyh@efala.com
 * @date 2019/12/19 16:44:10
 */
public class TestAnnotion {
    @Test
    public void test() throws Exception {
        Class<TestAnnotion> clazz = TestAnnotion.class;
        Method show = clazz.getMethod("show",String.class);
        MyAnnotation[] annotations = show.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation annotation : annotations) {
            System.out.println(annotation.value());
        }
        System.out.println(annotations);
    }

    @MyAnnotation("hello")
    @MyAnnotation("world")
    public void show( String msg) {

    }
}
