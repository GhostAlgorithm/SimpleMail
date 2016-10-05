/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.tablahash;

import clases.protocolomail.MensajeEnviarCorreo;
import java.util.ArrayList;

/**
 *
 * @author home
 */
public class TablaHashEnlazadaCorreos {
    static final int TAMTABLA = 500;
    private ElementoListaCorreos[] tabla;
    private int numeroElementos = 0;
    
    public TablaHashEnlazadaCorreos(){
        this.tabla = new ElementoListaCorreos[TAMTABLA];
        
        for (int i =0; i<TAMTABLA; i++){
            this.tabla[i] = null;
        }
    }
    
    public void insertarCorreo(MensajeEnviarCorreo mensaje){
        int posicion;
        int x = 0;
        
        // Paso 1: Generar un numero entero en base al correo del usuario
        char[] email = mensaje.getDestinatario().toCharArray();
        for (int i=0; i<email.length; i++){
            x += (int) email[i];
        }
        
        // Paso 2: obteniendo posicion en base a la funcion hash
        posicion = FuncionHash.dispersion(TAMTABLA, x);
        
        // Paso 3: creando elementos y actualizando lista
        ElementoListaCorreos correoNuevo = new ElementoListaCorreos(mensaje);
        correoNuevo.setSiguiente(this.tabla[posicion]);
        this.tabla[posicion] = correoNuevo;
        this.numeroElementos++;
    }
    
    public ArrayList<ElementoListaCorreos> buscarCorreos(String mail){
        int posicion;
        int x = 0;
        ArrayList<ElementoListaCorreos> correos = new ArrayList();
        
        char[] email = mail.toCharArray();
        for (int i=0; i<email.length; i++){
            x += (int) email[i];
        }
        
        // Paso 2: obteniendo posicion en base a la funcion hash
        posicion = FuncionHash.dispersion(TAMTABLA, x);
        
        // Paso 3: Obteniendo la referencia al primer correo
        ElementoListaCorreos primero = this.tabla[posicion];
        
        if (primero != null){
            correos.add(primero);
            ElementoListaCorreos correo = primero;
            
            while (correo.getSiguiente() != null){
                correo = correo.getSiguiente();
                correos.add(correo);
            }
        }
        
        return correos;
    }
}
