package edu.phystech;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CsvReportGenerator<T> extends AbstractReportGenerator<T> {

    @Override
    public Report generate(List<T> entities) {
        StringBuilder sb = new StringBuilder();
        Class clazz = null;
        try {
            if (entities.size() > 0) {
                clazz = entities.get(0).getClass();
                List<String> keys = getKeyValues(clazz);
                for (int i = 0; i < entities.size(); i++) {
                    Map<String, String> fieldMap = generateFieldMap(entities.get(i));
                    if (i == 0) {
                        for (int j = 0; j < keys.size(); j++) {
                            sb.append(keys.get(j));
                            if (j < keys.size() - 1) {
                                sb.append(",");
                            }
                        }
                    }
                    sb.append("\n");
                    for (int j = 0; j < keys.size(); j++) {
                        sb.append(fieldMap.get(keys.get(j)));
                        if (j < keys.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                return new StringReport(sb.toString());
            } else {
                throw new InvalidArgumentException((String[]) (Collections.singletonList("entities").toArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
