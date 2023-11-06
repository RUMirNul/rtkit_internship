package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.Main;
import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

public class FindAverageGradePersonByFamilyNameFromDBCommand  implements Command {
    @Override
    public void execute() {
        System.out.print("Введите фамилию ученика: ");
        String familyName = Main.SCANNER.nextLine();
        StatisticsBDService.getStudentAverageByFamilyName(familyName);
    }
}
