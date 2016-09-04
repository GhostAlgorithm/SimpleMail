/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.protocolomail;

import java.io.InputStream;
import java.math.BigInteger;

/**
 *
 * @author home
 */
public class ProtocoloMail {
    public final static int INICIO_SESION = 1;
    public final static int SESION_ACEPTADA = 65;
    public final static int SESION_RECHAZADA = 127;
    public final static int BUSCAR_DESTINATARIO = 5;
    public final static int DESTINATARIO_ENCONTRADO = 15;
    public final static int DESTINATARIO_DESCONOCIDO = 17;
    public final static int ENVIAR_CORREO = 3;
    
    public static MensajeInicioSesion procesarInicioSesion(InputStream entrada) throws Exception{
        MensajeInicioSesion msj = new MensajeInicioSesion();
        
        // Paso 1: Leer el primer byte e identificar el tamaño del correo:
        int tamanioEmail = entrada.read();
        
        // Paso 2: leer el email completo
        String email = "";
        for (int i =0; i<tamanioEmail; i++){
            email += (char) entrada.read();
        }
        
        // Paso 3: leer el tamanio del Hash
        int tamanioHash = entrada.read();
        
        // `Paso 4: leer el hash MD5
        String password = "";
        for (int i=0; i<tamanioHash; i++){
            password += (char) entrada.read();
        }
        
        msj.setEmail(email);
        msj.setPassword(password);
        return msj;
    }
    
    public static MensajeComprobarDestinatario procesarCompDestinatario(InputStream entrada) throws Exception{
        MensajeComprobarDestinatario msj = new MensajeComprobarDestinatario();
        
        // Paso 1: el primr byte es para identificar el tamaño del correo
        int tamanioEmail = entrada.read();
        
        // Paso 2: leer el email completo
        String email = "";
        for (int i =0; i<tamanioEmail; i++){
            email += (char) entrada.read();
        }
        
        msj.setEmail(email);
        return msj;
    }
    
    public static MensajeEnviarCorreo procesarEnvioCorreo(InputStream entrada) throws Exception{
        MensajeEnviarCorreo msj = new MensajeEnviarCorreo();
        
        // Paso 1: el primer byte indica el tamaño en caractres del correo del remitente
        int tamanioRemitente = entrada.read();
        
        // Paso 2: leer el email del remitente
        String remitente = "";
        for (int i =0; i<tamanioRemitente; i++){
            remitente += (char) entrada.read();
        }
        msj.setRemitente(remitente);
        
        // Paso 3: el byte represente el tamaño en caracteres del correo del destinatario
        int tamanioDestinatario = entrada.read();
        
        // Paso 4: leemos el email del destinatario
        String destinatario = "";
        for (int i =0; i<tamanioDestinatario; i++){
            destinatario += (char) entrada.read();
        }
        msj.setDestinatario(destinatario);
        
        // Paso 5: el siguiente byte indica el tamaño en bytes del flag tamaño texto
        int lenFlagTamTexto = entrada.read();
        byte[] tamTextoByte = new byte[lenFlagTamTexto];
        
        // Paso 6: leemos los bytes que conforman el flag de tamaño texto
        for (int i = 0; i<lenFlagTamTexto; i++){
            tamTextoByte[i] = (byte) entrada.read();
        }
        
        // Paso 6.1 convertimos los bytes a su equivalente BigInteger
        long tamTexto = new BigInteger(tamTextoByte).longValue();
        
        // Paso 7: Leemos todos los datos que conforman el cuerpo del mensaje
        String mensaje = "";
        for (int i=0; i<tamTexto; i++){
            mensaje += (char) entrada.read();
        }
        msj.setMensaje(mensaje);
        
        // Paso 8: el siguiente flag indica si se ha adjuntado archivo
        int adjunto = entrada.read();
        
        if (adjunto == 1){
            msj.setHayAdjunto(true);
        }
        
        if (adjunto == 1){
            int lenFlagTamDatos = entrada.read();
            byte[] flagTamDatosByte = new byte[lenFlagTamDatos];
            
            for (int i = 0; i<lenFlagTamDatos; i++){
                flagTamDatosByte[i] = (byte) entrada.read();
            }
            
            long tamDatos = new BigInteger(flagTamDatosByte).longValue();
            byte[] datos = new byte[(int) tamDatos];
            for (int i=0; i<tamDatos; i++){
                datos[i] = (byte) entrada.read();
            }
            
            msj.setDatos(datos);
        }
        
        return msj;
    }
}
