package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.Main;
import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.person.data.PersonAverageGradeDto;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

import java.util.List;

/**
 * Класс FindAverageGradePersonByFamilyNameFromDBCommand реализует интерфейс Command
 * и предназначен для выполнения команды по поиску средней оценки ученика по фамилии из базы данных и вывода её в консоль.
 */
public class FindAverageGradePersonByFamilyNameFromDBCommand implements Command {

    /**
     * Метод execute() выполняет команду по поиску средней оценки ученика по фамилии.
     */
    @Override
    public void execute() {
        System.out.print("Введите фамилию ученика: ");
        String familyName = Main.SCANNER.nextLine();

        List<PersonAverageGradeDto> persons = StatisticsBDService.getStudentAverageByFamilyName(familyName);
        if (persons.isEmpty()) {
            System.out.println("Ученики с такой фамилией не найдены!");
        } else {
            System.out.printf("%15s %12s %8s %8s\n", "Фамилия", "Имя", "Группа", "Средняя оценка");
            persons.forEach(System.out::println);
        }
    }
}
