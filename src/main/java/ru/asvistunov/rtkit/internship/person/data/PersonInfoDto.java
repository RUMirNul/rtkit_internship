package ru.asvistunov.rtkit.internship.person.data;

/**
 * Класс `PersonAverageGradeDto` представляет информацию о человеке, включая его фамилию, имя, возраст, номер группы.
 */
public class PersonInfoDto {
    private String familyName;
    private String name;
    private int age;
    private int group;

    /**
     * Конструктор без аргументов создает экземпляр класса `PersonInfoDto`.
     */
    public PersonInfoDto() {
    }

    /**
     * Конструктор с аргументами для создания объекта PersonInfoDto с указанными значениями.
     *
     * @param familyName Фамилия студента
     * @param name       Имя студента
     * @param age        Возраст студента
     * @param group      Группа, к которой относится студент
     */
    public PersonInfoDto(String familyName, String name, int age, int group) {
        this.familyName = familyName;
        this.name = name;
        this.age = age;
        this.group = group;
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

    @Override
    public String toString() {
        return String.format("%15s %12s %8d %8d", familyName, name, age, group);
    }
}
