package ru.asvistunov.rtkit.internship.dao;

import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.groups.DataGroup;
import ru.asvistunov.rtkit.internship.person.inputs.DataLoader;
import ru.asvistunov.rtkit.internship.person.inputs.PersonDataCSVReader;

import java.util.Arrays;
import java.util.List;

public class PersonDaoImpl implements PersonDao{
    private final DataLoader<Person, String> dataLoader;
    private final List<Person> personList;
    private final DataGroup<Integer> groupNumberDataGroup;

    private PersonDaoImpl(DataLoader<Person, String> dataLoader) throws Exception {
        this.dataLoader = dataLoader;
        this.personList = dataLoader.loadData("src/main/resources/students.csv");
        this.groupNumberDataGroup = new DataGroup<>(Person::getGroup);
        personList.forEach(groupNumberDataGroup::addPerson);
    }

    @Override
    public List<Person> getStudentList() {
        return personList;
    }

    @Override
    public List<Person> getStudentListByGroupNumber(int groupNumber) {
        return Arrays.asList(groupNumberDataGroup.getPersons(groupNumber));
    }

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

    public static synchronized PersonDaoImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
