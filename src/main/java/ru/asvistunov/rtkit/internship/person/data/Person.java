package ru.asvistunov.rtkit.internship.person.data;


import ru.asvistunov.rtkit.internship.collections.MyArrayList;

import java.util.List;

/**
 * Класс `Person` представляет информацию о человеке, включая его фамилию, имя, возраст, номер группы и список оценок по предметам.
 */
public class Person {
    private String familyName;
    private String name;
    private int age;
    private int group;
    private final List<SubjectGrade> subjectGradeList;

    /**
     * Конструктор без аргументов создает экземпляр класса `Person` и инициализирует пустой список оценок.
     */
    public Person() {
        this.subjectGradeList = new MyArrayList<>();
    }


    /**
     * Конструктор с аргументами создает экземпляр класса `Person` с заданными данными, включая фамилию, имя, возраст,
     * и номер группы. Также инициализирует пустой список оценок.
     *
     * @param familyName Фамилия.
     * @param name       Имя.
     * @param age        Возраст.
     * @param group      Класс(группа), где обучается человек.
     */
    public Person(String familyName, String name, int age, int group) {
        this.familyName = familyName;
        this.name = name;
        this.age = age;
        this.group = group;
        this.subjectGradeList = new MyArrayList<>();
    }

    /**
     * Метод добавляет оценку по предмету в список оценок для данной персоны.
     *
     * @param subjectGrade Объект содержащий предмет и оценку по этому предмету.
     */
    public void addSubjectGrade(SubjectGrade subjectGrade) {
        subjectGradeList.add(subjectGrade);
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public List<SubjectGrade> getSubjectGradeList() {
        return subjectGradeList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "familyName='" + familyName + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", group=" + group +
                ", subjectGradeList=" + subjectGradeList +
                '}';
    }


}
