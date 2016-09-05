/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.seguridad;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author home
 */
public class FirmaDigital {
    public static boolean comprobarFirmaDigital(byte[] data, byte[] llavePublica, byte[] firma){
        boolean resultado = false;
        
        try {
            Signature sig = Signature.getInstance("MD5WithRSA");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(llavePublica);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
            sig.initVerify(publicKey);
            sig.update(data);
            resultado = sig.verify(firma);
        } catch(Exception e){
            System.out.println("Error al comprobar firma digital");
        }
        
        return resultado;
    }
}
