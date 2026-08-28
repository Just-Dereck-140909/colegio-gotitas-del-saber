/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.edu.ingsoft.colegio.gotitas.service;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;

/**
 *
 * @author informatica
 */
public class DashBoardService {
    private EstudianteRepository estudianteRepository;
    
    
    public DashBoardService(EstudianteRepository dashboardRepository){
        this.estudianteRepository = dashboardRepository;
    }
    
    
    public ObservableList<Estudiante> listStudent() throws Exception{
        if(estudianteRepository.findAll() == null){
            throw new RuntimeException("Sin datos que mostrar");
        }else{
            return estudianteRepository.findAll();
        }
    }
}
