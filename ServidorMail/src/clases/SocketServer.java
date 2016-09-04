/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author home
 */
public class SocketServer extends Thread{
    private ServerSocket server;
    private boolean continuar = true;
    private ArrayList<ConexionCliente> clientes;
    
    public SocketServer(){
        this.clientes = new ArrayList<ConexionCliente>();
    }
    
    public void run(){
        try {
            this.server = new ServerSocket(9873);
            while (this.continuar){
                Socket cliente = this.server.accept();
                ConexionCliente con = new ConexionCliente(cliente);
                con.start();
                
                // agregando conexion al arraylist
                this.agregarCliente(con);
            }
        } catch(Exception e){
        }
    }
    
    public void setContinuar(boolean val){
        this.continuar = false;
        try {
            this.server.close();
        } catch(Exception e){
            
        }
    }
    
    public void agregarCliente(ConexionCliente cliente){
        this.clientes.add(cliente);
    }
}
