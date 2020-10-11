package edu.phystech;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractReportGenerator<T> implements ReportGenerator<T> {
    @Override
    public abstract Report generate(List<T> entities);

    protected Map<String, String> generateFieldMap(T entity) {
        try {
            Map<String, String> fieldMap = new LinkedHashMap<>();
            Class clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                boolean access = f.isAccessible();
                f.setAccessible(true);
                String name = f.getName();
                if (f.isAnnotationPresent(AlternativeName.class)) {
                    name = f.getAnnotation(AlternativeName.class).value();
                }
                String value = f.get(entity).toString();
                fieldMap.put(name, value);
                f.setAccessible(access);
            }
            return fieldMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<String> getKeyValues(Class<?> clazz) {
        List<String> keys = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f: fields) {
            boolean access = f.isAccessible();
            f.setAccessible(true);
            String name = f.getName();
            if (f.isAnnotationPresent(AlternativeName.class)) {
                name = f.getAnnotation(AlternativeName.class).value();
            }
            f.setAccessible(access);
            keys.add(name);
        }
        return keys;
    }

}
