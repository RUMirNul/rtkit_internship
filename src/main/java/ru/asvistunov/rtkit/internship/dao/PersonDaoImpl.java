package ru.asvistunov.rtkit.internship.dao;

import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.groups.DataGroup;
import ru.asvistunov.rtkit.internship.person.inputs.DataLoader;
import ru.asvistunov.rtkit.internship.person.inputs.PersonDataCSVReader;

import java.util.Arrays;
import java.util.List;

/**
 * Класс PersonDaoImpl реализует интерфейс PersonDao и предоставляет доступ к данным о студентах.
 */
public class PersonDaoImpl implements PersonDao {
    private final DataLoader<Person, String> dataLoader;
    private final List<Person> personList;
    private final DataGroup<Integer> groupNumberDataGroup;

    /**
     * Конструктор класса, принимающий объект DataLoader для загрузки данных о студентах.
     *
     * @param dataLoader Объект, выполняющий загрузку данных о студентах из файла.
     * @throws Exception Если возникли проблемы при загрузке данных.
     */
    private PersonDaoImpl(DataLoader<Person, String> dataLoader) throws Exception {
        this.dataLoader = dataLoader;
        this.personList = dataLoader.loadData("students.csv");
        this.groupNumberDataGroup = new DataGroup<>(Person::getGroup);
        personList.forEach(groupNumberDataGroup::addPerson);
    }

    /**
     * Метод для получения списка всех студентов.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    @Override
    public List<Person> getStudentList() {
        return personList;
    }

    /**
     * Метод для получения списка студентов по номеру группы.
     *
     * @param groupNumber Номер группы, по которой выполняется фильтрация студентов.
     * @return Список объектов типа Person, представляющих студентов в указанной группе.
     */
    @Override
    public List<Person> getStudentListByGroupNumber(int groupNumber) {
        return Arrays.asList(groupNumberDataGroup.getPersons(groupNumber));
    }

    /**
     * Метод для обновления информации о студенте.
     *
     * @param person Объект типа Person, представляющий студента, который требует обновления.
     * @return Обновленный объект Person с обновленными данными.
     */
    @Override
    public Person updatePerson(Person person) {
        for (Person p : personList) {
            if (p.getName().equals(person.getName())
                    && p.getFamilyName().equals(person.getFamilyName())
                    && p.getGroup() == person.getGroup()) {
                p.setAge(person.getAge());
                p.setSubjectGradeList(person.getSubjectGradeList());

                return p;
            }
        }
        return new Person();
    }

    /**
     * Приватный вложенный класс InstanceHolder, обеспечивающий ленивую инициализацию
     * синглтон-экземпляра PersonDaoImpl.
     */
    private static final class InstanceHolder {
        private static final PersonDaoImpl INSTANCE;

        static {
            try {
                INSTANCE = new PersonDaoImpl(new PersonDataCSVReader());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод для получения экземпляра синглтона PersonDaoImpl.
     *
     * @return Экземпляр класса PersonDaoImpl.
     */
    public static synchronized PersonDaoImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
