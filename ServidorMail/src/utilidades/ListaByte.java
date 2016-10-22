/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.Serializable;

/**
 *
 * @author home
 */
public class ListaByte implements Serializable{
    private byte[] array = null;
    private int tamanio = 0;
    
    public ListaByte(){
    }
    
    public void agregarByte(byte b){
        byte[] temp = new byte[1];
        temp[0] = b;
        
        byte[] arrayFinal = new byte[tamanio + 1];
        
        if (array == null){
            System.arraycopy(temp, 0, arrayFinal, 0, 1);
        } else {
            System.arraycopy(array, 0, arrayFinal, 0, array.length);
            System.arraycopy(temp, 0, arrayFinal, array.length, 1);
        }
        
        array = arrayFinal;
        tamanio = array.length;
    }
    
    public void agregarArrayByte(byte[] b){
        byte[] arrayFinal = new byte[tamanio + b.length];
        
        if (array == null){
            System.arraycopy(b, 0, arrayFinal, 0, b.length);
        } else {
            System.arraycopy(array, 0, arrayFinal, 0, array.length);
            System.arraycopy(b, 0, arrayFinal, array.length, b.length);
        }
        
        array = arrayFinal;
        tamanio = array.length;
    }
    
    public void agregarString(String texto){
        char[] textoChar = texto.toCharArray();
        byte[] textoBytes = new byte[textoChar.length];
        
        for (int i=0; i<textoChar.length; i++){
            textoBytes[i] = (byte) textoChar[i];
        }
        
        this.agregarArrayByte(textoBytes);
    }
    
    public byte[] getBytes(){
        return this.array;
    }
}
