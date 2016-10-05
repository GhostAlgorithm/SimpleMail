/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.InputStream;
import java.math.BigInteger;

/**
 *
 * @author home
 */
public class LectorEntrada {
    public static ListaByte datosLeidos = new ListaByte();
    
    public static String leerString(InputStream entrada, int tamanio) throws Exception{
        String texto = "";
        
        for (int i=0; i<tamanio; i++){
            texto += (char) entrada.read();
        }
        
        datosLeidos.agregarString(texto);
        return texto;
    }
    
    public static long leerBigInteger(InputStream entrada, int tamanio) throws Exception{
        long resultado = 0;
        
        byte[] bigInteger = new byte[tamanio];
        for (int i = 0; i<tamanio; i++){
            bigInteger[i] = (byte) entrada.read();
        }
        
        datosLeidos.agregarArrayByte(bigInteger);
        resultado = new BigInteger(bigInteger).longValue();
        return resultado;
    }
    
    public static byte[] leerArrayBytes(InputStream entrada, int tamanio) throws Exception{
        byte[] datos = new byte[tamanio];
        
        for (int i=0; i<tamanio; i++){
            datos[i] = (byte) entrada.read();
        }
        
        datosLeidos.agregarArrayByte(datos);
        return datos;
    }
    
    public static int leerByte(InputStream entrada) throws Exception{
        int num =  entrada.read();
        datosLeidos.agregarByte((byte) num);
        return num;
    }
}
