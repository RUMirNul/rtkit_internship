package ru.asvistunov.rtkit.internship.person.inputs;

import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;
import ru.asvistunov.rtkit.internship.person.data.groups.PersonDataGroups;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Класс `PersonDataCSVReader` предоставляет методы для чтения данных о людях из CSV-файла и загрузки их в список
 * групп (PersonDataGroups).
 */
public class PersonDataCSVReader {
    public static final String UNDEFINED_SUBJECT_NAME = "undefined";

    /**
     * Метод `loadPersonData` загружает данные о людях из указанного CSV-файла и добавляет их в список групп.
     *
     * @param path                 Путь к CSV-файлу с данными о людях.
     * @param personDataGroupsList Список групп, в которые будут добавлены данные.
     * @throws Exception Если произошла ошибка при чтении файла или список групп пустой или null.
     */
    public static void loadPersonData(String path, List<PersonDataGroups> personDataGroupsList) throws Exception {
        if (personDataGroupsList == null || personDataGroupsList.size() == 0) {
            throw new Exception("Список классов, куда записывать данные пустой или null.");
        }

        try (BufferedReader csvReader = new BufferedReader(new FileReader(path))) {
            int count = 0;
            String[] columnsNames = null;
            String row;

            while ((row = csvReader.readLine()) != null) {

                if (count == 0) {
                    columnsNames = row.split(";");
                } else {
                    String[] data = row.split(";");

                    Person person = new Person();
                    person.setFamilyName(data[0]);
                    person.setName(data[1]);
                    person.setAge(Integer.parseInt(data[2]));
                    person.setGroup(Integer.parseInt(data[3]));

                    for (int i = 4; i < data.length; i++) {
                        SubjectGrade subjectGrade = new SubjectGrade(columnsNames.length < i ?
                                UNDEFINED_SUBJECT_NAME : columnsNames[i], Integer.parseInt(data[i]));
                        person.addSubjectGrade(subjectGrade);
                    }

                    for (PersonDataGroups pdg : personDataGroupsList) {
                        pdg.addPerson(person);
                    }
                }
                count++;
            }
        } catch (IOException e) {
            throw new Exception("Не удалось прочитать файл.");
        }
    }
}
