package ru.asvistunov.rtkit.internship.person.data;

/**
 * Класс `SubjectGrade` представляет информацию об оценке по определенному предмету.
 */
public class SubjectGrade {
    private final String subjectName;
    private int grade;


    /**
     * Конструктор создает экземпляр класса `SubjectGrade` с заданным именем предмета и оценкой.
     *
     * @param subjectName Название предмета.
     * @param grade       Оценка по предмету.
     */
    public SubjectGrade(String subjectName, int grade) {
        this.subjectName = subjectName;
        this.grade = grade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "SubjectGrade{" +
                "subjectName='" + subjectName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
