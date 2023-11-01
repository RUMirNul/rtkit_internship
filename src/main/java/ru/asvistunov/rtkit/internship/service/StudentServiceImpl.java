package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.inputs.DataLoader;
import ru.asvistunov.rtkit.internship.service.StudentService;

import java.util.List;

/**
 * Класс StudentServiceImpl реализует интерфейс StudentService и предоставляет методы для
 * загрузки и получения данных о студентах.
 */
public class StudentServiceImpl implements StudentService {
    private final DataLoader<Person, String> dataLoader;
    private List<Person> personList;

    /**
     * Конструктор класса, принимающий объект типа DataLoader< Person, String >.
     *
     * @param dataLoader Объект, выполняющий загрузку данных о студентах из файла.
     */
    public StudentServiceImpl(DataLoader<Person, String> dataLoader) {
        this.dataLoader = dataLoader;
        this.personList = new MyArrayList<>();
    }

    /**
     * Загружает данные о студентах из указанного файла.
     *
     * @param filePath Путь к файлу, содержащему данные о студентах.
     * @throws Exception Если возникли проблемы при загрузке данных.
     */
    @Override
    public void loadStudentData(String filePath) throws Exception {
        personList = dataLoader.loadData(filePath);
    }

    /**
     * Возвращает список студентов, загруженных из файла.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    @Override
    public List<Person> getStudentList() {
        return personList;
    }
}
