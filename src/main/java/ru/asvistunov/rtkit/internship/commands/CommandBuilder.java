package ru.asvistunov.rtkit.internship.commands;

import ru.asvistunov.rtkit.internship.commands.commands.*;
import ru.asvistunov.rtkit.internship.service.StudentService;

/**
 * Класс CommandBuilder предоставляет метод для создания конкретных команд в соответствии с заданным
 * перечислимым значением CommandEnum.
 */
public class CommandBuilder {
    private final StudentService studentService;

    /**
     * Создает новый объект CommandBuilder с указанным сервисом StudentService.
     *
     * @param studentService сервис, предоставляющий доступ к данным студентов.
     */
    public CommandBuilder(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Создает и возвращает конкретную команду на основе указанного перечислимого значения CommandEnum.
     *
     * @param command перечислимое значение, представляющее команду для выполнения.
     * @return объект команды, соответствующий указанному перечислимому значению.
     */
    public Command build(CommandEnum command) {
        return
                switch (command) {
                    case FIND_AVERAGE_GROUP_GRADE -> new FindAverageGroupGradeCommand(studentService);
                    case FIND_EXCELLENT_PERSON_AGED_ABOVE -> new FindExcellentPersonAgedAboveCommand(studentService);
                    case FIND_PERSON_BY_FAMILY_NAME -> new FindPersonByFamilyNameCommand(studentService);
                    case FIND_AVERAGE_GROUP_GRADE_FROM_DB -> new FindAverageGroupGradeFromDBCommand();
                    case FIND_EXCELLENT_PERSON_ABOVE_14_FROM_DB -> new FindExcellentPersonAbove14FromDBCommand();
                    case FIND_AVERAGE_GRADE_PERSON_BY_FAMILY_NAME_FROM_DB -> new FindAverageGradePersonByFamilyNameFromDBCommand();
                };
    }
}
