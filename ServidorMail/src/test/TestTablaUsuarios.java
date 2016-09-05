/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import clases.tablahash.TablaHashEnlazadaUsuarios;
import clases.Usuario;

/**
 *
 * @author home
 */
public class TestTablaUsuarios {
    public static TablaHashEnlazadaUsuarios crearTablaPrueba(){
        TablaHashEnlazadaUsuarios tabla = new TablaHashEnlazadaUsuarios();
        
        // Creando Usuarios de prueba
        
        // Contraseña: test1234
        Usuario usuario1 = new Usuario("gomezlopez.jorge96@mail.com", "16d7a4fca7442dda3ad93c9a726597e4");
        
        // Contraseña: rr14004
        Usuario usuario2 = new Usuario("salvador.ramos@mail.com", "9adef10f178d241aa9d59f21c87ff94a");
        
        // Contraseña: cesar.lopez
        Usuario usuario3 = new Usuario("cesar.lopez@mail.com", "30e4898f6691fdae45365db25fb20350");
        
        // Contraseña: rusosvd
        Usuario usuario4 = new Usuario("ruso.svd.ues@mail.com", "f0fc068dc2ac239d4b3e75a51c83c6a6");
        
        
        // Añadiendo a la tabla hash
        tabla.insertarUsuario(usuario1);
        tabla.insertarUsuario(usuario2);
        tabla.insertarUsuario(usuario3);
        tabla.insertarUsuario(usuario4);
        
        return tabla;
    }
}
