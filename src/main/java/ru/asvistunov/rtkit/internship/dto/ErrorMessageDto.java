package ru.asvistunov.rtkit.internship.dto;

/**
 * Класс ErrorMessageDto представляет объект, содержащий сообщение об ошибке в виде строки.
 */
public class ErrorMessageDto {
    private String errorMessage;

    /**
     * Конструктор класса, инициализирующий объект сообщением об ошибке.
     *
     * @param errorMessage Строка с сообщением об ошибке.
     */
    public ErrorMessageDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
