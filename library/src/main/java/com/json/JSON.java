package com.json;
import android.text.TextUtils;
import com.json.enums.JSON_TYPE;
import com.json.util.Logger;
import java.lang.reflect.Type;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by zhouguizhi on 2017/11/14.
 */
public class JSON {
    public static Object jsonToObj(String json,Class clazz){
        if(TextUtils.isEmpty(json)||clazz==null){
            return null;
        }
        Object object=null;
        //数组
        if(json.charAt(0)=='[')
        {
            try {
                object= addObjToList(json,clazz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(json.charAt(0)=='{')
        {
            try {
                JSONObject jsonObject = new JSONObject(json);
                //反射得到最外层的model
                object=clazz.newInstance();
               //获取json最外层的key 和反射中的字段进行对比
                Iterator<?> iterator=jsonObject.keys();
                while (iterator.hasNext())
                {
                    String key= (String) iterator.next();
                    Object fieldValue;
                    //得到当前clazz类型的所有成员变量
                    List<Field>  fields=getAllFields(clazz,null);
                    for (Field field:fields)
                    {
                        //将key和成员变量进行匹配
                        if(field.getName().equalsIgnoreCase(key))
                        {
                            field.setAccessible(true);
                            //得到 key所对应的值   值 可以基本类型  类类型
                            fieldValue=getFieldValue(field,jsonObject,key);
                            if(fieldValue!=null)
                            {
                                field.set(object,fieldValue);
                            }
                            field.setAccessible(false);
                        }
                    }
                }
            } catch (JSONException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    private static Object addObjToList(String json, Class clazz) throws JSONException {
        if(TextUtils.isEmpty(json)||clazz==null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        JSONArray jsonArray=new JSONArray(json);
        for (int i=0;i<jsonArray.length();i++)
        {
            //拿到JSON字符串
            String jsonValue=jsonArray.getJSONObject(i).toString();
            switch (getJSONType(jsonValue))
            {
                case JSON_ARRAY:
                    //外层JSONArray 嵌套里面JSONArray
                    List<?> infoList=(List<?>) addObjToList(jsonValue,clazz);
                    list.add(infoList);
                    break;
                case JSON_OBJECT:
                    list.add(jsonToObj(jsonValue,clazz));
                    break;
                case JSON_ERROR:

                    break;
            }
        }
        return list;
    }
    public static String toJson(Object obj)
    {
        if(obj==null){
            throw  new NullPointerException("不能转递空对象");
        }
        StringBuffer json=new StringBuffer();
        if(obj instanceof List<?>){ //判断是否是集合类型
            json.append("[");
            List list= (List) obj;
            if(list.isEmpty()){//空集合
                json.append("]");
                return json.toString();
            }
            for (int i=0;i<list.size();i++){
                addSingleObjToJson(json,list.get(i));
                if(i<list.size()-1)
                {
                    json.append(",");
                }
            }
        }
        else{
            addSingleObjToJson(json,obj);
        }
        return json.toString();
    }
    private static Object getFieldValue(Field field, JSONObject jsonObject, String key) throws JSONException {
       if(field==null||jsonObject==null||TextUtils.isEmpty(key)){
           return null;
       }
        Object fieldValue=null;
        //得到当前成员变量类型
        Class<?> fieldClass=field.getType();
        Class<?> clazzType = field.getType();
        if(clazzType==int.class||clazzType==Integer.class){
            fieldValue=jsonObject.getInt(key);
        }else if(clazzType==double.class||clazzType==Double.class){
            fieldValue=jsonObject.getDouble(key);
        }else if(clazzType==long.class||clazzType==Long.class){
            fieldValue=jsonObject.getLong(key);
        }else if(clazzType==String.class){
            fieldValue=jsonObject.getString(key);
        }else{
            //判断集合类型 和对象类型 jsonValue 代表完整的json字符串  里面一层
            String jsonValue=jsonObject.getString(key);
            switch (getJSONType(jsonValue))
            {
                case JSON_ARRAY:
                    Type fieldType=field.getGenericType();
                    if(fieldType instanceof ParameterizedType)
                    {
                        ParameterizedType parameterizedType= (ParameterizedType) fieldType;
                        //获取集合中的类型
                        Type[]  fieldArgType=parameterizedType.getActualTypeArguments();
                        for(Type type:fieldArgType)
                        {
                            //fieldArgClass  代表着User.class
                            Class<?> fieldArgClass= (Class<?>) type;
                            fieldValue= addObjToList(jsonValue,fieldArgClass);
                        }
                    }
                    break;
                case JSON_OBJECT:
                    fieldValue=jsonToObj(jsonValue,fieldClass);
                    break;
                case JSON_ERROR:
                    break;
            }
        }
        return fieldValue;
    }
    private static void addSingleObjToJson(StringBuffer jsonBuffer, Object obj) {
        if(null==obj||jsonBuffer==null) return;
        jsonBuffer.append("{");
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        getAllFields(clazz ,fields);
        if(!fields.isEmpty()){
            for (int i=0;i<fields.size();i++)
            {
                Method method=null;
                //反射拿到成员变量的值
                Object filedValue=null;
                Field field=fields.get(i);
                String fieldName=field.getName();
                //拼接时拿到反射的Method对象
                String methodName ;
                if(fieldName.startsWith("_")){
                    methodName="get"+fieldName;
                }else{
                    methodName="get"+((char)(fieldName.charAt(0)-32))+fieldName.substring(1);
                }
                try {
                    method=clazz.getMethod(methodName);
                } catch (NoSuchMethodException e) {
                    if(field.getType()==Boolean.class||field.getType()==boolean.class){
                        if(fieldName.startsWith("is")){
                            methodName=fieldName;
                        }else{
                            if(fieldName.startsWith("_")){
                                methodName="is"+fieldName;
                            }else{
                                methodName="is"+((char)(fieldName.charAt(0)-32))+fieldName.substring(1);
                            }
                        }
                    }
                    try {
                        method=clazz.getMethod(methodName);
                    } catch (NoSuchMethodException ignored) {
                    }
                }
                //方法不为空
                if(method!=null)
                {
                    try {
                        filedValue=method.invoke(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{//说明没有get方法
                    try {
                        if (null != field.get(obj)) {
                            filedValue = field.get(obj).toString();
                        }
                    } catch (IllegalAccessException e) {
                        Logger.e("发生错误的是哪个变量="+fieldName);
                        e.printStackTrace();
                    }
                }
                if(filedValue!=null)
                {
                    jsonBuffer.append("\"");
                    jsonBuffer.append(fieldName);
                    jsonBuffer.append("\":");

                    //判断成员变的类型
                    if(filedValue instanceof
                            Integer||filedValue instanceof Long||
                            filedValue instanceof Double||
                            filedValue instanceof  Boolean||filedValue instanceof Short
                            ||filedValue instanceof Float)
                    {
                        jsonBuffer.append(filedValue);
                    }else if(filedValue instanceof String)
                    {
                        jsonBuffer.append("\"");
                        jsonBuffer.append(filedValue.toString());
                        jsonBuffer.append("\"");
                    }else if(filedValue instanceof List<?>)
                    {
                        //集合类型
                        addListToBuffer(jsonBuffer,filedValue);
                    }else
                    {
                        //对象类型  递归调用
                        addSingleObjToJson(jsonBuffer,filedValue);
                    }
                    jsonBuffer.append(",");
                }
                if(i==fields.size()-1&&jsonBuffer.charAt(jsonBuffer.length()-1)==',')
                {
                    jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
                }
            }
        }
        jsonBuffer.append("}");
    }
    private static void addListToBuffer(StringBuffer jsonBuffer, Object filedValue) {
        jsonBuffer.append("[");
        List<?> list= (List<?>) filedValue;
        if(null==list||list.isEmpty()){
            jsonBuffer.append("]");
            return;
        }
        for (int i=0;i<list.size();i++)
        {
            addSingleObjToJson(jsonBuffer,list.get(i));
            if(i<list.size()-1)
            {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("]");
    }
    private static  List<Field> getAllFields(Class<?> clazz, List<Field> fields) {
        if(null==clazz){
            return null;
        }
        if(null==fields){
            fields = new ArrayList<>();
        }
        //递归时排除Object类型
        if(clazz.getSuperclass()!=null)
        {
            Field[] declaredFields=clazz.getDeclaredFields();
            for(Field field:declaredFields)
            {
                //排除final修饰的成员变量  final成员变量表示常量，只能被赋值一次，赋值后值不再改变。
                if(!Modifier.isFinal(field.getModifiers()))
                {
                    fields.add(field);
                }
            }
            //当前类型遍历完成之后 开始遍历父类型成员变量
            getAllFields(clazz.getSuperclass(),fields);
        }
        return fields;
    }
    /**
     * 获取当前json字符串的类型
     */
    private static JSON_TYPE getJSONType(String jsonValue) {
        char firstChar=jsonValue.charAt(0);
        if(firstChar=='{')
        {
            return JSON_TYPE.JSON_OBJECT;
        }else if(firstChar=='[')
        {
            return JSON_TYPE.JSON_ARRAY;
        }else
        {
            return JSON_TYPE.JSON_ERROR;
        }
    }
}
