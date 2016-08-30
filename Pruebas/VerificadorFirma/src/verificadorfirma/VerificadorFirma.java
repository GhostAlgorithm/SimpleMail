/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificadorfirma;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author home
 */
public class VerificadorFirma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "C:\\llaves";
        String algorithm = "RSA";
        
        try {
            // Verificando firma
            Signature sig = Signature.getInstance("MD5WithRSA");
            
            // Leyendo clave publica del que ha firmado
            File filePublicKey = new File(path + "/public.key");
            FileInputStream fis = new FileInputStream(path + "/public.key");
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
	    fis.close();
            System.out.println("Clave publica leida con exito!");
            
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
            // agregandola al objeto signature
            sig.initVerify(publicKey);
            
            // Agregando data a verificar
            String mensaje = "Mensaje que yo envie";
            byte[] data = mensaje.getBytes("UTF8");
            sig.update(data);
            
            // Leyendo firma digital
            File firmaArchivo = new File(path + "/firma.sig");
            fis = new FileInputStream(path + "/firma.sig");
            byte[] datosFirma = new byte[(int) firmaArchivo.length()];
            fis.read(datosFirma);
            
            // comprobando validez
            boolean verifies = sig.verify(datosFirma);
            
            if (verifies){
                System.out.println("Firma verificada con exito, es la persona real!");
            } else {
                System.out.println("Firma no paso prueba de verificacion, ERROR!");
            }
            
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    
}
