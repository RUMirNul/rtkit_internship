package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.person.PersonDataQuerys;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.groups.DataGroup;
import ru.asvistunov.rtkit.internship.service.StudentService;

/**
 * Класс FindAverageGroupGradeCommand реализует интерфейс Command и представляет собой команду для нахождения средней
 * оценки в разных классах.
 */
public class FindAverageGroupGradeCommand implements Command {
    private final StudentService studentService;

    /**
     * Создает новый объект FindAverageGroupGradeCommand с указанным StudentService.
     *
     * @param studentService сервис, предоставляющий доступ к данным студентов.
     */
    public FindAverageGroupGradeCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Метод выполняет команду для нахождения средней оценки в разных классах.
     */
    @Override
    public void execute() {
        DataGroup<Integer> dataGroup = new DataGroup<>(Person::getGroup);
        studentService.getStudentList().forEach(dataGroup::addPerson);
        System.out.printf("Средняя оценка в 10 классе: %.2f\n", PersonDataQuerys.findAverageGroupGrade(10, dataGroup));
        System.out.printf("Средняя оценка в 11 классе: %.2f\n", PersonDataQuerys.findAverageGroupGrade(11, dataGroup));
    }
}
