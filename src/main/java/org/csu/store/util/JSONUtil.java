package org.csu.store.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.StringUtils;
import org.csu.store.domain.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class JSONUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段都将被JSON序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //将默认的把Data转换为Timestamp设置为false
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置日期格式
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.BASIC_ISO_DATE));
//
//        objectMapper.registerModule(javaTimeModule);

        //将空Bean进行序列化时的错误忽略，返回{}
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //反序列化
        //反序列化时属性对应不上，忽略错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static <T> String objectToString(T object) {
        if (object == null) {
            return null;
        }

        try {
            return object instanceof String ? (String) object : objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToObject(String string, Class<T> objectClass) {
        if (StringUtils.isEmpty(string) || objectClass == null){
            return null;
        }

        try {
            return objectClass.equals(String.class) ? (T)string : objectMapper.readValue(string, objectClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToObject(String string, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try{
            return objectMapper.readValue(string, javaType);
        }catch (Exception e){
            return null;
        }

    }

    public static void main(String []args){
        User user = new User();
        user.setId(12);

        String userJson = JSONUtil.objectToString(user);

        System.out.println(userJson);
    }

}
