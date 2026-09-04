
package main.java.edu.ingsoft.colegio.gotitas.util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import main.java.edu.ingsoft.colegio.gotitas.controller.DashboardController;
import main.java.edu.ingsoft.colegio.gotitas.controller.LoginController;
import main.java.edu.ingsoft.colegio.gotitas.repository.AuthRepository;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.service.DashBoardService;


public class SceneManager {
    //Atributos
    private Stage primaryStage;
    private final String FXMLPATH = "/main/resources/view/";
    
    public SceneManager(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    //Metodo
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource( FXMLPATH + "login-view.fxml"));
        loader.setControllerFactory(
        clazz -> {
            if(clazz == LoginController.class){
                AuthRepository authRepository = new AuthRepository();
                AuthService authService = new AuthService(authRepository);
                return new LoginController(authService, this); 
            }
            try{
                return clazz.getDeclaredConstructor().newInstance();
            } catch(Exception e){
                throw new RuntimeException("Error al crear el constuctor" + e.getMessage());
            }
        }
        );
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();       
    }    
    
    public void showDashBoardView() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPATH + "dashboard-view.fxml"));
        loader.setControllerFactory(clazz -> {
                    if (clazz == DashboardController.class){
                        EstudianteRepository dashboardRepository = new EstudianteRepository();
                        DashBoardService dashboardService = new DashBoardService(dashboardRepository);
                        return new DashboardController(dashboardService, this);
                    }
                    try{
                        return clazz.getDeclaredConstructor().newInstance();
                    }catch (Exception e){
                        throw new RuntimeException("Error al crear el constructor" + e.getMessage());
                    }
                }
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 820, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FXMLPATH);
        primaryStage.centerOnScreen();
        primaryStage.show();
        
        
    }
    
    
    
    public void showInfoAlert(String head, String title, String content, AlertType type){
        Alert alert = new Alert(type);
        alert.initOwner(this.primaryStage);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(head);
        alert.showAndWait();
        
    }
    
    
}
