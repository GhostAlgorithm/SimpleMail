/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import static clases.TablaHashEnlazadaUsuarios.TAMTABLA;
import clases.protocolomail.MensajeEnviarCorreo;

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
}
