package ru.asvistunov.rtkit.internship;

import ru.asvistunov.rtkit.internship.commands.CommandBuilder;
import ru.asvistunov.rtkit.internship.commands.CommandEnum;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.inputs.DataLoader;
import ru.asvistunov.rtkit.internship.person.inputs.PersonDataCSVReader;
import ru.asvistunov.rtkit.internship.service.StudentService;
import ru.asvistunov.rtkit.internship.service.StudentServiceImpl;

import java.util.Scanner;

public class Main {
    public static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        DataLoader<Person, String> dataLoader = new PersonDataCSVReader();
        StudentService studentService = new StudentServiceImpl(dataLoader);

        try {
            studentService.loadStudentData("src/main/resources/students.csv");
            CommandBuilder commandBuilder = new CommandBuilder(studentService);

            System.out.println("Выберите какую команду выполнить:\n" +
                    "a - Найти среднюю оценку в 10 и 11 классах\n" +
                    "b - Найти отличников старше 14 лет\n" +
                    "c - Найти всех студентов с заданной фамилией\n" +
                    "d - Найти среднюю оценку в 10 и 11 классе (из БД)\n" +
                    "e - Найти отличников старше 14 лет (из БД)\n" +
                    "f - Найти среднюю оценку ученика по фамилии (из БД)\n" +
                    "q - Закончить\n");

            boolean proceed = true;
            while (proceed && SCANNER.hasNext()) {
                String choice = SCANNER.nextLine();
                CommandEnum command;
                switch (choice) {
                    case "a" -> command = CommandEnum.FIND_AVERAGE_GROUP_GRADE;
                    case "b" -> command = CommandEnum.FIND_EXCELLENT_PERSON_AGED_ABOVE;
                    case "c" -> {
                        command = CommandEnum.FIND_PERSON_BY_FAMILY_NAME;
                    }
                    case "d" -> command = CommandEnum.FIND_AVERAGE_GROUP_GRADE_FROM_DB;
                    case "e" -> command = CommandEnum.FIND_EXCELLENT_PERSON_ABOVE_14_FROM_DB;
                    case "f" -> command = CommandEnum.FIND_AVERAGE_GRADE_PERSON_BY_FAMILY_NAME_FROM_DB;
                    case "q" -> {
                        proceed = false;
                        continue;
                    }
                    default -> {
                        System.out.println("Такой команды нет");
                        continue;
                    }
                }
                commandBuilder.build(command).execute();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}