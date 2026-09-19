package main.java.edu.ingsoft.colegio.gotitas.model;

public class Usuario {

    private String idUsuario;
    private String idDocente;
    private String nombreDocente;
    private String apellidoDocente;
    private String email;
    private int idRol;
    private String nombreRol;

    public Usuario(String idUsuario, String idDocente, String nombreDocente, String apellidoDocente,
                    String email, int idRol, String nombreRol) {
        this.idUsuario = idUsuario;
        this.idDocente = idDocente;
        this.nombreDocente = nombreDocente;
        this.apellidoDocente = apellidoDocente;
        this.email = email;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(String idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }

    public String getNombreCompletoDocente() {
        String n = nombreDocente == null ? "" : nombreDocente;
        String a = apellidoDocente == null ? "" : apellidoDocente;
        return (n + " " + a).trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}