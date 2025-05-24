package javafxapplication1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    // Cambia estos valores por los de tu base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/tu_basedatos?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            // Carga el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de MySQL:");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una nueva conexión a la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Alias para getConnection(), por compatibilidad con código anterior.
     */
    public static Connection connectdb() throws SQLException {
        return getConnection();
    }
}
