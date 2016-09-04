/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemail;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author home
 */
public class FirmaDigital {
    public static KeyPair cargarLlaves(String rutaLlavePrivada, String rutaLlavePublica){
        KeyPair keyPair = null;
        
        try {
            // Paso 1: cargar llave publica
            File filePublicKey = new File(rutaLlavePublica);
            FileInputStream fis = new FileInputStream(rutaLlavePublica);
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
	    fis.close();
            
            // Paso 2: cargar llave privada
            File filePrivateKey = new File(rutaLlavePrivada);
            fis = new FileInputStream(rutaLlavePrivada);
            byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
	    fis.read(encodedPrivateKey);
	    fis.close();
            
            // Paso 3: recreando KeyPair
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            
            keyPair = new KeyPair(publicKey, privateKey);
        } catch(Exception e){
            System.out.println("Error leyendo llaves: " + e.toString());
        }
        
        return keyPair;
    }
    
    public static byte[] firmar(KeyPair keyPair, byte[] data){
        byte[] signatureBytes = null;
        
        try {
            Signature sig = Signature.getInstance("MD5WithRSA");
            sig.initSign(keyPair.getPrivate());
            sig.update(data);
            signatureBytes = sig.sign();
        } catch(Exception e){
            System.out.println("Error al realizar firma digital: " + e.toString());
        }   
        
        return signatureBytes;
    }
    
    public static byte[] cargarLlavePublica(String rutaLlavePublica){
        byte[] encodedPublicKey = null;
        
        try {
            File filePublicKey = new File(rutaLlavePublica);
            FileInputStream fis = new FileInputStream(rutaLlavePublica);
            encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
	    fis.close();
        } catch(Exception e){
            
        }
        
        return encodedPublicKey;
    }
}
