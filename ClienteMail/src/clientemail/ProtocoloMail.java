/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemail;

import java.math.BigInteger;
import java.security.MessageDigest;

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
    
    public static byte[] crearMsgInicioSesion(String email, String password){
        byte[] mensaje = new byte[1 + 1 + email.length() + 1 + 32];
        
        // Paso 1: Un mensaje de inciio de sesion , siempre tendra el ID de 1
        mensaje[0] = (byte) INICIO_SESION;
        
        // Paso 2: El segundo parametro indica la cantidad de caracteres que posee
        // el email escrito
        mensaje[1] = (byte) email.length();
        
        // Paso 3: todo lo que sigue, son los caracteres que conforman el correo en si
        int posicionArray = 2;
        char[] charEmail = email.toCharArray();
        
        for (int i =0; i<charEmail.length; i++){
            mensaje[posicionArray] = (byte) charEmail[i];
            posicionArray++;
        }
        
        // Paso 4: el siguiente byte, corresponde al tamaño del hash MD5 de la contraseña
        // en este caso, para MD5 siendo siempre 32
        mensaje[posicionArray] = 32;
        posicionArray++;
        
        // Paso 5: los demas bytes, corresponden a los caracteres que conforman el hash MD5
        byte[] md5Hash = new byte[5];
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md5Hash = md.digest(password.getBytes());
        } catch(Exception e){
        }
        char[] md5 = new BigInteger(1, md5Hash).toString(16).toCharArray();
        
        for (int i=0; i<md5.length; i++){
            mensaje[posicionArray] = (byte) md5[i];
            posicionArray++;
        }
        
        return mensaje;
    }
    
    public static byte[] crearMsgCompDestinatario(String email){
        byte[] mensaje = new byte[1 + 1 + email.length()];
        
        // Paso 1: Todo mensaje de compribar si el destinatario existe o no
        // tendra un ID de 5
        mensaje[0] = (byte) BUSCAR_DESTINATARIO;
        
        // Paso 2: el segundo byte indicara el tamaño en caracteres, del correo a buscar
        mensaje[1] = (byte) email.length();
        
        // paso 3: Lo demás del mensaje corresponde a los caracteres del email
        int posicionArray = 2;
        char[] emailChar = email.toCharArray();
        
        for (int i =0; i<emailChar.length; i++){
            mensaje[posicionArray] = (byte) emailChar[i];
            posicionArray++;
        }
        
        return mensaje;
    }
}
