package com.ibs.utill;

import com.ibs.config.Config;
import com.ibs.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
/**
 * @author Mironov Roman
 * Класс для работы с БД.
 */
public class JdbcHelper {

    private static final Logger log = LoggerFactory.getLogger(JdbcHelper.class);

    private static final String URL = Config.getDbUrl();
    private static final String USER = Config.getDbUser();
    private static final String PASSWORD = Config.getDbPassword();
    private JdbcHelper() {
    }

    /**
     * Получает новое соединение с базой данных.
     *
     * @return {@link Connection} объект для работы с базой данных.
     * @throws SQLException если не удалось установить соединение с базой данных.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Проверяет доступность базы данных.
     */
    public static boolean isDatabaseAccessible() {
        try (Connection conn = getConnection()) {
            return true;
        } catch (SQLException e) {
            log.error("Ошибка подключения: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Проверка наличия продукта в базе.
     *
     * @param name Название продукта.
     * @param type Тип продукта.
     * @param exotic Флаг экзотичности.
     */
    public static boolean isFoodPresent(String name, String type, boolean exotic) {
        String query = "SELECT COUNT(*) FROM FOOD fo WHERE fo.FOOD_NAME = ? AND fo.FOOD_TYPE = ? AND fo.FOOD_EXOTIC = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setBoolean(3, exotic);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при проверке продукта в БД", e);
        }
    }

    /**
     * Проверка точных данных продукта.
     *
     *  @param name Название продукта.
     *  @param type Тип продукта.
     *  @param exotic Флаг экзотичности.
     */
    public static boolean verifyFoodDetails(String name, String type, boolean exotic) {
        String query = "SELECT fo.FOOD_NAME, fo.FOOD_TYPE, fo.FOOD_EXOTIC " +
                "FROM FOOD fo WHERE fo.FOOD_NAME = ? AND fo.FOOD_TYPE = ? AND fo.FOOD_EXOTIC = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setBoolean(3, exotic);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("FOOD_NAME").equals(name)
                            && rs.getString("FOOD_TYPE").equals(type)
                            && rs.getBoolean("FOOD_EXOTIC") == exotic;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при проверке деталей продукта в БД", e);
        }
    }

    /**
     * Удаление продукта из базы.
     *
     * @param name Название продукта.
     */
    public static void deleteFoodByName(String name) {
        String query = "DELETE FROM FOOD fo WHERE fo.FOOD_NAME = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                log.info("Product deleted: name={}", name);
            } else {
                log.warn("No product found to delete: name={}", name);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при удалении продукта из БД", e);
        }
    }
}
