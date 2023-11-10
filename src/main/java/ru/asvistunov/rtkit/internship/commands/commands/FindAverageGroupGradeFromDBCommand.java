package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

/**
 * Класс FindAverageGroupGradeFromDBCommand реализует интерфейс Command и предназначен
 * для выполнения команды по поиску средней оценки для группы из базы данных и вывода информации в консоль.
 */
public class FindAverageGroupGradeFromDBCommand implements Command {

    /**
     * Метод execute() выполняет команду по поиску средней оценки для двух групп (10 и 11 классы).
     * Для каждой из групп выполняются соответствующие запросы к базе данных.
     */
    @Override
    public void execute() {
        System.out.printf("%8s %8s\n", "Группа", "Средняя оценка");
        System.out.printf("%8d %8.2f\n", 11, StatisticsBDService.getAverageGrade(11));
        System.out.printf("%8s %8s\n", "Группа", "Средняя оценка");
        System.out.printf("%8d %8.2f\n", 10, StatisticsBDService.getAverageGrade(10));
    }
}
