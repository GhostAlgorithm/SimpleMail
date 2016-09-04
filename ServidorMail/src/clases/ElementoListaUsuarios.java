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
public class ElementoListaUsuarios {
    private Usuario usuario;
    private ElementoListaUsuarios siguiente = null;
    
    public ElementoListaUsuarios(Usuario usuario){
        this.setUsuario(usuario);
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }
    
    public void setSiguiente(ElementoListaUsuarios elemento){
        this.siguiente = elemento;
    }
    
    public ElementoListaUsuarios getSiguiente(){
        return this.siguiente;
    }
}
