package ru.asvistunov.rtkit.internship.person.data;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;

import java.util.List;

public class PersonDto {

    private int id;
    private String familyName;
    private String name;
    private int age;
    private int group;
    private List<SubjectGrade> subjectGradeList;

    public PersonDto() {
        this.subjectGradeList = new MyArrayList<>();
    }

    public PersonDto(int id, String familyName, String name, int age, int group) {
        this.id = id;
        this.familyName = familyName;
        this.name = name;
        this.age = age;
        this.group = group;
        this.subjectGradeList = new MyArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setSubjectGradeList(List<SubjectGrade> subjectGradeList) {
        this.subjectGradeList = subjectGradeList;
    }
}
