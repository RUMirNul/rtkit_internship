package ru.asvistunov.rtkit.internship.person;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;
import ru.asvistunov.rtkit.internship.person.data.groups.DataGroup;

import java.util.List;
import java.util.Set;

/**
 * Класс `PersonDataQuerys` предоставляет статические методы для выполнения запросов к данным о людях.
 */
public class PersonDataQuerys {
    // Используя ClassroomDataGroups находим список учеников за O(1) - в среднем, в худшем за O(n).
    // Чтобы найти среднюю оценку в классе, надо проверить K предметов у M учеников, следовательно, сложность O(m*k)
    // Общая сложность алгоритма: O(m*k).

    /**
     * Метод `findAverageGroupGrade` находит среднюю оценку в указанной группе на основе данных из `ClassroomDataGroups`.
     *
     * @param groupNum   Номер группы.
     * @param dataGroups Предоставление доступа к данным.
     * @return Средняя оценка в группе.
     */
    public static double findAverageGroupGrade(int groupNum, DataGroup<Integer> dataGroups) {
        double averageGrade = 0;
        Person[] persons = dataGroups.getPersons(groupNum);

        if (persons.length == 0) return 0;

        for (Person person : persons) {
            double personAverageGrade = 0;

            for (SubjectGrade subjectGrade : person.getSubjectGradeList()) {
                personAverageGrade += subjectGrade.getGrade();
            }
            averageGrade += personAverageGrade / person.getSubjectGradeList().size();
        }
        return averageGrade / persons.length;
    }

    // Используя PersonAgeDataGroups находим список учеников за O(1) - в среднем, в худшем за O(n).
    // Количество списков учеников старше определенного возраста - k.
    // Количество учеников в списке n.
    // Количество оценок у каждого ученика m. Но если встречается оценка отличная от 5, то поиск по оценкам будет прерван.
    // Тогда m' означает количество рассмотренных оценок.
    // Средняя сложность: O(k*n*m'). Наихудшая сложность O(k*n*m).

    /**
     * Метод `findExcellentPersonAgedAbove` находит учеников, старше указанного возраста, с отличными оценками
     * на основе данных из `PersonAgeDataGroups`.
     *
     * @param ageAbove   Минимальный возраст для поиска отличников.
     * @param dataGroups Предоставление доступа к данным.
     * @return Массив учеников с отличными оценками, старше указанного возраста.
     */
    public static Person[] findExcellentPersonAgedAbove(int ageAbove, DataGroup<Integer> dataGroups) {
        Set<Integer> ages = dataGroups.getKeys();

        List<Person> excellentPersons = new MyArrayList<>();

        for (int age : ages) {
            if (age <= ageAbove) {
                continue;
            }

            Person[] persons = dataGroups.getPersons(age);
            for (Person person : persons) {
                boolean excellent = false;
                List<SubjectGrade> subjectGrades = person.getSubjectGradeList();
                for (SubjectGrade subjectGrade : subjectGrades) {
                    excellent = true;
                    if (subjectGrade.getGrade() != 5) {
                        excellent = false;
                        break;
                    }
                }
                if (excellent) {
                    excellentPersons.add(person);
                }
            }
        }
        return excellentPersons.toArray(new Person[0]);
    }


    // Используя PersonNameDataGroups находим список учеников, у которых фамилия начинается на ту же букву, что и у той,
    // которая была получена в параметрах. Поиск производится за O(1) - в среднем, в худшем за O(n).
    // Пройтись по всем фамилиям из списка занимает O(n), где n - количество учеников в списке.
    // Сравнить строки с помощью equals в худшем случае O(k), где k - количество символов в строке.
    // Но сравнение строк прерывается, если встречаются разные символы. Тогда обозначим k' - количество
    // рассмотренных символов.
    // Средняя сложность: O(n*k'). Наихудшая сложность O(n*k).

    /**
     * Метод `findPersonByFamilyName` находит учеников, у которых фамилия начинается с указанной буквы,
     * на основе данных из `PersonNameDataGroups`.
     *
     * @param familyName Фамилия для поиска учеников.
     * @param dataGroups Предоставление доступа к данным.
     * @return Массив учеников с указанной фамилией.
     */
    public static Person[] findPersonByFamilyName(String familyName, DataGroup<Character> dataGroups) {
        Person[] persons = dataGroups.getPersons(familyName.charAt(0));

        List<Person> personsFamilyNameMatches = new MyArrayList<>();
        for (Person person : persons) {
            if (familyName.equals(person.getFamilyName())) {
                personsFamilyNameMatches.add(person);
            }
        }
        return personsFamilyNameMatches.toArray(new Person[0]);
    }

    public static void printPerson(Person[] persons) {
        for (Person person : persons) {
            System.out.printf("%15s %12s, Возраст: %3d , класс: %3d\n",
                    person.getFamilyName(), person.getName(), person.getAge(), person.getGroup());
        }
    }
}
