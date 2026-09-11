package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.edu.ingsoft.colegio.gotitas.model.Usuario;
import main.java.edu.ingsoft.colegio.gotitas.repository.UsuarioRepository.Opcion;
import main.java.edu.ingsoft.colegio.gotitas.service.UsuarioService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class DashboardUsuariosController implements Initializable {

    private final UsuarioService usuarioService;
    private final SceneManager sceneManager;

    private Usuario usuarioSeleccionado;

    @FXML
    private TableView<Usuario> tvUsuarios;
    @FXML
    private TableColumn<Usuario, String> tvColumnIdUsuario;
    @FXML
    private TableColumn<Usuario, String> tvColumnDocente;
    @FXML
    private TableColumn<Usuario, String> tvColumnEmail;
    @FXML
    private TableColumn<Usuario, String> tvColumnRol;

    @FXML
    private ComboBox<Opcion> cbDocente;
    @FXML
    private ComboBox<Opcion> cbRol;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;

    public DashboardUsuariosController(UsuarioService usuarioService, SceneManager sceneManager) {
        this.usuarioService = usuarioService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvColumnIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        tvColumnDocente.setCellValueFactory(new PropertyValueFactory<>("nombreCompletoDocente"));
        tvColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tvColumnRol.setCellValueFactory(new PropertyValueFactory<>("nombreRol"));

        tvUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                cargarFormulario(nuevo);
            }
        });

        cargarCombos();
        handleLoadTableUsuarios();
    }

    private void cargarCombos() {
        cbDocente.setItems(usuarioService.listDocentes());
        cbRol.setItems(usuarioService.listRoles());
    }

    @FXML
    private void handleLoadTableUsuarios() {
        tvUsuarios.setItems(usuarioService.listUsuarios());
    }

    private void cargarFormulario(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
        txtEmail.setText(usuario.getEmail());
        txtPassword.clear();
        txtPassword.setPromptText("Dejar vacío para no cambiar la contraseña");

        seleccionarOpcionPorId(cbDocente, usuario.getIdDocente());
        seleccionarOpcionPorId(cbRol, String.valueOf(usuario.getIdRol()));
    }

    private void seleccionarOpcionPorId(ComboBox<Opcion> combo, String id) {
        for (Opcion opcion : combo.getItems()) {
            if (opcion.getId().equals(id)) {
                combo.getSelectionModel().select(opcion);
                return;
            }
        }
        combo.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleAgregar() {
        try {
            String idDocente = cbDocente.getValue() != null ? cbDocente.getValue().getId() : null;
            int idRol = cbRol.getValue() != null ? Integer.parseInt(cbRol.getValue().getId()) : -1;

            usuarioService.crearUsuario(idDocente, txtEmail.getText(), txtPassword.getText(), idRol);

            sceneManager.showInfoAlert("Usuario creado", "Éxito", "El usuario se agregó correctamente.", AlertType.INFORMATION);
            handleLoadTableUsuarios();
            handleLimpiarCampos();
        } catch (RuntimeException e) {
            sceneManager.showInfoAlert("No se pudo crear", "Error", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleActualizar() {
        try {
            if (usuarioSeleccionado == null) {
                sceneManager.showInfoAlert("Sin selección", "Atención", "Selecciona un usuario de la tabla primero.", AlertType.WARNING);
                return;
            }
            String idDocente = cbDocente.getValue() != null ? cbDocente.getValue().getId() : null;
            int idRol = cbRol.getValue() != null ? Integer.parseInt(cbRol.getValue().getId()) : -1;

            usuarioService.actualizarUsuario(usuarioSeleccionado.getIdUsuario(), idDocente, txtEmail.getText(), txtPassword.getText(), idRol);

            sceneManager.showInfoAlert("Usuario actualizado", "Éxito", "Los cambios se guardaron correctamente.", AlertType.INFORMATION);
            handleLoadTableUsuarios();
            handleLimpiarCampos();
        } catch (RuntimeException e) {
            sceneManager.showInfoAlert("No se pudo actualizar", "Error", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleEliminar() {
        if (usuarioSeleccionado == null) {
            sceneManager.showInfoAlert("Sin selección", "Atención", "Selecciona un usuario de la tabla primero.", AlertType.WARNING);
            return;
        }
        try {
            usuarioService.eliminarUsuario(usuarioSeleccionado.getIdUsuario());
            sceneManager.showInfoAlert("Usuario eliminado", "Éxito", "El usuario se eliminó correctamente.", AlertType.INFORMATION);
            handleLoadTableUsuarios();
            handleLimpiarCampos();
        } catch (RuntimeException e) {
            sceneManager.showInfoAlert("No se pudo eliminar", "Error", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleLimpiarCampos() {
        usuarioSeleccionado = null;
        tvUsuarios.getSelectionModel().clearSelection();
        txtEmail.clear();
        txtPassword.clear();
        txtPassword.setPromptText("Contraseña");
        cbDocente.getSelectionModel().clearSelection();
        cbRol.getSelectionModel().clearSelection();
    }
}