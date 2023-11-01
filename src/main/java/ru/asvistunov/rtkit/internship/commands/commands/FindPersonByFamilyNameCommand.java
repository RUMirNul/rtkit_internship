package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.Main;
import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.person.PersonDataQuerys;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.groups.DataGroup;
import ru.asvistunov.rtkit.internship.service.StudentService;

/**
 * Класс FindPersonByFamilyNameCommand реализует интерфейс Command и представляет команду
 * для поиска студентов по фамилии, введенной пользователем в консоли.
 */
public class FindPersonByFamilyNameCommand implements Command {

    private final StudentService studentService;

    /**
     * Создает новый объект FindPersonByFamilyNameCommand с указанным StudentService.
     *
     * @param studentService сервис, предоставляющий доступ к данным студентов.
     */
    public FindPersonByFamilyNameCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Выполняет команду для поиска студентов по фамилии, введенной пользователем в консоли.
     */
    @Override
    public void execute() {
        DataGroup<Character> dataGroup = new DataGroup<>(person -> {
            if (person.getFamilyName() == null || person.getFamilyName().isBlank()) {
                return null;
            }
            return person.getFamilyName().charAt(0);
        });
        studentService.getStudentList().forEach(dataGroup::addPerson);

        System.out.print("Введите фамилию ученика: ");
        String familyName = Main.SCANNER.nextLine();
        Person[] persons = PersonDataQuerys.findPersonByFamilyName(familyName, dataGroup);
        System.out.printf("Найдено %d студентов с фамилией %s:\n", persons.length, familyName);
        PersonDataQuerys.printPerson(persons);

    }
}
