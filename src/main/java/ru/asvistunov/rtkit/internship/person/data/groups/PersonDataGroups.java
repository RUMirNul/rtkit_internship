package ru.asvistunov.rtkit.internship.person.data.groups;

import ru.asvistunov.rtkit.internship.person.data.Person;

/**
 * Интерфейс `PersonDataGroups` определяет метод `addPerson`, который должен быть реализован в классах,
 * предоставляющих группировку данных о людях по разным критериям.
 */
public interface PersonDataGroups {
    /**
     * Метод `addPerson` добавляет информацию о человеке в соответствующую группу.
     *
     * @param person Объект класса `Person`, представляющий информацию о человеке.
     */
    void addPerson(Person person);
}
