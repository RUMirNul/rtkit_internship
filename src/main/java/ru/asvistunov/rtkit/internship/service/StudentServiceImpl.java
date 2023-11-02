package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.dao.PersonDao;
import ru.asvistunov.rtkit.internship.dao.PersonDaoImpl;
import ru.asvistunov.rtkit.internship.dto.PersonDto;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;

import java.util.List;

/**
 * Класс StudentServiceImpl реализует интерфейс StudentService и предоставляет методы для
 * загрузки и получения данных о студентах.
 */
public class StudentServiceImpl implements StudentService {
    private final PersonDao personDao;

    private StudentServiceImpl() {
        this.personDao = PersonDaoImpl.getInstance();
    }

    private static final class InstanceHolder {
        private static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
    }

    public static StudentServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Возвращает список студентов, загруженных из файла.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    @Override
    public List<Person> getStudentList() {
        return personDao.getStudentList();
    }

    @Override
    public List<Person> getStudentListByGroupNumber(int groupNumber) {
        return personDao.getStudentListByGroupNumber(groupNumber);
    }

    @Override
    public List<PersonDto> getMeanGradeStudentByGroupNumber(int groupNumber) {
        List<Person> personList = personDao.getStudentListByGroupNumber(groupNumber);
        List<PersonDto> resultList = new MyArrayList<>();
        for (Person person : personList) {
            PersonDto personDto = new PersonDto();
            personDto.setName(person.getName());
            personDto.setFamilyName(person.getFamilyName());
            personDto.setGroup(person.getGroup());
            personDto.setAge(person.getAge());

            double meanGrade = 0;
            for (SubjectGrade subjectGrade : person.getSubjectGradeList()) {
                meanGrade += (double) subjectGrade.getGrade() / person.getSubjectGradeList().size();
            }
            meanGrade = (double) Math.round(meanGrade * 100) / 100;
            personDto.setMeanGrade(meanGrade);

            resultList.add(personDto);
        }
        return resultList;
    }
}
