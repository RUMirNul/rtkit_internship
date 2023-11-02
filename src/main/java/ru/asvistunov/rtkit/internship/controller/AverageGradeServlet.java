package ru.asvistunov.rtkit.internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.asvistunov.rtkit.internship.service.StudentService;
import ru.asvistunov.rtkit.internship.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/students/average_grade/*"})
public class AverageGradeServlet extends HttpServlet {
    private final StudentService studentService = StudentServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getPathInfo());
        int group = Integer.parseInt(req.getPathInfo().substring(1));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try (var output = resp.getWriter()) {
            resp.setContentType("application/json; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            //System.out.println(studentService.getMeanGradeStudentByGroupNumber(group));
            //System.out.println(new ObjectMapper().writeValueAsString(studentService.getMeanGradeStudentByGroupNumber(group)));
            objectMapper.writeValue(output, studentService.getMeanGradeStudentByGroupNumber(group)); //studentService.getMeanGradeStudentByGroupNumber(group)
            //output.write(new ObjectMapper().writeValueAsString());
            output.flush();
        }
    }

    @Override
    public void init() throws ServletException {
    }
}
