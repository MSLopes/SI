/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyExchanger;

import java.security.cert.Certificate;
import java.security.Key;

/**
 *
 * @author MLopes
 */
public interface IKeyCipher {
    byte[] Set (Key key , Certificate cert);
    Key Get (byte[] input , Certificate cert);
    void SetAlgorithm(String Algorithm);
}
