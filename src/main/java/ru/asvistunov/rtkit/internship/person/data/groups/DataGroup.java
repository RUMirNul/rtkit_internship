package ru.asvistunov.rtkit.internship.person.data.groups;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.collections.MyHashMap;
import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс DataGroup<T>}представляет группировку объектов типа Person по критерию типа T.
 * Группировка выполняется с использованием критерия, предоставленного в виде интерфейса GroupCriterion.
 *
 * @param <T> Тип ключа группы.
 */
public class DataGroup<T> {
    private final Map<T, List<Person>> personGroupsMap;
    private final GroupCriterion<T> groupCriterion;

    /**
     * Конструктор класса DataGroup.
     *
     * @param groupCriterion Критерий, определяющий, к какой группе будет отнесен объект Person.
     */
    public DataGroup(GroupCriterion<T> groupCriterion) {
        this.personGroupsMap = new MyHashMap<>();
        this.groupCriterion = groupCriterion;
    }

    /**
     * Добавляет объект Person в соответствующую группу, используя критерий.
     *
     * @param person Объект Person, который должен быть добавлен в группу.
     */
    public void addPerson(Person person) {
        T groupKey = groupCriterion.getGroupKey(person);
        List<Person> personList = personGroupsMap.getOrDefault(groupKey, new MyArrayList<>());
        personList.add(person);
        personGroupsMap.put(groupKey, personList);
    }

    /**
     * Получает массив объектов Person, отнесенных к указанной группе.
     *
     * @param key Ключ группы.
     * @return Массив объектов Person, принадлежащих указанной группе.
     */
    public Person[] getPersons(T key) {
        return personGroupsMap.getOrDefault(key, new MyArrayList<>()).toArray(new Person[0]);
    }

    /**
     * Возвращает множество ключей всех существующих групп.
     *
     * @return Множество ключей групп.
     */
    public Set<T> getKeys() {
        return personGroupsMap.keySet();
    }
}
