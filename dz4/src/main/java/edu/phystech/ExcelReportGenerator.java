package edu.phystech;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExcelReportGenerator<T> extends AbstractReportGenerator<T> {
    Class clazz;

    public ExcelReportGenerator(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Report generate(List<T> entities) {
        try {
            List<String> keys = getKeyValues(clazz);
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Report");
            Row row = sheet.createRow(0);
            for (int i = 0; i < keys.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(keys.get(i));
            }
            for (int i = 0; i < entities.size(); i++) {
                T entity = entities.get(i);
                Map<String, String> values = generateFieldMap(clazz, entity);
                Row valueRow = sheet.createRow(i+1);
                for (int j = 0; j < keys.size(); j++) {
                    Cell cell = valueRow.createCell(j);
                    cell.setCellValue(values.get(keys.get(j)));
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            baos.close();
            return new ByteReport(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
