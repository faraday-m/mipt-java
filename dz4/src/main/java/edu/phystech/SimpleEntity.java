package edu.phystech;

import java.time.LocalDate;
import java.util.List;

public class SimpleEntity {
    @AlternativeName("Имя")
    private String name;
    @AlternativeName("День рождения")
    private LocalDate birthday;
    @AlternativeName("Хобби")
    private List<String> hobbies;
    @AlternativeName("Возраст")
    private int age;

    public SimpleEntity(String name, LocalDate birthday, List<String> hobbies, int age) {
        this.name = name;
        this.birthday = birthday;
        this.hobbies = hobbies;
        this.age = age;
    }
}
