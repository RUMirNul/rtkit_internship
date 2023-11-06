package ru.asvistunov.rtkit.internship.commands.commands;

import ru.asvistunov.rtkit.internship.commands.Command;
import ru.asvistunov.rtkit.internship.service.StatisticsBDService;

public class FindExcellentPersonAbove14FromDBCommand implements Command {
    @Override
    public void execute() {
        StatisticsBDService.getTopStudentsAbove14();
    }
}
