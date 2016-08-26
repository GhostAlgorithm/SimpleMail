/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author home
 */
public class SocketServer extends Thread{
    private ServerSocket server;
    private boolean continuar = true;
    
    public SocketServer(){
        
    }
    
    public void run(){
        try {
            this.server = new ServerSocket(9873);
            while (this.continuar){
                Socket cliente = this.server.accept();
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
}
