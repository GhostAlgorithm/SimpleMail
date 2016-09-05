/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.protocolomail;

import java.io.InputStream;
import java.math.BigInteger;
import utilidades.LectorEntrada;

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
    
    public static MensajeInicioSesion procesarInicioSesion(InputStream entrada) throws Exception{
        MensajeInicioSesion msj = new MensajeInicioSesion();
        
        // Paso 1: Leer el primer byte e identificar el tamaño del correo:
        int tamanioEmail = entrada.read();
        msj.setEmail(LectorEntrada.leerString(entrada, tamanioEmail));
        
        // Paso 2: leer el tamanio del Hash
        int tamanioHash = entrada.read();
        msj.setPassword(LectorEntrada.leerString(entrada, tamanioHash));
        
        return msj;
    }
    
    public static MensajeComprobarDestinatario procesarCompDestinatario(InputStream entrada) throws Exception{
        MensajeComprobarDestinatario msj = new MensajeComprobarDestinatario();
        
        // Paso 1: el primr byte es para identificar el tamaño del correo
        int tamanioEmail = entrada.read();
        
        // Paso 2: leer el email completo
        msj.setEmail(LectorEntrada.leerString(entrada, tamanioEmail));
        return msj;
    }
    
    public static MensajeEnviarCorreo procesarEnvioCorreo(InputStream entrada) throws Exception{
        MensajeEnviarCorreo msj = new MensajeEnviarCorreo();
        
        // Paso 1: el primer byte indica el tamaño en caractres del correo del remitente
        int tamanioRemitente = entrada.read();
        msj.setRemitente(LectorEntrada.leerString(entrada, tamanioRemitente));
        
        // Paso 2: el byte represente el tamaño en caracteres del correo del destinatario
        int tamanioDestinatario = entrada.read();
        msj.setDestinatario(LectorEntrada.leerString(entrada, tamanioDestinatario));
        
        // Paso 3: el siguiente byte indica el tamaño en bytes del flag tamaño texto
        int lenFlagTamTexto = entrada.read();
        long tamTexto = LectorEntrada.leerBigInteger(entrada, lenFlagTamTexto);
        
        // Paso 4: Leemos todos los datos que conforman el cuerpo del mensaje
        msj.setMensaje(LectorEntrada.leerString(entrada, (int) tamTexto));
        
        // Paso 5: el siguiente flag indica si se ha adjuntado archivo
        int adjunto = entrada.read();
        
        if (adjunto == 1){
            msj.setHayAdjunto(true);
        }
        
        // Paso 6: leyendo los datos del archivo adjunto
        if (adjunto == 1){
            int lenFlagTamDatos = entrada.read();
            long tamDatos = LectorEntrada.leerBigInteger(entrada, lenFlagTamDatos);
            msj.setDatos(LectorEntrada.leerArrayBytes(entrada, (int) tamDatos));
            
            int tamExtension = entrada.read();
            msj.setExtensionArchivo(LectorEntrada.leerString(entrada, tamExtension));
        }
        
        // Paso 7: el siguiente flag indica el tamaño en bytes de la firma
        int tamFirma = entrada.read();
        msj.setFirmaDigital(LectorEntrada.leerArrayBytes(entrada, tamFirma));
        
        // Paso 8: el siguiente flag indica el tamaño en bytes de la llave publica
        int tamLlave = entrada.read();
        msj.setLlavePublica(LectorEntrada.leerArrayBytes(entrada, tamLlave));
        
        return msj;
    }
}
