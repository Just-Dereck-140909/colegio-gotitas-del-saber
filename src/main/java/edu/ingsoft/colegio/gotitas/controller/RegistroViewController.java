package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegistroUsuarioRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegistroUsuarioResponse;
import main.java.edu.ingsoft.colegio.gotitas.service.RegistroService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class RegistroViewController implements Initializable {

    private final RegistroService registroService;
    private final SceneManager sceneManager;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField txtFieldPass;

    public RegistroViewController(RegistroService registroService, SceneManager sceneManager) {
        this.registroService = registroService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void handleRegistro() {

        try {

            RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                    txtFieldEmail.getText(),
                    txtFieldPass.getText()
            );

            RegistroUsuarioResponse response =
                    registroService.registrarUsuario(request);

            sceneManager.showInfoAlert(
                    "Registro exitoso",
                    "Usuario creado",
                    "Tu usuario ha sido registrado correctamente.\nID: "
                    + response.getIdUsuario(),
                    AlertType.INFORMATION
            );

            txtFieldEmail.clear();
            txtFieldPass.clear();

        } catch (RuntimeException e) {

            sceneManager.showInfoAlert(
                    "Error en el registro",
                    "No se pudo registrar",
                    e.getMessage(),
                    AlertType.ERROR
            );

        } catch (Exception e) {

            sceneManager.showInfoAlert(
                    "Error",
                    "Ocurrió un problema",
                    "No se pudo completar el registro.",
                    AlertType.ERROR
            );
        }
    }
    
    @FXML
    public void handleRegresar() throws Exception {
        sceneManager.showLoginView();
    }
}
