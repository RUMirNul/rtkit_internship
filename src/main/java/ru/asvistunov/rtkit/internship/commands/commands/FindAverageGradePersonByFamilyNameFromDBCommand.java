package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.Main;
import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

/**
 * Класс FindAverageGradePersonByFamilyNameFromDBCommand реализует интерфейс Command
 * и предназначен для выполнения команды по поиску средней оценки ученика по фамилии из базы данных.
 */
public class FindAverageGradePersonByFamilyNameFromDBCommand implements Command {

    /**
     * Метод execute() выполняет команду по поиску средней оценки ученика по фамилии.
     */
    @Override
    public void execute() {
        System.out.print("Введите фамилию ученика: ");
        String familyName = Main.SCANNER.nextLine();
        StatisticsBDService.getStudentAverageByFamilyName(familyName);
    }
}
