package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;

/**
 * Интерфейс StudentService определяет методы, необходимые для обслуживания студентов, включая
 * загрузку данных и получение списка студентов.
 */
public interface StudentService {
    /**
     * Загружает данные о студентах из указанного файла.
     *
     * @param filePath Путь к файлу, содержащему данные о студентах.
     * @throws Exception Если возникли проблемы при загрузке данных.
     */
    void loadStudentData(String filePath) throws Exception;

    /**
     * Возвращает список студентов, загруженных из файла.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    List<Person> getStudentList();
}