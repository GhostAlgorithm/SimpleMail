/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.tablahash.ElementoListaUsuarios;
import clases.protocolomail.MensajeComprobarDestinatario;
import clases.protocolomail.MensajeEnviarCorreo;
import clases.protocolomail.MensajeInicioSesion;
import clases.protocolomail.ProtocoloMail;
import clases.seguridad.IntegridadInformacion;
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
    
    public boolean chequearLogin(MensajeInicioSesion msj){
        ElementoListaUsuarios usuario = this.padre.tablaUsuarios.buscarUsuario(msj.getEmail());
                
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
    
    public boolean buscarDestinatario(MensajeComprobarDestinatario msj){
        ElementoListaUsuarios usuario = this.padre.tablaUsuarios.buscarUsuario(msj.getEmail());
        
        if (usuario != null){
            return true;
        }
        return false;
    }
    
    public boolean procesarCorreo(MensajeEnviarCorreo msj){
        System.out.println("Remitente: " + msj.getRemitente());
        System.out.println("Destinatario: " + msj.getDestinatario());
        System.out.println("Mensaje: " + msj.getMensaje());
        System.out.println("Tamaño de adjunto: " + msj.getDatos().length);
        System.out.println("Extension de archivo: " + msj.getExtensionArchivo());
        System.out.println("MD5Checksum del archivo: " + msj.getMd5Checksum());
        System.out.println("Tamaño de bytes de firma: " + msj.getFirmaDigital().length);
        System.out.println("Tamaño de bytes de llave: " + msj.getLlavePublica().length);
        
        boolean resultado = false;
        
        if (msj.getHayAdjunto()){
            if (IntegridadInformacion.validezDelArchivo(msj.getDatos(), msj.getMd5Checksum())){
                resultado = true;
                System.out.println("Los checksum son igaules, archivo recibido de manera correcta");
            }
        }
        
        if (resultado){
            this.padre.tablaCorreos.insertarCorreo(msj);
        }
        
        resultado = true;
        return resultado;
    }
    
    public void run(){
        try {
            entrada = cliente.getInputStream();
            salida = cliente.getOutputStream();
            
            while (this.getContinuar()){
                int ID = entrada.read();
                
                switch(ID){
                    case ProtocoloMail.INICIO_SESION:
                        MensajeInicioSesion msj = ProtocoloMail.procesarInicioSesion(entrada);
                        if (chequearLogin(msj)){
                            salida.write(ProtocoloMail.SESION_ACEPTADA);
                        } else {
                            salida.write(ProtocoloMail.SESION_RECHAZADA);
                        }
                        break;
                    case ProtocoloMail.BUSCAR_DESTINATARIO:
                        MensajeComprobarDestinatario msj2 = ProtocoloMail.procesarCompDestinatario(entrada);
                        if (this.buscarDestinatario(msj2)){
                            salida.write(ProtocoloMail.DESTINATARIO_ENCONTRADO);
                        } else {
                            salida.write(ProtocoloMail.DESTINATARIO_DESCONOCIDO);
                        }
                        break;
                    case ProtocoloMail.ENVIAR_CORREO:
                        MensajeEnviarCorreo msj3 = ProtocoloMail.procesarEnvioCorreo(entrada);
                        if (this.procesarCorreo(msj3)){
                            salida.write(ProtocoloMail.ENVIO_EXITOSO);
                        } else {
                            salida.write(ProtocoloMail.ENVIO_FALLIDO);
                        }
                        break;
                    case -1:
                        this.setContinuar(false);
                        this.cliente.close();
                        break;
                    default:
                        System.out.println("Error al leer id, se ha leido id desconocido de: " + ID);
                        break;
                }
            }
        } catch(Exception e){
            System.out.println("Error al leer datos!!!");
            System.out.println("" + e.toString());
            this.setContinuar(false);
        }
    }
}
