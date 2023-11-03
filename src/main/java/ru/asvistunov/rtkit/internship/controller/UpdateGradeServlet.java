package ru.asvistunov.rtkit.internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.asvistunov.rtkit.internship.dto.ErrorMessageDto;
import ru.asvistunov.rtkit.internship.dto.PersonGradeUpdateDto;
import ru.asvistunov.rtkit.internship.person.data.Person;
import ru.asvistunov.rtkit.internship.service.StudentService;
import ru.asvistunov.rtkit.internship.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс UpdateGradeServlet - сервлет, обрабатывающий put-запросы для изменения оценки по предмету у определённого
 * человека по его ФИО и группе.
 */
@WebServlet("/students/update_grade")
public class UpdateGradeServlet extends HttpServlet {
    private final StudentService studentService = StudentServiceImpl.getInstance();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        BufferedReader reader = req.getReader();
        PersonGradeUpdateDto personGradeUpdateDto;
        try {
            personGradeUpdateDto = objectMapper.readValue(reader, PersonGradeUpdateDto.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(objectMapper.writeValueAsString(new ErrorMessageDto("Некорректный запрос.")));
            return;
        }
        Person person = null;
        try {
            person = studentService.updatePersonGrade(personGradeUpdateDto);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(objectMapper.writeValueAsString(new ErrorMessageDto(e.getMessage())));
            return;
        }

        try (var output = resp.getWriter()) {
            objectMapper.writeValue(output, person);
            output.flush();
        }
    }
}
