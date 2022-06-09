package org.csu.store.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csu.store.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static{
        //对象的所有字段都将被JSON序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //将默认的把Date转换为Timestamp设置为false
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //设置日期格式

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        objectMapper.registerModule(javaTimeModule);

        //将空Bean进行序列化时的错误忽略，返回{}
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //反序列化
        //反序列化时属性对应不上，忽略错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String objectToString (T object){
        if(object == null){
            return null;
        }

        try {
            return object instanceof String ? (String)object : objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("Paser Object to String Error..." , e);
            return null;
        }
    }

    public static <T> String objectToStringPretty (T object){
        if(object == null){
            return null;
        }

        try {
            return object instanceof String ? (String)object : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            log.warn("Paser Object to String Error..." , e);
            return null;
        }
    }

    public static <T> T stringToObject(String string, Class<T> objectClass) {
        if(StringUtils.isEmpty(string) || objectClass == null){
            return null;
        }

        try {
            return objectClass.equals(String.class) ? (T)string : objectMapper.readValue(string, objectClass);
        } catch (JsonProcessingException e) {
            log.warn("Paser String to Object Error..." , e);
            return null;
        }

    }


    //List<User>
    public static <T> T stringToObject(String string, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(string) || typeReference == null){
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T)string : objectMapper.readValue(string, typeReference);
        } catch (Exception e) {
            log.warn("Parse String to Object error..." , e);
            return null;
        }
    }

    public static <T> T stringToObject(String string, Class<?> collectionClass, Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(string, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error..." , e);
            return null;
        }
    }

    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("abc@csu.edu.cn");
        user1.setCreateTime(LocalDateTime.now());

        String user1JSON = JSONUtil.objectToStringPretty(user1);

        System.out.println(user1JSON);


//        User user2 = new User();
//        user2.setId(2);
//        user2.setEmail("cba@csu.edu.cn");
//
//        List<User> userList = Lists.newArrayList();
//        userList.add(user1);
//        userList.add(user2);
//
//        String listJSON = JSONUtil.objectToStringPretty(userList);
//
//        System.out.println(listJSON);
//
//        List<User> userList2 = JSONUtil.stringToObject(listJSON,List.class);
//
//        userList2.get(0).getId();

        System.out.println("end");

//
//        String userJson = JSONUtil.objectToStringPretty(user);
//
//        User user2 = JSONUtil.stringToObject(userJson, User.class);
//
//        System.out.println(userJson);
//
//        System.out.println(user2);



    }

}
