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
}
