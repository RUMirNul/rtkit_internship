package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.dao.PersonDao;
import ru.asvistunov.rtkit.internship.dao.PersonDaoImpl;
import ru.asvistunov.rtkit.internship.dto.PersonDto;
import ru.asvistunov.rtkit.internship.dto.PersonGradeUpdateDto;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;

import java.util.List;

/**
 * Класс StudentServiceImpl реализует интерфейс StudentService и предоставляет методы для
 * загрузки и получения данных о студентах.
 */
public class StudentServiceImpl implements StudentService {
    private final PersonDao personDao;

    /**
     * Приватный конструктор для инициализации объекта класса StudentServiceImpl.
     */
    private StudentServiceImpl() {
        this.personDao = PersonDaoImpl.getInstance();
    }

    /**
     * Приватный вложенный класс InstanceHolder, обеспечивающий ленивую инициализацию
     * синглтон-экземпляра StudentServiceImpl.
     */
    private static final class InstanceHolder {
        private static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
    }

    /**
     * Метод для получения экземпляра синглтона StudentServiceImpl.
     *
     * @return Экземпляр класса StudentServiceImpl.
     */
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

    /**
     * Возвращает список студентов с заданным номером группы.
     *
     * @param groupNumber Номер группы студентов.
     * @return Список объектов типа Person, представляющих студентов в указанной группе.
     */
    @Override
    public List<Person> getStudentListByGroupNumber(int groupNumber) {
        return personDao.getStudentListByGroupNumber(groupNumber);
    }

    /**
     * Возвращает список объектов PersonDto, представляющих средние оценки студентов в указанной группе.
     *
     * @param groupNumber Номер группы студентов.
     * @return Список объектов типа PersonDto, представляющих средние оценки студентов в указанной группе.
     */
    @Override
    public List<PersonDto> getMeanGradeStudentByGroupNumber(int groupNumber) {
        List<Person> personList = personDao.getStudentListByGroupNumber(groupNumber);
        List<PersonDto> resultList = new MyArrayList<>();

        for (Person person : personList) {
            double meanGrade = 0;
            for (SubjectGrade subjectGrade : person.getSubjectGradeList()) {
                meanGrade += (double) subjectGrade.getGrade() / person.getSubjectGradeList().size();
            }
            meanGrade = (double) Math.round(meanGrade * 100) / 100;

            PersonDto personDto = new PersonDto();
            personDto.setName(person.getName());
            personDto.setFamilyName(person.getFamilyName());
            personDto.setGroup(person.getGroup());
            personDto.setAge(person.getAge());
            personDto.setMeanGrade(meanGrade);

            resultList.add(personDto);
        }

        return resultList;
    }

    /**
     * Обновляет оценку конкретного студента по ФИО, номеру группы и названию предмета.
     *
     * @param personGradeUpdateDto Объект, содержащий информацию о студенте и новой оценке.
     * @return Обновленный объект типа Person с обновленными оценками.
     * @throws Exception Если студент для обновления не найден или данные некорректны.
     */
    @Override
    public Person updatePersonGrade(PersonGradeUpdateDto personGradeUpdateDto) throws Exception {
        List<Person> persons = personDao.getStudentListByGroupNumber(personGradeUpdateDto.getGroup());
        Person person = new Person();

        for (Person p : persons) {
            if (p.getName().equals(personGradeUpdateDto.getName())
                    && p.getFamilyName().equals(personGradeUpdateDto.getFamilyName())) {
                person = p;
                break;
            }
        }

        if (!isValidPerson(person)) {
            throw new Exception("Человек для обновления не найден.");
        }

        person.getSubjectGradeList().stream()
                .filter(p -> p.getSubjectName().equals(personGradeUpdateDto.getSubjectName()))
                .forEach(sg -> sg.setGrade(personGradeUpdateDto.getNewGrade()));

        return personDao.updatePerson(person);
    }

    /**
     * Проверяет, что переданный объект Person является корректным.
     *
     * @param person Объект Person, который требуется проверить.
     * @return true, если объект корректен, иначе false.
     */
    private boolean isValidPerson(Person person) {
        if (person.getName() != null
                && person.getFamilyName() != null
                && person.getAge() > 0) {
            return true;
        }
        return false;
    }
}
