package main.java.edu.ingsoft.colegio.gotitas.dto.response;

public class RegistroUsuarioResponse {

    private String idUsuario;
    private String email;

    public RegistroUsuarioResponse(String idUsuario, String email) {
        this.idUsuario = idUsuario;
        this.email = email;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}