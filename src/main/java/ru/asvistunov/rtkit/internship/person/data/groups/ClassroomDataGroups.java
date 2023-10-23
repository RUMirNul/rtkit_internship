package ru.asvistunov.rtkit.internship.person.data.groups;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.collections.MyHashMap;
import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;
import java.util.Map;

/**
 * Класс `ClassroomDataGroups` реализует интерфейс `PersonDataGroups` и предоставляет механизм хранения и
 * организации данных о людях (Person) в группах классов.
 */
public class ClassroomDataGroups implements PersonDataGroups {
    /**
     * Используется Map, а именно HashMap. Потому что операции поиска и добавления занимают константное время O(1)
     * (В худшем случае O(n)).
     * В качестве значения используется List, а именно ArrayList, что бы добавлять в список людей с одинаковыми
     * параметрами за константное время O(1) (В худшем случае O(n)).
     */
    private final Map<Integer, List<Person>> personMap;

    /**
     * Конструктор класса создает экземпляр HashMap для хранения данных о людях в классах.
     */
    public ClassroomDataGroups() {
        this.personMap = new MyHashMap<>();
    }

    /**
     * Добавляет человек (Person) в соответствующий класс (группу). Если класс уже существует, то добавляет к
     * соответствующему списку, иначе создает новый список с этим человек.
     *
     * @param person Объект Person, которого надо добавить в соответствующий класс (группу).
     */
    @Override
    public void addPerson(Person person) {
        List<Person> persons = personMap.getOrDefault(person.getGroup(), new MyArrayList<>());
        persons.add(person);
        personMap.put(person.getGroup(), persons);
    }

    /**
     * Находит всех людей в заданном классе(группе). Если класс существует, то возвращает массив Person, иначе
     * возвращает пустой массив.
     *
     * @param groupNum Номер класса(группы), по которому надо найти людей.
     * @return Массив Person из запрошенной группы.
     */
    public Person[] getPersons(int groupNum) {
        if (personMap.containsKey(groupNum)) {
            return personMap.get(groupNum).toArray(new Person[0]);
        }
        return new Person[0];
    }

    /**
     * Возвращает массив с номерами всех существующих классов (групп) в хранилище.
     *
     * @return Массив с номерами всех существующих классов (групп).
     */
    public Integer[] getGroups() {
        return personMap.keySet().toArray(new Integer[0]);
    }
}
