package ru.asvistunov.rtkit.internship.person.data;


/**
 * Класс `PersonAverageGradeDto` представляет информацию о человеке, включая его фамилию, имя, номер группы и среднюю оценку.
 */
public class PersonAverageGradeDto {
    private String familyName;
    private String name;
    private int group;
    private double averageGrade;


    /**
     * Конструктор без аргументов создает экземпляр класса `PersonAverageGradeDto`.
     */
    public PersonAverageGradeDto() {
    }

    /**
     * Конструктор с аргументами для создания объекта PersonAverageGradeDto с указанными значениями.
     *
     * @param familyName   Фамилия студента
     * @param name         Имя студента
     * @param group        Группа, к которой относится студент
     * @param averageGrade Средняя оценка студента
     */
    public PersonAverageGradeDto(String familyName, String name, int group, double averageGrade) {
        this.familyName = familyName;
        this.name = name;
        this.group = group;
        this.averageGrade = averageGrade;
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

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return String.format("%15s %12s %7s %8.2f", familyName, name, group, averageGrade);
    }
}
