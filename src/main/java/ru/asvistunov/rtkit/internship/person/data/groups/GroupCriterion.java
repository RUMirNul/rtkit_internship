package ru.asvistunov.rtkit.internship.person.data.groups;

import ru.asvistunov.rtkit.internship.person.data.Person;

/**
 * Функциональный интерфейс GroupCriterion<T> предоставляет метод, который по объекту типа Person
 * определяет ключ (критерий), по которому этот объект будет отнесен к определенной группе.
 *
 * @param <T> Тип ключа группы, который будет определен на основе объекта Person.
 */
@FunctionalInterface
public interface GroupCriterion<T> {
    /**
     * Определяет ключ (критерий) группировки на основе объекта Person.
     *
     * @param person Объект Person, по которому определяется ключ группировки.
     * @return Ключ (критерий) группировки типа T.
     */
    T getGroupKey(Person person);
}
