package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;

public class RegistroRepository {

    public String findDocenteByEmail(String email) throws Exception {

        String sql = "SELECT id_docente FROM docentes WHERE correo_electronico = ?";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("id_docente");
                }
            }
        }

        return null;
    }

    public void saveUsuario(String idUsuario, String idDocente,
            String contrasenaHash, int idRol, String email) throws Exception {

        String sql = "INSERT INTO usuarios "
                + "(id_usuario, id_docente, contrasena_hash, id_rol, email) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, idUsuario);
            statement.setString(2, idDocente);
            statement.setString(3, contrasenaHash);
            statement.setInt(4, idRol);
            statement.setString(5, email);

            statement.executeUpdate();
        }
    }
    
    public String findLastUserId() throws Exception {

        String sql = "SELECT id_usuario FROM usuarios "
                   + "ORDER BY id_usuario DESC LIMIT 1";
        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("id_usuario");
            }
        }

        return null;
    }
    
}