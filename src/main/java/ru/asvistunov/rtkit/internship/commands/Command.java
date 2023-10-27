package ru.asvistunov.rtkit.internship.commands;

/**
 * Интерфейс Command представляет собой шаблон для создания команд, которые можно выполнять.
 */
public interface Command {
    /**
     * Метод определяет действие, которое выполняется при вызове команды.
     */
    void execute();
}
