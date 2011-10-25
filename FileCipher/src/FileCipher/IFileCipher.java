/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileCipher;

import java.security.Key;

/**
 *
 * @author MLopes
 */
public interface IFileCipher {
    void GetInit (Key Key);
    void SetInit (Key Key);
    void SetAlgorithm(String Algorithm);
    public byte[] CripherDecipher(byte[] input);
    public int GetSize();
}
