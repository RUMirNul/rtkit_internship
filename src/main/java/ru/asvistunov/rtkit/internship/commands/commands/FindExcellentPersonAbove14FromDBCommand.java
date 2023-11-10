package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.person.data.PersonInfoDto;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

import java.util.List;

/**
 * Класс FindExcellentPersonAbove14FromDBCommand реализует интерфейс Command
 * и предназначен для выполнения команды по поиску отличников старше 14 лет в базе данных.
 */
public class FindExcellentPersonAbove14FromDBCommand implements Command {

    /**
     * Метод execute() выполняет команду по поиску и выводу списка отличников,
     * которые старше 14 лет, используя сервис StatisticsBDService и выводит информацию в консоль.
     */
    @Override
    public void execute() {
        List<PersonInfoDto> persons = StatisticsBDService.getTopStudentsAbove14();
        if (persons.isEmpty()) {
            System.out.println("Нет отличников старше 14 лет.");
        } else {
            System.out.printf("%15s %12s %8s %8s\n", "Фамилия", "Имя", "Возраст", "Группа");
            persons.forEach(System.out::println);
        }
    }
}
