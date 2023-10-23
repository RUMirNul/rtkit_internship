package ru.asvistunov.rtkit.internship.person.data.groups;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.collections.MyHashMap;
import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;
import java.util.Map;

/**
 * Класс `PersonNameDataGroups` реализует интерфейс `PersonDataGroups` и предоставляет механизм хранения и
 * организации данных о людях (Person) в группах, где группы определяются на основе первой буквы фамилии.
 */
public class PersonNameDataGroups implements PersonDataGroups {
    /**
     * Используется Map, а именно HashMap. Потому что операции поиска и добавления занимают константное время O(1)
     * (В худшем случае O(n)).
     * В качестве значения используется List, а именно ArrayList, что бы добавлять в список людей с одинаковыми
     * параметрами за константное время O(1) (В худшем случае O(n)).
     */
    private final Map<Character, List<Person>> personMap;

    /**
     * Конструктор класса создает экземпляр HashMap для хранения данных о людях в группах, основанных на первой букве фамилии.
     */
    public PersonNameDataGroups() {
        this.personMap = new MyHashMap<>();
    }

    /**
     * Добавляет человека (Person) в соответствующую группу на основе первой буквы его фамилии. Если буква отсутствует
     * или пустая, игнорирует добавление.
     *
     * @param person Объект Person, которого надо добавить в соответствующий класс (группу).
     */
    @Override
    public void addPerson(Person person) {
        if (person.getFamilyName() == null || person.getFamilyName().isBlank()) {
            return;
        }
        Character firstLetter = person.getFamilyName().charAt(0);
        List<Person> persons = personMap.getOrDefault(firstLetter, new MyArrayList<>());
        persons.add(person);
        personMap.put(firstLetter, persons);
    }

    /**
     * Получает всех людей, чьи фамилии начинаются с заданной буквы. Если группа с заданной буквой существует, возвращает
     * массив Person, иначе возвращает пустой массив.
     *
     * @param firstLetterFamilyName Первая буква фамилии.
     * @return Массив Person из запрошенной группы.
     */
    public Person[] getPersons(char firstLetterFamilyName) {
        return personMap.getOrDefault(firstLetterFamilyName, new MyArrayList<>()).toArray(new Person[0]);
    }

    /**
     * Возвращает массив с первыми буквами фамилий, которые представлены в хранилище данных.
     */
    public Character[] getFirstCharacterFamilyName() {
        return personMap.keySet().toArray(new Character[0]);
    }
}
