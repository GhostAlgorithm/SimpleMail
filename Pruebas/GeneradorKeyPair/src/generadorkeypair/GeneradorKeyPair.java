/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorkeypair;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author home
 */
public class GeneradorKeyPair {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String path = "";
        
        try {
            // Generando manager de generador de llaves publica/privada
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            
            // Generando llave publica/privada
            KeyPair generatedKeyPair = keyGen.genKeyPair();
            System.out.println("Llave publica/privada generada");
            
            // Guardando la llave por separado
            // Guardando la clave privada primero
            PrivateKey privateKey = generatedKeyPair.getPrivate();
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            FileOutputStream fos = new FileOutputStream(path + "private.key");
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();
            System.out.println("Llave privada guardada con exito!");
            
            // Guardando la llave publica
            PublicKey publicKey = generatedKeyPair.getPublic();
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            fos = new FileOutputStream(path + "public.key");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();
            System.out.println("Llave publica guardada con exito!");
            
            JOptionPane.showMessageDialog(null, "Llaves generadas con éxito!");
        } catch(Exception e){
            
        }
    }
    
}
