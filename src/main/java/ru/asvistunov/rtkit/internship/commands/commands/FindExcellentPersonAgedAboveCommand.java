package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.person.PersonDataQuerys;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.groups.DataGroup;
import ru.asvistunov.rtkit.internship.service.StudentService;

/**
 * Класс FindExcellentPersonAgedAboveCommand реализует интерфейс Command и представляет команду
 * для поиска студентов, которые имеют отличные оценки (5) и старше указанного возраста.
 */
public class FindExcellentPersonAgedAboveCommand implements Command {
    private final StudentService studentService;

    /**
     * Создает новый объект FindExcellentPersonAgedAboveCommand с указанным @code StudentService.
     *
     * @param studentService сервис, предоставляющий доступ к данным студентов.
     */
    public FindExcellentPersonAgedAboveCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Выполняет команду для поиска отличных студентов старше указанного возраста.
     */
    @Override
    public void execute() {
        DataGroup<Integer> dataGroup = new DataGroup<>(Person::getAge);
        studentService.getStudentList().forEach(dataGroup::addPerson);
        Person[] persons = PersonDataQuerys.findExcellentPersonAgedAbove(14, dataGroup);
        System.out.printf("Отличники старше 14 лет:\n");
        PersonDataQuerys.printPerson(persons);
    }
}
