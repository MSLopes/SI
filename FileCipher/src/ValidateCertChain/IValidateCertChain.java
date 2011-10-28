/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ValidateCertChain;

import java.security.KeyStore;
import java.security.cert.Certificate;

/**
 *
 * @author MLopes
 */
public interface IValidateCertChain {
    boolean Validate(Certificate certificate, String intermediateCerts, KeyStore trustAnchors);
}
