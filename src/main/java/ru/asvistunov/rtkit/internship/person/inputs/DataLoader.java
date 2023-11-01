package ru.asvistunov.rtkit.internship.person.inputs;

import java.util.List;

/**
 * Интерфейс DataLoader<T, P> определяет метод для загрузки данных типа T из указанного источника,
 * указанного типа P.
 *
 * @param <T> Тип данных, которые будут загружены.
 * @param <P> Тип параметра, который указывает на источник данных (например, путь к файлу).
 */
public interface DataLoader<T, P> {
    List<T> loadData(P path) throws Exception;
}
