package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.collections.MyArrayList;
import ru.asvistunov.rtkit.internship.person.data.PersonAverageGradeDto;
import ru.asvistunov.rtkit.internship.person.data.PersonInfoDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс StatisticsBDService предоставляет методы для извлечения статистической информации
 * из базы данных, связанной с учебной деятельностью студентов.
 */
public class StatisticsBDService {

    /**
     * Метод getTopStudentsAbove14 извлекает информацию о студентах старше 14 лет, имеющих средний балл 5.0.
     *
     * @return Список с информацией о людях, являющихся отличниками и старше 14 лет.
     */
    public static List<PersonInfoDto> getTopStudentsAbove14() {
        Connection connection = DatabaseService.connect();

        String sql = "SELECT s.name, s.family_name, s.age, sg.group_name " +
                "FROM Students s " +
                "JOIN Grades g ON s.student_id = g.student_id " +
                "JOIN StudyGroups sg ON s.group_id = sg.group_id " +
                "WHERE s.age > 14 " +
                "GROUP BY s.student_id, s.name, s.family_name, s.age, sg.group_name " +
                "HAVING AVG(g.grade) = 5.0";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<PersonInfoDto> persons = new MyArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String familyName = resultSet.getString("family_name");
                int groupName = resultSet.getInt("group_name");

                PersonInfoDto person = new PersonInfoDto(familyName, name, age, groupName);
                persons.add(person);
            }

            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
        return new MyArrayList<>();
    }

    /**
     * Метод getStudentAverageByFamilyName извлекает средний балл студента по его фамилии.
     * Выводит результат на консоль в виде таблицы.
     *
     * @param familyName Фамилия студента, по которой выполняется поиск.
     * @return Список людей с их средней оценкой.
     */
    public static List<PersonAverageGradeDto> getStudentAverageByFamilyName(String familyName) {
        Connection connection = DatabaseService.connect();

        String sql = "SELECT s.name, s.family_name, sg.group_name, AVG(g.grade) " +
                "FROM Students s " +
                "JOIN Grades g ON s.student_id = g.student_id " +
                "JOIN StudyGroups sg ON s.group_id = sg.group_id " +
                "WHERE s.family_name = ? " +
                "GROUP BY s.student_id, s.name, s.family_name, sg.group_name";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, familyName);

            List<PersonAverageGradeDto> persons = new MyArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String familyNamePerson = resultSet.getString("family_name");
                String name = resultSet.getString("name");
                int groupName = resultSet.getInt("group_name");
                double avgGrade = resultSet.getDouble("avg");

                PersonAverageGradeDto person = new PersonAverageGradeDto(familyNamePerson, name, groupName, avgGrade);
                persons.add(person);
            }

            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
        return new MyArrayList<>();
    }

    /**
     * Метод getAverageGrade извлекает средний балл студентов в указанной группе.
     * Выводит результат на консоль в виде таблицы.
     *
     * @param groupNumber Номер группы, для которой требуется найти средний балл студентов.
     * @return Средняя оценка в классе.
     */
    public static double getAverageGrade(int groupNumber) {
        String sql = "SELECT AVG(g.grade) as average_grade " +
                "FROM StudyGroups sg " +
                "JOIN Curricula c ON sg.group_id = c.group_id " +
                "JOIN Grades g ON c.curricula_id = g.curricula_id " +
                "WHERE sg.group_name = ? " +
                "GROUP BY sg.group_name";

        Connection connection = DatabaseService.connect();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, groupNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("average_grade");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }
}