/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyGenerator;

import java.security.Key;

/**
 *
 * @author MLopes
 */
public class KeyGenerator implements IKeyGenerator {

    @Override
    public Key GetKey(String Algorithm) {
        if(Algorithm == null) throw new IllegalArgumentException();
        return new KeyGenerator().GetKey(Algorithm);      
    }
    
}
