/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ValidateCertChain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.LDAPCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MLopes
 */
public class ValidateCertChain implements IValidateCertChain{
    private X509CertSelector  _certToValidate;
    private CertStore _certStore;

    @Override
    public boolean Validate(Certificate certificate, String intermediateCerts, KeyStore trustAnchors) {
            PKIXBuilderParameters builderParams;
        try {
            File[] certFiles = new File(intermediateCerts).listFiles();
            LinkedList<Certificate> certificateList = new LinkedList<Certificate>();
            CertificateFactory cf;
            for (File certFile : certFiles)
            {
                try {
                    cf = CertificateFactory.getInstance(certificate.getType());
                    certificateList.add(cf.generateCertificate(new FileInputStream(certFile)) );   
                } catch (CertificateException ex) {
                    Logger.getLogger(ValidateCertChain.class.getName()).log(Level.SEVERE, null, ex);
                }catch (FileNotFoundException ex) {
                        Logger.getLogger(ValidateCertChain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            _certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certificateList));
            _certToValidate = new X509CertSelector();
            _certToValidate.setCertificate((X509Certificate )certificate);
            builderParams = new PKIXBuilderParameters(trustAnchors, _certToValidate);
            builderParams.addCertStore(_certStore);
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
