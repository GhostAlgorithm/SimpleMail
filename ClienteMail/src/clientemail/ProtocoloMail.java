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
    public final static int ENVIAR_CORREO = 3;
    
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
    
    public static byte[] crearMsgEnvioCorreo(String remitente, String destinatario, String texto
                         , byte[] archivoData){
        
        byte[] tamanioTexto = BigInteger.valueOf(texto.length()).toByteArray();
        int lenArchivoData = 0;
        byte[] tamanioArchivoData = null;
        int tamanioData = 0;
        
        if (archivoData != null){
            tamanioArchivoData =  BigInteger.valueOf(archivoData.length).toByteArray();
            // El +1 ya que uno de los bytes representa el flag de su propio tamaño en bytes
            lenArchivoData = tamanioArchivoData.length + 1;
            tamanioData = archivoData.length;
        }
        
        byte[] mensaje = new byte[1 + 1 + remitente.length() + 1 + destinatario.length()
                                + 1 + tamanioTexto.length + texto.length() + 1 + lenArchivoData
                                + tamanioData];
        
        // Paso 1: Todo mensaje de envio de correo tendra el ID de 3
        mensaje[0] = ENVIAR_CORREO;
        
        // Paso 2: El siguiente byte representa la cantidad de caracteres que posee
        // el correo del remitente
        mensaje[1] = (byte) remitente.length();
        
        // Paso 3: lo demas corresponde a los caracteres que conforman el correo
        int posicionArray = 2;
        char[] remitenteChar = remitente.toCharArray();
        byte[] remitenteByte = new byte[remitenteChar.length];
        
        for (int i=0; i<remitenteChar.length; i++){
            remitenteByte[i] = (byte) remitenteChar[i];
            mensaje[posicionArray] = remitenteByte[i];
            posicionArray++;
        }
        
        // Paso 4: el siguiente byte representa la cantidad de caracteres que posee
        // el correo del destinatario
       mensaje[posicionArray] = (byte) destinatario.length();
       
       // Paso 5: lo demas corresponde a los caracteres que confoman el correo
       posicionArray++;
       char[] destinatarioChar = destinatario.toCharArray();
       byte[] destinatarioByte = new byte[destinatarioChar.length];
       
       for (int i=0; i<destinatarioChar.length; i++){
           destinatarioByte[i] = (byte) destinatarioChar[i];
           mensaje[posicionArray] = destinatarioByte[i];
           posicionArray++;
       }
       
       // Paso 6: el siguiente byte indica el tamaño en bytes que posee el numero entero
       // que representa el tamaño del texto
       mensaje[posicionArray] = (byte) tamanioTexto.length;
       posicionArray++;
       
       // Paso 7: lo demas son los bytes en si que conforman el numero entero
       for (int i=0; i<tamanioTexto.length; i++){
           mensaje[posicionArray] = tamanioTexto[i];
           posicionArray++;
       }
       
       // Paso 8: Los demas bytes representan el texto en si del mensaje
       char[] textoChar = texto.toCharArray();
       byte[] textoByte = new byte[textoChar.length];
       
       for (int i=0; i<textoChar.length; i++){
           textoByte[i] = (byte) textoChar[i];
           mensaje[posicionArray] = textoByte[i];
           posicionArray++;
       }
       
       // Paso 9: El siguiente byte es un flag, que indica si se ha adjuntado un
       // archivo o no
       if (archivoData != null){
           mensaje[posicionArray] = 1;
       } else {
           mensaje[posicionArray] = 0;
       }
       posicionArray++;
       
       // Paso 10: el siguiente byte indica el tamaño en bytes del numero que indica
       // la cantidad de bytes que conforman el archivo
       if (archivoData != null){
           mensaje[posicionArray] = (byte) tamanioArchivoData.length;
           posicionArray++;
           
           // Paso 11: los demas bytes corresponden a los bytes que representan el tamaño en
           // bytes del archivo enviado
           for (int i=0; i<tamanioArchivoData.length; i++){
               mensaje[posicionArray] = tamanioArchivoData[i];
               posicionArray++;
           }
           
           // Paso 12: los demas bytes corresponden a los bytes que conforman el archivo
           for (int i=0; i<archivoData.length; i++){
               mensaje[posicionArray] = archivoData[i];
               posicionArray++;
           }
       }
        
       return mensaje;
    }
}
