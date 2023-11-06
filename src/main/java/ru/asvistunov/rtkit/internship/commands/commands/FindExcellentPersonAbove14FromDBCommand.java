package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

/**
 * Класс FindExcellentPersonAbove14FromDBCommand реализует интерфейс Command
 * и предназначен для выполнения команды по поиску отличников старше 14 лет в базе данных.
 */
public class FindExcellentPersonAbove14FromDBCommand implements Command {

    /**
     * Метод execute() выполняет команду по поиску и выводу списка отличников,
     * которые старше 14 лет, используя сервис StatisticsBDService.
     */
    @Override
    public void execute() {
        StatisticsBDService.getTopStudentsAbove14();
    }
}
