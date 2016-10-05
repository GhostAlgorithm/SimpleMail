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
public class IntegridadInformacion {
    public static String generarMD5Checksum(byte[] data){
        String resultado = "";
        
        byte[] md5Hash = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md5Hash = md.digest(data);
        } catch(Exception e){
        }
        
        resultado = new BigInteger(1, md5Hash).toString(16);
        return resultado;
    }
    
    public static boolean validezDelArchivo(byte[] data, String md5Hash){
        boolean resultado = false;
        
        // Generando el md5checksum a partir de los datos del archivo
        String md5Nuevo = IntegridadInformacion.generarMD5Checksum(data);
        
        if (md5Nuevo.equals(md5Hash)){
            resultado = true;
        }
        
        return resultado;
    }
}
