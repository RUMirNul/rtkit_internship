package ru.asvistunov.rtkit.internship.dao;

import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;

/**
 * Интерфейс PersonDao предоставляет методы для доступа к данным о студентах.
 */
public interface PersonDao {
    /**
     * Метод для получения списка всех студентов.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    List<Person> getStudentList();

    /**
     * Метод для получения списка студентов по номеру группы.
     *
     * @param groupNumber Номер группы, по которой выполняется фильтрация студентов.
     * @return Список объектов типа Person, представляющих студентов в указанной группе.
     */
    List<Person> getStudentListByGroupNumber(int groupNumber);

    /**
     * Метод для обновления информации о студенте.
     *
     * @param person Объект типа Person, представляющий студента, который требует обновления.
     * @return Обновленный объект Person с обновленными данными.
     */
    Person updatePerson(Person person);
}
