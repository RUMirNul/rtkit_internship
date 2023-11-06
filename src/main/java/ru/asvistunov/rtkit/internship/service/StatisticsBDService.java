package ru.asvistunov.rtkit.internship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс StatisticsBDService предоставляет методы для извлечения статистической информации
 * из базы данных, связанной с учебной деятельностью студентов.
 */
public class StatisticsBDService {

    /**
     * Метод getTopStudentsAbove14 извлекает информацию о студентах старше 14 лет, имеющих средний балл 5.0.
     * Выводит результат на консоль в виде таблицы.
     */
    public static void getTopStudentsAbove14() {
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
            boolean isFirstOutput = true;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");;
                String familyName = resultSet.getString("family_name");
                int groupName = resultSet.getInt("group_name");

                if (isFirstOutput) {
                    System.out.printf("%15s %12s %8s %8s\n", "Фамилия", "Имя", "Возраст", "Группа");
                    isFirstOutput = false;
                }
                String studentInfo = String.format("%15s %12s %8d %8d", familyName, name, age, groupName);
                System.out.println(studentInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

    /**
     * Метод getStudentAverageByFamilyName извлекает средний балл студента по его фамилии.
     * Выводит результат на консоль в виде таблицы.
     *
     * @param familyName Фамилия студента, по которой выполняется поиск.
     */
    public static void getStudentAverageByFamilyName(String familyName) {
        Connection connection = DatabaseService.connect();

        String sql = "SELECT s.name, s.family_name, sg.group_name, AVG(g.grade) " +
                "FROM Students s " +
                "JOIN Grades g ON s.student_id = g.student_id " +
                "JOIN StudyGroups sg ON s.group_id = sg.group_id " +
                "WHERE s.family_name = ? " +
                "GROUP BY s.student_id, s.name, s.family_name, sg.group_name";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, familyName);

            ResultSet resultSet = statement.executeQuery();
            boolean isFirstOutput = true;
            while (resultSet.next()) {
                String familyNamePerson = resultSet.getString("family_name");
                String name = resultSet.getString("name");
                int groupName = resultSet.getInt("group_name");
                double avgGrade = resultSet.getDouble("avg");

                if (isFirstOutput) {
                    System.out.printf("%15s %12s %8s %8s\n", "Фамилия", "Имя", "Группа", "Средняя оценка");
                    isFirstOutput = false;
                }

                String studentInfo = String.format("%15s %12s %7s %8.2f", familyNamePerson, name, groupName, avgGrade);
                System.out.println(studentInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

    /**
     * Метод getAverageGrade извлекает средний балл студентов в указанной группе.
     * Выводит результат на консоль в виде таблицы.
     *
     * @param groupNumber Номер группы, для которой требуется найти средний балл студентов.
     */
    public static void getAverageGrade(int groupNumber) {
        String sql = "SELECT sg.group_name, AVG(g.grade) as average_grade " +
                "FROM StudyGroups sg " +
                "JOIN Curricula c ON sg.group_id = c.group_id " +
                "JOIN Grades g ON c.curricula_id = g.curricula_id " +
                "WHERE sg.group_name = ? " +
                "GROUP BY sg.group_name";

        Connection connection = DatabaseService.connect();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, groupNumber);

            ResultSet resultSet = statement.executeQuery();
            boolean isFirstOutput = true;
            while (resultSet.next()) {
                int groupName = resultSet.getInt("group_name");
                double avgGrade = resultSet.getDouble("average_grade");

                if (isFirstOutput) {
                    System.out.printf("%8s %8s\n", "Группа", "Средняя оценка");
                    isFirstOutput = false;
                }

                String groupInfo = String.format("%8s  %.2f", groupName, avgGrade);
                System.out.println(groupInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}