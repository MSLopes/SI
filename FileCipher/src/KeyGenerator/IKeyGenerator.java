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
public interface IKeyGenerator {
    Key GetKey(String Algorithm);
}
