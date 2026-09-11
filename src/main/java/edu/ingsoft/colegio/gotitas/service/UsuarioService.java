package main.java.edu.ingsoft.colegio.gotitas.service;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.model.Usuario;
import main.java.edu.ingsoft.colegio.gotitas.repository.UsuarioRepository;
import main.java.edu.ingsoft.colegio.gotitas.repository.UsuarioRepository.Opcion;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ObservableList<Usuario> listUsuarios() {
        return usuarioRepository.findAll();
    }

    public ObservableList<Opcion> listDocentes() {
        return usuarioRepository.findAllDocentes();
    }

    public ObservableList<Opcion> listRoles() {
        return usuarioRepository.findAllRoles();
    }

    public void crearUsuario(String idDocente, String email, String password, int idRol) {
        validarCamposComunes(idDocente, email, idRol);
        if (password == null || password.isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria para crear un usuario.");
        }
        usuarioRepository.insert(idDocente, email.trim(), password, idRol);
    }

    public void actualizarUsuario(String idUsuario, String idDocente, String email, String password, int idRol) {
        if (idUsuario == null || idUsuario.isBlank()) {
            throw new RuntimeException("Selecciona un usuario de la tabla para actualizar.");
        }
        validarCamposComunes(idDocente, email, idRol);
        usuarioRepository.update(idUsuario, idDocente, email.trim(), password, idRol);
    }

    public void eliminarUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isBlank()) {
            throw new RuntimeException("Selecciona un usuario de la tabla para eliminar.");
        }
        usuarioRepository.delete(idUsuario);
    }

    private void validarCamposComunes(String idDocente, String email, int idRol) {
        if (idDocente == null || idDocente.isBlank()) {
            throw new RuntimeException("Selecciona un docente.");
        }
        if (email == null || email.isBlank()) {
            throw new RuntimeException("El email es obligatorio.");
        }
        if (idRol <= 0) {
            throw new RuntimeException("Selecciona un rol.");
        }
    }
}