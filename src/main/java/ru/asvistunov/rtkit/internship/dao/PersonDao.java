package ru.asvistunov.rtkit.internship.dao;

import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;

public interface PersonDao {
    List<Person> getStudentList();
    List<Person> getStudentListByGroupNumber(int groupNumber);
}
