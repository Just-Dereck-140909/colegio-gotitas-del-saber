package main.java.edu.ingsoft.colegio.gotitas.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;


public class LoginController implements Initializable {
    //Atributos
    private final AuthService authService;
    private final SceneManager sceneManager;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private TextField txtFieldPass;

    
    
    public LoginController(AuthService authService, SceneManager sceneManager){
        this.authService = authService;
        this.sceneManager = sceneManager;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void handleLogin() throws Exception{
        if (txtFieldEmail.getText().isEmpty() || txtFieldPass.getText().isEmpty()){
            sceneManager.showInfoAlert("Campos faltantes", "Revisar Informacion", "Uno o mas campos vacios", AlertType.CONFIRMATION);
        }else{
            try{
            LoginResponse responseService = authService.login(new LoginRequest(txtFieldEmail.getText(), txtFieldPass.getText()));
            LoginResponse userLogged = new LoginResponse(responseService.getNombre(), responseService.getApellido());
            sceneManager.showInfoAlert("Bienvenido a Gotitas del saber", "Inicio exitoso", "Bienvenido : " + userLogged.getNombre(), AlertType.INFORMATION);
            }catch(RuntimeException e){
                sceneManager.showInfoAlert("Datos incorrectos", "Revisa tu informacion", "Intenta de nuevo", AlertType.INFORMATION);
            }
        }
    }
    
}
