package ru.asvistunov.rtkit.internship.person.inputs;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Класс PersonDataCSVReader реализует интерфейс DataLoader< Person, String > и предназначен для
 * загрузки данных о людях из CSV-файла.
 */
public class PersonDataCSVReader implements DataLoader<Person, String> {

    /**
     * Константа, представляющая название предмета по умолчанию для оценок.
     */
    public static final String UNDEFINED_SUBJECT_NAME = "undefined";

    /**
     * Загружает данные о людях из указанного CSV-файла.
     *
     * @param filePath Путь к CSV-файлу, из которого будут загружены данные.
     * @return Список объектов типа Person, содержащий информацию о людях.
     * @throws Exception Если возникнут проблемы при чтении файла или парсинге данных.
     */
    @Override
    public List<Person> loadData(String filePath) throws Exception {
        if (!filePath.endsWith(".csv")) {
            throw new Exception("Неподдерживаемый формат файла.");
        }

        List<Person> personList = new MyArrayList<>();

        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath), "UTF-8"))) {
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
                    personList.add(person);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл. " + e.getMessage());
        }

        return personList;
    }
}