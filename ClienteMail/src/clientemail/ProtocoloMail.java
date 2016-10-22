/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemail;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.MessageDigest;
import utilidades.LectorEntrada;
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
    public final static int ENVIO_EXITOSO = 6;
    public final static int ENVIO_FALLIDO = 7;
    public final static int BANDEJA_ENTRADA = 18;
    public final static int BANDEJA_VACIA = 19;
    public final static int CORREO = 20;
    public final static int FIN_BANDEJA = 21;
    
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
        
        // Paso 5: los demas bytes, corresponden a los caracteres que conforman el hash MD5
        byte[] md5Hash = new byte[5];
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md5Hash = md.digest(password.getBytes());
        } catch(Exception e){
            System.out.println("Error al procesar hash: " + e.toString());
        }
        String md5 = new BigInteger(1, md5Hash).toString(16);
        
        // Paso 4: el siguiente byte, corresponde al tamaño del hash MD5 de la contraseña
        // en este caso, para MD5 siendo siempre 32
        mensaje.agregarByte((byte) md5.length());
        mensaje.agregarString(md5);
        
        return mensaje.getBytes();
    }
    
    public static byte[] crearMsgBandejaEntrada(String email){
        ListaByte mensaje = new ListaByte();
        mensaje.agregarByte((byte) BANDEJA_ENTRADA);
        mensaje.agregarByte((byte) email.length());
        mensaje.agregarString(email);
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
                         , byte[] archivoData, String rutaLlavePrivada, String rutaLlavePublica
                         , String rutaArchivo){
        
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
            
            // Paso 12.1: el siguiente byte representa el tamaño en caracteres
            // de la extension del archivo enviado
            String[] ruta = rutaArchivo.split("\\.");
            String extension = "." + ruta[ruta.length - 1];
            bytes.agregarByte((byte) extension.length());
            
            // Paso 12.3 los demas bytes son la extension del archivo
            bytes.agregarString(extension);
            datosAFirmar.agregarString(extension);
            
            // Paso 12.4: Generar md5Checksum del archivo a enviar y agregarlo al mensaje
            String md5Checksum = IntegridadInformacion.generarMD5Checksum(archivoData);
            bytes.agregarByte((byte) 32);
            bytes.agregarString(md5Checksum);
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
    
    public static MensajeEnviarCorreo procesarEnvioCorreo(InputStream entrada) throws Exception{
        LectorEntrada.datosLeidos = new ListaByte();
        
        MensajeEnviarCorreo msj = new MensajeEnviarCorreo();
        
        // Paso 1: el primer byte indica el tamaño en caractres del correo del remitente
        int tamanioRemitente = LectorEntrada.leerByte(entrada);
        msj.setRemitente(LectorEntrada.leerString(entrada, tamanioRemitente));
        
        
        // Paso 2: el byte represente el tamaño en caracteres del correo del destinatario
        int tamanioDestinatario = LectorEntrada.leerByte(entrada);
        msj.setDestinatario(LectorEntrada.leerString(entrada, tamanioDestinatario));
        
        // Paso 3: el siguiente byte indica el tamaño en bytes del flag tamaño texto
        int lenFlagTamTexto = LectorEntrada.leerByte(entrada);
        long tamTexto = LectorEntrada.leerBigInteger(entrada, lenFlagTamTexto);
        
        // Paso 4: Leemos todos los datos que conforman el cuerpo del mensaje
        msj.setMensaje(LectorEntrada.leerString(entrada, (int) tamTexto));
        
        // Paso 5: el siguiente flag indica si se ha adjuntado archivo
        int adjunto = LectorEntrada.leerByte(entrada);
        
        if (adjunto == 1){
            msj.setHayAdjunto(true);
        }
        
        // Paso 6: leyendo los datos del archivo adjunto
        if (adjunto == 1){
            int lenFlagTamDatos = LectorEntrada.leerByte(entrada);
            long tamDatos = LectorEntrada.leerBigInteger(entrada, lenFlagTamDatos);
            msj.setDatos(LectorEntrada.leerArrayBytes(entrada, (int) tamDatos));
            
            // Paso 6.1: Leyendo la extension del archivo
            int tamExtension = LectorEntrada.leerByte(entrada);
            msj.setExtensionArchivo(LectorEntrada.leerString(entrada, tamExtension));
            
            // Paso 6.2: leyendo el md5Checksum asociado al archivo
            int tamMd5 = LectorEntrada.leerByte(entrada);
            msj.setMd5Checksum(LectorEntrada.leerString(entrada, tamMd5));
        }
        
        // Paso 7: el siguiente flag indica el tamaño en bytes de la firma
        int tamFirma = LectorEntrada.leerByte(entrada);
        msj.setFirmaDigital(LectorEntrada.leerArrayBytes(entrada, tamFirma));
        
        // Paso 8: el siguiente flag indica el tamaño en bytes de la llave publica
        int tamLlave = LectorEntrada.leerByte(entrada);
        msj.setLlavePublica(LectorEntrada.leerArrayBytes(entrada, tamLlave));
        
        // Paso 9: guardar los bytes originales
        msj.setDatosOriginales(LectorEntrada.datosLeidos);
        LectorEntrada.datosLeidos = new ListaByte();
        
        return msj;
    }
}
