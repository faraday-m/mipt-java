package edu.phystech;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ReportGenerator<SimpleEntity> generator = new CsvReportGenerator<>(SimpleEntity.class);
        List<SimpleEntity> objects = new ArrayList<>();
        List<String> petyaHobbies = new ArrayList<>();
        petyaHobbies.add("C#");
        petyaHobbies.add("Python");
        objects.add(new SimpleEntity("Вася",
                LocalDate.of(1997, 9, 23),
                Collections.singletonList("Java"),
                23));
        objects.add(new SimpleEntity("Петя",
                LocalDate.of(2000, 7, 12),
                petyaHobbies,
                20));
        Report report = generator.generate(objects);

        ReportGenerator<SimpleEntity> excelGenerator = new ExcelReportGenerator<>(SimpleEntity.class);
        Report excelReport = excelGenerator.generate(objects);
        report.writeTo(System.out);
        try {
            File file = new File("result.xlsx");
            if (!file.exists()) {
                file.createNewFile();
            }
            excelReport.writeTo(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
