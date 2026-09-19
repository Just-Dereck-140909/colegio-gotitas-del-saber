package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioRepository {

    public static class Opcion {
        private final String id;
        private final String etiqueta;

        public Opcion(String id, String etiqueta) {
            this.id = id;
            this.etiqueta = etiqueta;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

    public ObservableList<Usuario> findAll() {
        String sql = "select \n" +
                "u.id_usuario,\n" +
                "u.id_docente,\n" +
                "d.nombre,\n" +
                "d.apellido,\n" +
                "u.email,\n" +
                "u.id_rol,\n" +
                "r.nombre_rol\n" +
                "from usuarios as u\n" +
                "left join docentes as d on d.id_docente = u.id_docente\n" +
                "left join roles as r on r.id_rol = u.id_rol\n" +
                "order by d.nombre";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("id_usuario"),
                        rs.getString("id_docente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getInt("id_rol"),
                        rs.getString("nombre_rol")
                ));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar usuarios: " + e.getMessage());
        }
    }

    public ObservableList<Opcion> findAllDocentes() {
        String sql = "select id_docente, nombre, apellido from docentes order by nombre";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            ObservableList<Opcion> docentes = FXCollections.observableArrayList();
            while (rs.next()) {
                String etiqueta = rs.getString("nombre") + " " + rs.getString("apellido");
                docentes.add(new Opcion(rs.getString("id_docente"), etiqueta));
            }
            return docentes;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar docentes: " + e.getMessage());
        }
    }

    public ObservableList<Opcion> findAllRoles() {
        String sql = "select id_rol, nombre_rol from roles order by id_rol";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            ObservableList<Opcion> roles = FXCollections.observableArrayList();
            while (rs.next()) {
                roles.add(new Opcion(String.valueOf(rs.getInt("id_rol")), rs.getString("nombre_rol")));
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar roles: " + e.getMessage());
        }
    }

    public void insert(String idDocente, String email, String passwordPlano, int idRol) {
        String id = UUID.randomUUID().toString();
        String hash = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
        String sql = "insert into usuarios (id_usuario, id_docente, contrasena_hash, id_rol, email) values (?,?,?,?,?)";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, id);
            pstm.setString(2, idDocente);
            pstm.setString(3, hash);
            pstm.setInt(4, idRol);
            pstm.setString(5, email);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario: " + e.getMessage());
        }
    }

    public void update(String idUsuario, String idDocente, String email, String passwordPlano, int idRol) {
        boolean cambiarPassword = passwordPlano != null && !passwordPlano.isBlank();

        String sql = cambiarPassword
                ? "update usuarios set id_docente=?, email=?, id_rol=?, contrasena_hash=? where id_usuario=?"
                : "update usuarios set id_docente=?, email=?, id_rol=? where id_usuario=?";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idDocente);
            pstm.setString(2, email);
            pstm.setInt(3, idRol);

            if (cambiarPassword) {
                String hash = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
                pstm.setString(4, hash);
                pstm.setString(5, idUsuario);
            } else {
                pstm.setString(4, idUsuario);
            }
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void delete(String idUsuario) {
        String sql = "delete from usuarios where id_usuario = ?";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idUsuario);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage());
        }
    }
}