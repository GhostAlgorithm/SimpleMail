/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.tablahash;

import clases.protocolomail.MensajeEnviarCorreo;

/**
 *
 * @author home
 */
public class ElementoListaCorreos {
    private MensajeEnviarCorreo mensaje;
    private ElementoListaCorreos siguiente = null;
    private String setFecha;
    
    public ElementoListaCorreos(MensajeEnviarCorreo mensaje){
        this.setMensaje(mensaje);
    }
    
    public void setMensaje(MensajeEnviarCorreo mensaje){
        this.mensaje = mensaje;
    }
    
    public MensajeEnviarCorreo getMensaje(){
        return this.mensaje;
    }
    
    public void setSiguiente(ElementoListaCorreos siguiente){
        this.siguiente = siguiente;
    }
    
    public ElementoListaCorreos getSiguiente(){
        return this.siguiente;
    }
}
