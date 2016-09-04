/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.protocolomail.MensajeInicioSesion;
import clases.protocolomail.ProtocoloMail;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import servidormail.formularios.frmPantallaPrincipal;

/**
 *
 * @author home
 */
public class ConexionCliente extends Thread {
    private Socket cliente;
    private boolean continuar = true;
    private InputStream entrada;
    private OutputStream salida;
    private frmPantallaPrincipal padre;
    
    public ConexionCliente(frmPantallaPrincipal padre, Socket cliente){
        this.padre = padre;
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
    
    public boolean chequearLogin(ElementoListaUsuarios usuario, MensajeInicioSesion msj){
        if (usuario != null){
            if (usuario.getUsuario().getPassword().equals(msj.getPassword())){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public void run(){
        try {
            entrada = cliente.getInputStream();
            salida = cliente.getOutputStream();
            
            while (this.getContinuar()){
                byte ID = (byte) entrada.read();
                
                switch(ID){
                    case 1:
                        MensajeInicioSesion msj = ProtocoloMail.procesarInicioSesion(entrada);
                        ElementoListaUsuarios usuario = this.padre.tablaUsuarios.buscarUsuario(msj.getEmail());
                        if (chequearLogin(usuario, msj)){
                            salida.write(ProtocoloMail.SESION_ACEPTADA);
                        } else {
                            salida.write(ProtocoloMail.SESION_RECHAZADA);
                        }
                        break;
                    default:
                        System.out.println("Error al leer id, se ha leido id desconocido de: " + ID);
                        break;
                }
            }
        } catch(Exception e){
            System.out.println("Error al leer datos!!!");
            System.out.println("" + e.toString());
        }
    }
}
