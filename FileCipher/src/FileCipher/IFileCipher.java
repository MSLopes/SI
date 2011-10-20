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
    byte[] Get (byte[] input ,Key Key);
    byte[] Set (byte[] input , Key Key);
    void SetAlgorithm(String Algorithm);
}
