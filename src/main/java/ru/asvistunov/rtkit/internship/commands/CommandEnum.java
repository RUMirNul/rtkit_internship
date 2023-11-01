package ru.asvistunov.rtkit.internship.commands;

/**
 * Перечисление CommandEnum представляет доступные команды для выполнения.
 * Каждое значение этого перечисления соответствует конкретной команде.
 */
public enum CommandEnum {
    /**
     * Найти среднюю оценку в указанной группе.
     */
    FIND_AVERAGE_GROUP_GRADE,
    /**
     * Найти учеников старше указанного возраста с отличными оценками.
     */
    FIND_EXCELLENT_PERSON_AGED_ABOVE,
    /**
     * Найти учеников с указанной фамилией.
     */
    FIND_PERSON_BY_FAMILY_NAME;
}
