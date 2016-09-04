/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author home
 */
public class TablaHashEnlazadaUsuarios {
    static final int TAMTABLA = 200;
    private ElementoListaUsuarios[] tabla;
    private int numeroElementos = 0;
    
    public TablaHashEnlazadaUsuarios(){
        this.tabla = new ElementoListaUsuarios[TAMTABLA];
        for (int i=0; i<TAMTABLA; i++){
            this.tabla[i] = null;
        }
    }
    
    public void insertarUsuario(Usuario usuario){
        int posicion;
        int x = 0;
        
        // Paso 1: Generar un numero entero en base al correo del usuario
        char[] email = usuario.getCorreo().toCharArray();
        for (int i=0; i<email.length; i++){
            x += (int) email[i];
        }
        
        // Paso 2: obteniendo posicion en base a la funcion hash
        posicion = FuncionHash.dispersion(TAMTABLA, x);
        
        // Paso 3: creando elementos y actualizando lista
        ElementoListaUsuarios usuarioNuevo = new ElementoListaUsuarios(usuario);
        usuarioNuevo.setSiguiente(this.tabla[posicion]);
        this.tabla[posicion] = usuarioNuevo;
        this.numeroElementos++;
    }
}
