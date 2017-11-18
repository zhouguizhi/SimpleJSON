package com.json.util;
/**
 * Created by zhouguizhijxhz on 2017/11/17.
 */
public class Utils {
    public static boolean isPrivateType(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj instanceof String){
            throw new IllegalArgumentException("不能传递一个string类型");
        }
        Class<?> clazz = obj.getClass();
        if(clazz.isArray()){
            throw new IllegalArgumentException("不能传递一个数组值");
        }
        return isWrapClass(clazz);
    }
    public static boolean isWrapClass(Class clazz) {
        if(clazz==null){
            return false;
        }
        try {
            return !((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
