package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

public class FindAverageGroupGradeFromDBCommand implements Command {

    @Override
    public void execute() {
        StatisticsBDService.getAverageGrade(11);
        StatisticsBDService.getAverageGrade(10);
    }
}
