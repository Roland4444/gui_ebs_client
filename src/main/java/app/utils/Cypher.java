/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.utils;

/**
 *
 * @author roland
 */
public interface Cypher {
    public byte [] decrypt(byte []input);
    public byte [] encrypt(byte []input);      
    
    
}
