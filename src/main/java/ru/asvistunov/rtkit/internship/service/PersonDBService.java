package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.PersonDto;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;

import java.sql.*;
import java.util.List;

public class PersonDBService {
    public static Person getPerson(int personId) {
        Person person = new Person();

        try {
            Connection connection = DatabaseService.connect();
            connection.setAutoCommit(false);

            try {
                String sqlPersonInfo = "SELECT s.name, s.family_name, s.age, sg.group_name " +
                        "FROM Students s " +
                        "JOIN StudyGroups sg ON s.group_id = sg.group_id " +
                        "WHERE s.student_id = ?";

                PreparedStatement ps = connection.prepareStatement(sqlPersonInfo);
                ps.setInt(1, personId);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    person.setName(rs.getString("name"));
                    person.setFamilyName(rs.getString("family_name"));
                    person.setAge(rs.getInt("age"));
                    person.setGroup(rs.getInt("group_name"));
                }


                String sqlPersonSubjectAndGrade = "SELECT c.subject_name, g.grade " +
                        "FROM Grades g " +
                        "JOIN Curricula c ON c.curricula_id = g.curricula_id " +
                        "WHERE g.student_id = ?";

                PreparedStatement ps2 = connection.prepareStatement(sqlPersonSubjectAndGrade);
                ps2.setInt(1, personId);

                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String subjectName = rs2.getString("subject_name");
                    int subjectGrade = rs2.getInt("grade");
                    SubjectGrade sg = new SubjectGrade(subjectName, subjectGrade);
                    person.addSubjectGrade(sg);
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Транзакция отменена");
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }


        return person;
    }

    public static void addPerson(Person person) {
        try {

            Connection connection = DatabaseService.connect();
            connection.setAutoCommit(false);

            try {
                int groupId = findOrCreateGroup(connection, person.getGroup());
                int studentId = addStudent(connection, person.getName(), person.getFamilyName(), person.getAge(), groupId);
                addSubjectGrades(connection, studentId, groupId, person.getSubjectGradeList());

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Транзакция отменена");
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

    public static void updatePerson(PersonDto personDto) {
        try {
            Connection connection = DatabaseService.connect();
            connection.setAutoCommit(false);

            try {
                int groupId = findOrCreateGroup(connection, personDto.getGroup());
                int updatedStudentId = updateStudent(connection, personDto.getId(), personDto.getName(),
                        personDto.getFamilyName(), personDto.getAge(), groupId);
                updateSubjectGrades(connection, updatedStudentId, groupId, personDto.getSubjectGradeList());

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Транзакция отменена");
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

    public static void deletePerson(int personId) {
        try {
            Connection connection = DatabaseService.connect();
            connection.setAutoCommit(false);

            String deleteGradesQuery = "DELETE FROM Grades WHERE student_id = ?";
            String deleteStudentQuery = "DELETE FROM Students WHERE student_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGradesQuery)) {
                preparedStatement.setInt(1, personId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Транзакция отменена");
                throw e;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStudentQuery)) {
                preparedStatement.setInt(1, personId);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Транзакция отменена");
                throw e;
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }

    private static int updateStudent(Connection connection, int personId, String name, String familyName, int age, int groupId) throws SQLException {
        String query = "UPDATE Students SET name = ?, family_name = ?, age = ?, group_id = ? WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, familyName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, groupId);
            preparedStatement.setInt(5, personId);
            preparedStatement.executeUpdate();
        }
        return personId;
    }

    private static void updateSubjectGrades(Connection connection, int studentId, int groupId, List<SubjectGrade> subjectGradeList) throws SQLException {
        String deleteGradesQuery = "DELETE FROM Grades WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGradesQuery)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        }

        addSubjectGrades(connection, studentId, groupId, subjectGradeList);
    }

    private static int findOrCreateGroup(Connection connection, int groupName) throws SQLException {
        String query = "SELECT group_id FROM StudyGroups WHERE group_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("group_id");
            } else {
                return addGroup(connection, groupName);
            }
        }
    }

    private static int addGroup(Connection connection, int groupName) throws SQLException {
        String query = "INSERT INTO StudyGroups (group_name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, groupName);
            preparedStatement.executeUpdate();

            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                return key.getInt("group_id");
            } else {
                throw new SQLException("Ошибка при создании новой группы.");
            }
        }
    }

    private static int addStudent(Connection connection, String name, String familyName, int age, int groupId) throws SQLException {
        String query = "INSERT INTO Students (name, family_name, age, group_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, familyName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, groupId);
            preparedStatement.executeUpdate();

            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                return key.getInt("student_id");
            } else {
                throw new SQLException("Ошибка при добавлении нового студента.");
            }
        }
    }

    private static void addSubjectGrades(Connection connection, int studentId, int groupId, List<SubjectGrade> subjectGradeList) throws SQLException {
        String query = "INSERT INTO Grades (student_id, curricula_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (SubjectGrade subjectGrade : subjectGradeList) {
                int curriculaId = findOrCreateCurricula(connection, groupId, subjectGrade.getSubjectName());
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, curriculaId);
                preparedStatement.setInt(3, subjectGrade.getGrade());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private static int findOrCreateCurricula(Connection connection, int groupId, String subjectName) throws SQLException {
        String query = "SELECT curricula_id FROM Curricula WHERE group_id = ? AND subject_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.setString(2, subjectName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("curricula_id");
            } else {
                return addCurricula(connection, groupId, subjectName);
            }
        }
    }

    private static int addCurricula(Connection connection, int groupId, String subjectName) throws SQLException {
        String query = "INSERT INTO Curricula (group_id, subject_name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.setString(2, subjectName);
            preparedStatement.executeUpdate();

            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                return key.getInt("curricula_id");
            } else {
                throw new SQLException("Ошибка при добавлении новой учебной программы.");
            }
        }
    }


}
