/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lectorkeypairfirma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class LectorKeyPairFirma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Leyendo firmas generadas anteriormente
        String path = "C:\\llaves";
        String algorithm = "RSA";
        
        try {
            System.out.println("Reconstruyendo objeto KeyPair");
            System.out.println("Leyendo clave publica");
            // Leyendo llave publica
            File filePublicKey = new File(path + "/public.key");
            FileInputStream fis = new FileInputStream(path + "/public.key");
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
	    fis.close();
            System.out.println("Clave publica leida con exito!");
            
            System.out.println("Leyendo clave privada");
            // Leyendo clave privada
            File filePrivateKey = new File(path + "/private.key");
            fis = new FileInputStream(path + "/private.key");
            byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
	    fis.read(encodedPrivateKey);
	    fis.close();
            System.out.println("Clave privada leida con exito!");
            
            // Recreando objeto keyPair
            System.out.println("Creando objeto KeyPair");
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            
            KeyPair keyPair = new KeyPair(publicKey, privateKey);
            System.out.println("Objeto KeyPair creado con exito!");
            
            // Realizando firma
            System.out.println("Creando firma de prueba");
            Signature sig = Signature.getInstance("MD5WithRSA");
            sig.initSign(keyPair.getPrivate());
            String mensaje = "Mensaje que yo envie";
            byte[] data = mensaje.getBytes("UTF8");
            sig.update(data);
            byte[] signatureBytes = sig.sign();
            FileOutputStream fos = new FileOutputStream(path + "/firma.sig");
            fos.write(signatureBytes);
            fos.close();
            System.out.println("Firma digital guardada con exito!");
        } catch(Exception e){
            
        }
        
    }
    
}
