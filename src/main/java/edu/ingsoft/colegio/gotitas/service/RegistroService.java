package main.java.edu.ingsoft.colegio.gotitas.service;

import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegistroUsuarioRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegistroUsuarioResponse;
import main.java.edu.ingsoft.colegio.gotitas.repository.RegistroRepository;
import org.mindrot.jbcrypt.BCrypt;

public class RegistroService {

    private final RegistroRepository registroRepository;

    public RegistroService(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public RegistroUsuarioResponse registrarUsuario(
            RegistroUsuarioRequest request) throws Exception {

        if (request == null) {
            throw new RuntimeException("Los datos de registro están vacíos.");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("El correo y la contraseña son obligatorios.");
        }

        String idDocente = registroRepository.findDocenteByEmail(request.getEmail());

        if (idDocente == null) {
            throw new RuntimeException("No existe un docente con ese correo.");
        }

        String ultimoId = registroRepository.findLastUserId();
        String idUsuario = generarIdUsuario(ultimoId);

        String contrasenaHash = BCrypt.hashpw(
                request.getPassword(),
                BCrypt.gensalt()
        );

        int idRol = 2;

        registroRepository.saveUsuario(
                idUsuario,
                idDocente,
                contrasenaHash,
                idRol,
                request.getEmail()
        );

        return new RegistroUsuarioResponse(
                idUsuario,
                request.getEmail()
        );
    }

    private String generarIdUsuario(String ultimoId) {

        if (ultimoId == null) {
            return "US001";}

        int numero = Integer.parseInt(ultimoId.substring(2));
        numero++;

        return String.format("US%03d", numero);
    }
}