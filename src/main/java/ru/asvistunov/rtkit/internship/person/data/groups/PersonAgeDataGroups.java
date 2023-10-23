package ru.asvistunov.rtkit.internship.person.data.groups;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.collections.MyHashMap;
import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;
import java.util.Map;

/**
 * Класс `PersonAgeDataGroups` реализует интерфейс `PersonDataGroups` и предоставляет механизм хранения и
 * организации данных о людях (Person) в группах, где группы определяются на основе возраста.
 */
public class PersonAgeDataGroups implements PersonDataGroups {
    /**
     * Используется Map, а именно HashMap. Потому что операции поиска и добавления занимают константное время O(1)
     * (В худшем случае O(n)).
     * В качестве значения используется List, а именно ArrayList, что бы добавлять в список людей с одинаковыми
     * параметрами за константное время O(1) (В худшем случае O(n)).
     */
    private final Map<Integer, List<Person>> personMap;

    /**
     * Конструктор класса создает экземпляр HashMap для хранения данных о людях в группах, основанных на возрасте.
     */
    public PersonAgeDataGroups() {
        this.personMap = new MyHashMap<>();
    }

    /**
     * Добавляет человека (Person) в соответствующую группу на основе его возраста. Если группа с заданным возрастом
     * уже существует, добавляет к существующему списку, иначе создает новый список.
     *
     * @param person Объект Person, которого надо добавить в соответствующий класс (группу).
     */
    @Override
    public void addPerson(Person person) {
        List<Person> persons = personMap.getOrDefault(person.getAge(), new MyArrayList<>());
        persons.add(person);
        personMap.put(person.getAge(), persons);
    }

    /**
     * Получает всех людей в заданной группе на основе возраста. Если группа с заданным возрастом существует, возвращает
     * массив Person, иначе возвращает пустой массив.
     *
     * @param age Возраст, по которому надо найти группу людей.
     * @return Массив Person из запрошенной группы.
     */
    public Person[] getPersons(int age) {
        return personMap.getOrDefault(age, new MyArrayList<>()).toArray(new Person[0]);
    }

    /**
     * Возвращает массив с возрастами, которые представлены в хранилище данных.
     */
    public Integer[] getAges() {
        return personMap.keySet().toArray(new Integer[0]);
    }
}
