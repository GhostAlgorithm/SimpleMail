/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.protocolomail.MensajeInicioSesion;
import clases.protocolomail.ProtocoloMail;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author home
 */
public class ConexionCliente extends Thread {
    private Socket cliente;
    private boolean continuar = true;
    private InputStream entrada;
    
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
        System.out.println("Ejecutando Thread del cliente");
        try {
            entrada = cliente.getInputStream();
            while (this.getContinuar()){
                byte ID = (byte) entrada.read();
                
                switch(ID){
                    case 1:
                        MensajeInicioSesion msj = ProtocoloMail.procesarInicioSesion(entrada);
                        System.out.println("Mensaje Inicio de sesion: ");
                        System.out.println("Email: " + msj.getEmail());
                        System.out.println("Password: " + msj.getPassword());
                        break;
                    default:
                        System.out.println("Error al leer id, se ha leido id desconocido de: " + ID);
                        break;
                }
            }
        } catch(Exception e){
            System.out.println("Error al leer datos!!!");
        }
    }
}
