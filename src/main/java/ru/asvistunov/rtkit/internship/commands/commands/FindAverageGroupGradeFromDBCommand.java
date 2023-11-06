package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

/**
 * Класс FindAverageGroupGradeFromDBCommand реализует интерфейс Command
 * и предназначен для выполнения команды по поиску средней оценки для группы из базы данных.
 */
public class FindAverageGroupGradeFromDBCommand implements Command {

    /**
     * Метод execute() выполняет команду по поиску средней оценки для двух групп (10 и 11 классы).
     * Для каждой из групп выполняются соответствующие запросы к базе данных.
     */
    @Override
    public void execute() {
        StatisticsBDService.getAverageGrade(11);
        StatisticsBDService.getAverageGrade(10);
    }
}
