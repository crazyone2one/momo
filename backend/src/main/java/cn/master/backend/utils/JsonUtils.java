package cn.master.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author create by 11's papa on 2023/7/14-14:29
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * desc: 字符串 => 复杂对象(List，Map，Set等)
     *
     * @param str           源字符串
     * @param typeReference 类型
     * @return T
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, typeReference);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * desc: 对象 => json字符串
     *
     * @param object 源对象
     * @return java.lang.String
     */
    public static <T> String obj2String(T object) {
        String json = null;
        if (Objects.nonNull(object)) {
            try {
                json = object instanceof String ? (String) object : OBJECT_MAPPER.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.warn("Parse Object to String error", e);
                throw new RuntimeException(e);
            }
        }
        return json;
    }

    /**
     * desc: json字符串 => 对象
     *
     * @param str   源字符串
     * @param clazz class type
     * @return T
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || Objects.isNull(clazz)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }
}
