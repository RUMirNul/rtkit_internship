package ru.asvistunov.rtkit.internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.asvistunov.rtkit.internship.dto.ErrorMessageDto;
import ru.asvistunov.rtkit.internship.dto.PersonDto;
import ru.asvistunov.rtkit.internship.service.StudentService;
import ru.asvistunov.rtkit.internship.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Класс AverageGradeServlet - сервлет, обрабатывающий GET-запросы для получения средних оценок студентов
 * в указанной группе.
 */
@WebServlet(urlPatterns = {"/students/average_grade/*"})
public class AverageGradeServlet extends HttpServlet {
    private final StudentService studentService = StudentServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        int group;
        try {
            group = Integer.parseInt(req.getPathInfo().substring(1));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(objectMapper.writeValueAsString(
                    new ErrorMessageDto("Номер группы должен быть не пустым и являться целым числом.")));
            return;
        }

        List<PersonDto> personsDto = studentService.getMeanGradeStudentByGroupNumber(group);

        if (personsDto.size() == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(objectMapper.writeValueAsString(new ErrorMessageDto("Группа не найдена.")));
        }

        try (var output = resp.getWriter()) {
            objectMapper.writeValue(output, personsDto);
            output.flush();
        }
    }
}
