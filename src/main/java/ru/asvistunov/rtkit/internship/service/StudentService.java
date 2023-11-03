package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.dto.PersonDto;
import ru.asvistunov.rtkit.internship.dto.PersonGradeUpdateDto;
import ru.asvistunov.rtkit.internship.person.data.Person;

import java.util.List;

/**
 * Интерфейс StudentService определяет методы, необходимые для обслуживания студентов, включая
 * загрузку данных и получение списка студентов.
 */
public interface StudentService {

    /**
     * Возвращает список студентов, загруженных из файла.
     *
     * @return Список объектов типа Person, представляющих студентов.
     */
    List<Person> getStudentList();

    /**
     * Возвращает список студентов, относящихся к указанной группе.
     *
     * @param groupNumber Номер группы, для которой необходимо получить список студентов.
     * @return Список объектов типа Person, представляющих студентов из указанной группы.
     */
    List<Person> getStudentListByGroupNumber(int groupNumber);

    /**
     * Возвращает средние оценки студентов для указанной группы в форме списка DTO объектов PersonDto.
     *
     * @param groupNumber Номер группы, для которой необходимо рассчитать средние оценки студентов.
     * @return Список объектов типа PersonDto, представляющих студентов со средними оценками из указанной группы.
     */
    List<PersonDto> getMeanGradeStudentByGroupNumber(int groupNumber);

    /**
     * Обновляет оценку студента на указанном предмете в указанной группе.
     *
     * @param personGradeUpdateDto DTO объект, содержащий информацию о студенте, предмете и новой оценке.
     * @return Обновленный объект типа Person с новой оценкой.
     * @throws Exception В случае, если произошла ошибка при обновлении оценки.
     */
    Person updatePersonGrade(PersonGradeUpdateDto personGradeUpdateDto) throws Exception;
}