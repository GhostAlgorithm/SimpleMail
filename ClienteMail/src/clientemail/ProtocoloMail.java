/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemail;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.MessageDigest;
import utilidades.ListaByte;

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
        //byte[] mensaje = new byte[1 + 1 + email.length() + 1 + 32];
        ListaByte mensaje = new ListaByte();
        
        // Paso 1: Un mensaje de inciio de sesion , siempre tendra el ID de 1
        mensaje.agregarByte((byte) INICIO_SESION);
        
        // Paso 2: El segundo parametro indica la cantidad de caracteres que posee
        // el email escrito
        mensaje.agregarByte((byte) email.length());
        
        // Paso 3: todo lo que sigue, son los caracteres que conforman el correo en si
        mensaje.agregarString(email);
        
        // Paso 4: el siguiente byte, corresponde al tamaño del hash MD5 de la contraseña
        // en este caso, para MD5 siendo siempre 32
        mensaje.agregarByte((byte) 32);
        
        // Paso 5: los demas bytes, corresponden a los caracteres que conforman el hash MD5
        byte[] md5Hash = new byte[5];
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md5Hash = md.digest(password.getBytes());
        } catch(Exception e){
        }
        String md5 = new BigInteger(1, md5Hash).toString(16);
        mensaje.agregarString(md5);
        
        return mensaje.getBytes();
    }
    
    public static byte[] crearMsgCompDestinatario(String email){
        //byte[] mensaje = new byte[1 + 1 + email.length()];
        ListaByte mensaje = new ListaByte();
        
        // Paso 1: Todo mensaje de compribar si el destinatario existe o no
        // tendra un ID de 5
        mensaje.agregarByte((byte) BUSCAR_DESTINATARIO);
        
        // Paso 2: el segundo byte indicara el tamaño en caracteres, del correo a buscar
        mensaje.agregarByte((byte) email.length());
        
        // paso 3: Lo demás del mensaje corresponde a los caracteres del email
        mensaje.agregarString(email);
        
        return mensaje.getBytes();
    }
    
    public static byte[] crearMsgEnvioCorreo(String remitente, String destinatario, String texto
                         , byte[] archivoData, String rutaLlavePrivada, String rutaLlavePublica){
        
        ListaByte bytes = new ListaByte();
        ListaByte datosAFirmar = new ListaByte();
        
        // Paso 1: todo mensaje de envio de correo tendra el ID de 3
        bytes.agregarByte((byte) ENVIAR_CORREO);
        
        // Paso 2: El siguiente byte representa la cantidad de caracteres que posee
        // el correo del remitente
        bytes.agregarByte((byte) remitente.length());
        
        // Paso 3: lo demas corresponde a los caracteres que conforman el correo
        bytes.agregarString(remitente);
        datosAFirmar.agregarString(remitente);
        
        // Paso 4: el siguiente byte representa la cantidad de caracteres que posee el
        // correo del destinatario
        bytes.agregarByte((byte) destinatario.length());
        
        // Paso 5: lo demas corresponde a los caracteres que conforman el correo
        bytes.agregarString(destinatario);
        datosAFirmar.agregarString(destinatario);
        
        // Paso 6: el siguiente byte indica el tamaño en bytes que posee el numero entero
        // que representa el tamaño del texto
        byte[] flagTamTexto = BigInteger.valueOf(texto.length()).toByteArray();
        bytes.agregarByte((byte) flagTamTexto.length);
        
        // Paso 7: lo demas son los bytes que conforman el flag que determina el tamaño
        // del texto
        bytes.agregarArrayByte(flagTamTexto);
        
        // Paso 8: lo demas son los bytes del texto en si mismo
        bytes.agregarString(texto);
        datosAFirmar.agregarString(texto);
        
        // Paso 9: El siguiente byte es un flag, que indica si se ha adjuntado un
        // archivo o no
        if (archivoData != null){
            bytes.agregarByte((byte) 1);
        } else {
            bytes.agregarByte((byte) 0);
        }
        
        // Paso 10: el siguiente byte indica el tamaño en bytes del numero que indica
        // la cantidad de bytes que conforman el archivo
        if (archivoData != null){
            byte[] flagTamDatos = BigInteger.valueOf(archivoData.length).toByteArray();
            bytes.agregarByte((byte) flagTamDatos.length);
            
            // Paso 11: los demas bytes corresponden a los bytes que representan el tamaño en
            // bytes del archivo enviado
            bytes.agregarArrayByte(flagTamDatos);
            
            // Paso 12: los demas bytes corresponden a los bytes que conforman el archivo
            bytes.agregarArrayByte(archivoData);
            datosAFirmar.agregarArrayByte(archivoData);
        }
        
        // Paso 13: Teniendo toda la informacion necesaria, crear firma digital:
        byte[] data = datosAFirmar.getBytes();
        KeyPair llaves = FirmaDigital.cargarLlaves(rutaLlavePrivada, rutaLlavePublica);
        byte[] firmaDigital = FirmaDigital.firmar(llaves, data);
        
        // Paso 14: El siguiente byte representa el tamaño en bytes que posee la firma digital
        bytes.agregarByte((byte) firmaDigital.length);
        
        // Paso 15: los demas bytes corresponden a los bytes de la firma digital
        bytes.agregarArrayByte(firmaDigital);
        
        // Paso 16: obtener los bytes de la llave publica
        byte[] llavePublica = FirmaDigital.cargarLlavePublica(rutaLlavePublica);
        
        // Paso 17: el siguiente byte representa el tamaño en bytes, de la llave publica
        bytes.agregarByte((byte) llavePublica.length);
        
        // Paso 18: los demas bytes son los bytes de la llave publica
        bytes.agregarArrayByte(llavePublica);
        
        System.out.println("El tamaño del mensaje es: " + bytes.getBytes().length);
        return bytes.getBytes();
    }
}
