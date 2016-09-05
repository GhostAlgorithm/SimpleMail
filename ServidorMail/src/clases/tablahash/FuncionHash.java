/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.tablahash;

/**
 *
 * @author home
 */
public class FuncionHash {
    static final double R = 0.618034;
    
    public static int dispersion(int tamtabla, long x){
        double t;
        int v;
        
        t = R*x - Math.floor(R*x);
        v = (int) (tamtabla*t);
        return v;
    }
}
