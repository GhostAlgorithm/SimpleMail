/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.net.Socket;

/**
 *
 * @author home
 */
public class ConexionCliente extends Thread {
    private Socket cliente;
    private boolean continuar = true;
    
    public ConexionCliente(Socket cliente){
        this.setCliente(cliente);
    }
    
    public void setCliente(Socket cliente){
        this.cliente = cliente;
    }
    
    public void setContinuar(boolean val){
        this.continuar = val;
    }
    
    public boolean getContinuar(){
        return this.continuar;
    }
    
    public void run(){
        while (this.getContinuar()){
            
        }
    }
}
