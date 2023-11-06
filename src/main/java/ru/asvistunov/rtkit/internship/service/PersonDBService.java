package ru.asvistunov.rtkit.internship.service;

import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.person.data.PersonDto;
import ru.asvistunov.rtkit.internship.person.data.SubjectGrade;

import java.sql.*;
import java.util.List;

/**
 * Класс PersonDBService предоставляет методы для взаимодействия с базой данных, связанными с сущностью Person.
 */
public class PersonDBService {

    /**
     * Метод getPerson(int personId) возвращает информацию о студенте по его идентификатору.
     *
     * @param personId Идентификатор студента.
     * @return Объект Person с информацией о студенте.
     */
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

    /**
     * Метод addPerson(Person person) добавляет информацию о студенте в базу данных.
     *
     * @param person Объект Person с информацией о студенте.
     */
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

    /**
     * Метод updatePerson(PersonDto personDto) обновляет информацию о студенте в базе данных.
     *
     * @param personDto Объект PersonDto с новой информацией о студенте.
     */
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

    /**
     * Метод deletePerson(int personId) удаляет информацию о студенте и его оценках из базы данных.
     *
     * @param personId Идентификатор студента для удаления.
     */
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

    // Дополнительные методы для внутреннего использования:

    /**
     * Метод updateStudent обновляет информацию о студенте в базе данных.
     *
     * @param connection Соединение с базой данных.
     * @param personId   Идентификатор студента.
     * @param name       Имя студента.
     * @param familyName Фамилия студента.
     * @param age        Возраст студента.
     * @param groupId    Идентификатор группы, к которой принадлежит студент.
     * @return Идентификатор обновленного студента.
     * @throws SQLException В случае ошибки SQL.
     */
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

    /**
     * Метод updateSubjectGrades обновляет оценки студента в базе данных.
     *
     * @param connection       Соединение с базой данных.
     * @param studentId        Идентификатор студента.
     * @param groupId          Идентификатор группы, к которой принадлежит студент.
     * @param subjectGradeList Список объектов SubjectGrade с оценками.
     * @throws SQLException В случае ошибки SQL.
     */
    private static void updateSubjectGrades(Connection connection, int studentId, int groupId, List<SubjectGrade> subjectGradeList) throws SQLException {
        String deleteGradesQuery = "DELETE FROM Grades WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGradesQuery)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        }

        addSubjectGrades(connection, studentId, groupId, subjectGradeList);
    }

    /**
     * Метод findOrCreateGroup находит существующую или создает новую группу в базе данных.
     *
     * @param connection Соединение с базой данных.
     * @param groupName  Имя группы.
     * @return Идентификатор группы.
     * @throws SQLException В случае ошибки SQL.
     */
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

    /**
     * Метод addGroup добавляет новую учебную группу в базу данных.
     *
     * @param connection Соединение с базой данных.
     * @param groupName  Название группы.
     * @return Идентификатор новой группы.
     * @throws SQLException Если произошла ошибка при выполнении SQL-запроса.
     */
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

    /**
     * Метод addStudent добавляет нового студента в базу данных.
     *
     * @param connection Соединение с базой данных.
     * @param name       Имя студента.
     * @param familyName Фамилия студента.
     * @param age        Возраст студента.
     * @param groupId    Идентификатор группы, к которой принадлежит студент.
     * @return Идентификатор нового студента.
     * @throws SQLException Если произошла ошибка при выполнении SQL-запроса.
     */
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

    /**
     * Метод addSubjectGrades добавляет оценки по предметам студента в базу данных.
     *
     * @param connection       Соединение с базой данных.
     * @param studentId        Идентификатор студента.
     * @param groupId          Идентификатор группы, к которой принадлежит студент.
     * @param subjectGradeList Список оценок по предметам.
     * @throws SQLException Если произошла ошибка при выполнении SQL-запросов.
     */
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

    /**
     * Метод addCurricula добавляет новую учебную программу в базу данных.
     *
     * @param connection  Соединение с базой данных.
     * @param groupId     Идентификатор группы, к которой относится учебная программа.
     * @param subjectName Название предмета.
     * @return Идентификатор новой учебной программы (curricula_id).
     * @throws SQLException Если произошла ошибка при выполнении SQL-запросов.
     */
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
