/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import servidormail.formularios.frmPantallaPrincipal;

/**
 *
 * @author home
 */
public class SocketServer extends Thread{
    private ServerSocket server;
    private boolean continuar = true;
    private ArrayList<ConexionCliente> clientes;
    private frmPantallaPrincipal padre;
    
    public SocketServer(frmPantallaPrincipal padre){
        this.clientes = new ArrayList<ConexionCliente>();
        this.padre = padre;
    }
    
    public void run(){
        try {
            this.server = new ServerSocket(9873);
            while (this.continuar){
                Socket cliente = this.server.accept();
                ConexionCliente con = new ConexionCliente(this.padre, cliente);
                con.start();
                
                // agregando conexion al arraylist
                this.agregarCliente(con);
                this.padre.agregarAccionThread("Un nuevo usuario ha iniciado conexion!\n");
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
