package ru.asvistunov.rtkit.internship;

import ru.asvistunov.rtkit.internship.person.PersonDataQuerys;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.groups.ClassroomDataGroups;
import ru.asvistunov.rtkit.internship.person.data.groups.PersonAgeDataGroups;
import ru.asvistunov.rtkit.internship.person.data.groups.PersonDataGroups;
import ru.asvistunov.rtkit.internship.person.data.groups.PersonNameDataGroups;
import ru.asvistunov.rtkit.internship.person.inputs.PersonDataCSVReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassroomDataGroups classroomDataGroups = new ClassroomDataGroups();
        PersonAgeDataGroups personAgeDataGroups = new PersonAgeDataGroups();
        PersonNameDataGroups personNameDataGroups = new PersonNameDataGroups();

        List<PersonDataGroups> personDataGroupsList = new ArrayList<>();
        personDataGroupsList.add(classroomDataGroups);
        personDataGroupsList.add(personAgeDataGroups);
        personDataGroupsList.add(personNameDataGroups);

        try (Scanner sc = new Scanner(System.in)) {
            PersonDataCSVReader.loadPersonData("src/main/resources/students.csv", personDataGroupsList);

            double averageGrade10 = PersonDataQuerys.findAverageGroupGrade(10, classroomDataGroups);
            System.out.printf("Средняя оценка в 10 класс: %.2f\n", averageGrade10);

            double averageGrade11 = PersonDataQuerys.findAverageGroupGrade(11, classroomDataGroups);
            System.out.printf("Средняя оценка в 11 класс: %.2f\n", averageGrade11);
            System.out.println("\n");

            printPerson(PersonDataQuerys.findExcellentPersonAgedAbove(14, personAgeDataGroups));
            System.out.println("\n");

            System.out.print("Введите фамилию ученика: ");
            String familyName = sc.nextLine();
            printPerson(PersonDataQuerys.findPersonByFamilyName(familyName, personNameDataGroups));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printPerson(Person[] persons) {
        for (Person person : persons) {
            System.out.printf("%15s %12s, Возраст: %3d , класс: %3d\n",
                    person.getFamilyName(), person.getName(), person.getAge(), person.getGroup());
        }
    }

}