package com.ibs.exception;

/**
 * @author Mironov Roman
 * Исключение, которое выбрасывается при ошибках работы с базой данных.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Создает новое исключение с сообщением и причиной.
     *
     * @param message Сообщение об ошибке.
     * @param cause Причина возникновения исключения.
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
