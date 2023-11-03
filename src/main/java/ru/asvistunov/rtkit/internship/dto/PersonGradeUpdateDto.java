package ru.asvistunov.rtkit.internship.dto;

/**
 * Класс PersonGradeUpdateDto представляет объект для передачи информации о запросе на обновление оценки студента
 * в формате DTO (Data Transfer Object).
 */
public class PersonGradeUpdateDto {
    private String familyName;
    private String name;
    private int group;
    private String subjectName;
    private int newGrade;

    /**
     * Конструктор класса, инициализирующий объект PersonGradeUpdateDto.
     *
     * @param familyName  Фамилия студента.
     * @param name        Имя студента.
     * @param group       Номер группы, к которой относится студент.
     * @param subjectName Название предмета, для которого обновляется оценка.
     * @param newGrade    Новая оценка для студента.
     */
    public PersonGradeUpdateDto(String familyName, String name, int group, String subjectName, int newGrade) {
        this.familyName = familyName;
        this.name = name;
        this.group = group;
        this.subjectName = subjectName;
        this.newGrade = newGrade;
    }

    /**
     * Пустой конструктор класса PersonGradeUpdateDto.
     */
    public PersonGradeUpdateDto() {
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(int newGrade) {
        this.newGrade = newGrade;
    }

    @Override
    public String toString() {
        return "PersonGradeUpdateDto{" +
                "familyName='" + familyName + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                ", subjectName='" + subjectName + '\'' +
                ", newGrade=" + newGrade +
                '}';
    }
}
