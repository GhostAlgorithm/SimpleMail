/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.protocolomail;

/**
 *
 * @author home
 */
public class MensajeEnviarCorreo {
    private String remitente;
    private String destinatario;
    private String mensaje;
    private boolean hayAdjunto = false;
    private byte[] datos = new byte[1];
    private byte[] firmaDigital = new byte[1];
    private byte[] llavePublica = new byte[1];
    
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
}
