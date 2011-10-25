/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ValidateCertChain;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MLopes
 */
public class ValidateCertChain implements IValidateCertChain{
    private X509CertSelector  certToValidate;

    @Override
    public boolean Validate(Certificate certificate, CertStore intermediateCerts, KeyStore trustAnchors) {
            PKIXBuilderParameters builderParams;
        try {
            certToValidate = new X509CertSelector();
            certToValidate.setCertificate((X509Certificate )certificate);
            builderParams = new PKIXBuilderParameters(trustAnchors, certToValidate);
            builderParams.addCertStore(intermediateCerts);
            builderParams.setRevocationEnabled(false);
            CertPathBuilder builder;
            builder = CertPathBuilder.getInstance("PKIX");
            builder.build(builderParams); 
            return true;
        } catch (KeyStoreException ex) {
            Logger.getLogger(ValidateCertChain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(ValidateCertChain.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ValidateCertChain.class.getName()).log(Level.SEVERE, null, ex);
        }catch (CertPathBuilderException ex) {
                Logger.getLogger(ValidateCertChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
