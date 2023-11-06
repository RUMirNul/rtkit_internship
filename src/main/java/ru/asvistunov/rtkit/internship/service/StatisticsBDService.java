package ru.asvistunov.rtkit.internship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsBDService {
    public static void getTopStudentsAbove14() {
        Connection connection = DatabaseService.connect();

        String sql = "SELECT s.name, s.family_name, sg.group_name, AVG(g.grade) as average_grade " +
                "FROM Students s " +
                "JOIN Grades g ON s.student_id = g.student_id " +
                "JOIN StudyGroups sg ON s.group_id = sg.group_id " +
                "WHERE s.age > 14 " +
                "GROUP BY s.name, s.family_name, sg.group_name " +
                "HAVING AVG(g.grade) = 5.0";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String familyName = resultSet.getString("family_name");
                String groupName = resultSet.getString("group_name");
                double avgGrade = resultSet.getDouble("average_grade");

                String studentInfo = String.format("%15s %12s %3s: %.2f", familyName, name, groupName, avgGrade);
                System.out.println(studentInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

    public static void getStudentAverageByFamilyName(String familyName) {
        Connection connection = DatabaseService.connect();

        String sql = "SELECT s.name, s.family_name, sg.group_name, AVG(g.grade) " +
                "FROM Students s " +
                "JOIN Grades g ON s.student_id = g.student_id " +
                "JOIN StudyGroups sg ON s.group_id = sg.group_id " +
                "WHERE s.family_name = ? " +
                "GROUP BY s.name, s.family_name, sg.group_name";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, familyName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String groupName = resultSet.getString("group_name");
                String familyNamePerson = resultSet.getString("family_name");
                double avgGrade = resultSet.getDouble("avg");

                String studentInfo = String.format("%15s %12s %3s %.2f", familyNamePerson, name, groupName, avgGrade);
                System.out.println(studentInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

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
            while (resultSet.next()) {
                String groupName = resultSet.getString("group_name");
                double avgGrade = resultSet.getDouble("average_grade");

                String groupInfo = String.format("%3s  %.2f", groupName, avgGrade);
                System.out.println(groupInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}