/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.protocolomail;

import java.io.Serializable;
import utilidades.ListaByte;

/**
 *
 * @author home
 */
public class MensajeEnviarCorreo implements Serializable{
    private String remitente;
    private String destinatario;
    private String mensaje;
    private String extensionArchivo;
    private String md5Checksum;
    private boolean hayAdjunto = false;
    private byte[] datos = new byte[1];
    private byte[] firmaDigital = new byte[1];
    private byte[] llavePublica = new byte[1];
    private ListaByte datosOriginales = new ListaByte();
    
    public MensajeEnviarCorreo(){
    }
    
    public void setRemitente(String remitente){
        this.remitente = remitente;
    }
    
    public String getRemitente(){
        return this.remitente;
    }
    
    public void setDestinatario(String destinatario){
        this.destinatario = destinatario;
    }
    
    public String getDestinatario(){
        return this.destinatario;
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }
    
    public String getMensaje(){
        return this.mensaje;
    }
    
    public void setExtensionArchivo(String extension){
        this.extensionArchivo = extension;
    }
    
    public String getExtensionArchivo(){
        return this.extensionArchivo;
    }
    
    public void setMd5Checksum(String md5){
        this.md5Checksum = md5;
    }
    
    public String getMd5Checksum(){
        return this.md5Checksum;
    }
    
    public void setHayAdjunto(boolean val){
        this.hayAdjunto = val;
    }
    
    public boolean getHayAdjunto(){
        return this.hayAdjunto;
    }
    
    public void setDatos(byte[] datos){
        this.datos = datos;
    }
    
    public byte[] getDatos(){
        return this.datos;
    }
    
    public void setFirmaDigital(byte[] firma){
        this.firmaDigital = firma;
    }
    
    public byte[] getFirmaDigital(){
        return this.firmaDigital;
    }
    
    public void setLlavePublica(byte[] llave){
        this.llavePublica = llave;
    }
    
    public byte[] getLlavePublica(){
        return this.llavePublica;
    }
    
    public byte[] getDatosFirmados(){
        ListaByte datosFirmados = new ListaByte();
        datosFirmados.agregarString(remitente);
        datosFirmados.agregarString(destinatario);
        datosFirmados.agregarString(mensaje);
        datosFirmados.agregarArrayByte(datos);
        datosFirmados.agregarString(extensionArchivo);
        return datosFirmados.getBytes();
    }
    
    public ListaByte getDatosOriginales(){
        return this.datosOriginales;
    }
    
    public void setDatosOriginales(ListaByte datos){
        this.datosOriginales = datos;
    }
}
