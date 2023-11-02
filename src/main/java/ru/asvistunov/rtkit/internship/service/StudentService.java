package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.dto.PersonDto;
import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;

/**
 * Интерфейс StudentService определяет методы, необходимые для обслуживания студентов, включая
 * загрузку данных и получение списка студентов.
 */
public interface StudentService {

    /**
     * Возвращает список студентов, загруженных из файла.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    List<Person> getStudentList();
    List<Person> getStudentListByGroupNumber(int groupNumber);
    List<PersonDto> getMeanGradeStudentByGroupNumber(int groupNumber);
}