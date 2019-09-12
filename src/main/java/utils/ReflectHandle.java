package utils;

import annotation.HideInfo;
import model.TestModel;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class ReflectHandle {
    /**
     * 根据对象，获取注解中的字段名称
     *
     * @param obj
     * @return
     */
    public void getColumnStr(Object obj) throws Exception {
        Class t = obj.getClass();
        Field[] fields = t.getDeclaredFields();
        int fieldLength = fields.length;
        String columnName = null;
        Field field = null;
        for (int i = 0; i < fieldLength; i++) {
            field = fields[i];
            if (!field.isAnnotationPresent(HideInfo.class)) {
                continue;
            }
            field.setAccessible(true);
            String colunmType = field.getAnnotation(HideInfo.class).type();
            if (StringUtils.equalsIgnoreCase("phone", colunmType) || StringUtils.equalsIgnoreCase("idcardNum", colunmType)) {
                String name = getFieldName(field);
                Method gettterNameMethod = t.getMethod("get" + name);
                Object value = gettterNameMethod.invoke(obj);
                if (EmptyUtil.isNotEmpty(value)) {
                    if (StringUtils.equalsIgnoreCase("phone", colunmType)) {
                        t.getMethod("set" + name, String.class).invoke(obj, utils.StringUtils.hidePhoneNum(value.toString()));
                    }
                    if (StringUtils.equalsIgnoreCase("idcardNum", colunmType)) {
                        t.getMethod("set" + name, String.class).invoke(obj, utils.StringUtils.hideIdCardNum(value.toString()));
                    }
                }
            }
            field.setAccessible(false);
        }
    }

    public String getFieldName(Field field) {
        String name = field.getName(); // 获取属性的名字
        name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
        return name;
    }
}
