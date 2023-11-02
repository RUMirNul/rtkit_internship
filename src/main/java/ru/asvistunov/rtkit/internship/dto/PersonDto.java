package ru.asvistunov.rtkit.internship.dto;

public class PersonDto {
    private String familyName;
    private String name;
    private int age;
    private int group;
    private double meanGrade;

    public PersonDto(String familyName, String name, int age, int group, double meanGrade) {
        this.familyName = familyName;
        this.name = name;
        this.age = age;
        this.group = group;
        this.meanGrade = meanGrade;
    }

    public PersonDto() {
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

    public double getMeanGrade() {
        return meanGrade;
    }

    public void setMeanGrade(double meanGrade) {
        this.meanGrade = meanGrade;
    }
}
