/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ValidateCertChain;

import java.security.cert.Certificate;

/**
 *
 * @author MLopes
 */
public class ValidateCertChain implements IValidateCertChain{

    @Override
    public boolean Validate(Certificate certificate) {
        return true;
    }
}
