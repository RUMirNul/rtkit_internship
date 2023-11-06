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
    FIND_PERSON_BY_FAMILY_NAME,
    /**
     * Найти среднюю оценку в 10 и 11 классе в БД.
     */
    FIND_AVERAGE_GROUP_GRADE_FROM_DB,
    /**
     * Найти отличников старше 14 лет в БД.
     */
    FIND_EXCELLENT_PERSON_ABOVE_14_FROM_DB,
    /**
     * Найти среднюю оценку людей с одинаковой фамилией в БД.
     */
    FIND_AVERAGE_GRADE_PERSON_BY_FAMILY_NAME_FROM_DB;
}
