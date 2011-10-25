/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cipher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 *
 * @author MLopes
 */
public class KeyGetter {
    
    //"PKCS12"
    public KeyStore GetKeyStore(String FilePath, String KeyStorePassword, String KeyStoreType) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException{
        KeyStore keyStore = KeyStore.getInstance(KeyStoreType);
        FileInputStream keyStoreStream = new FileInputStream(FilePath);
        keyStore.load(keyStoreStream,KeyStorePassword.toCharArray());
        return keyStore;
    }
    //"X.509"
    public Certificate GetPublicCertificate(String FilePath, String CertificateType) throws CertificateException, FileNotFoundException{
        CertificateFactory Certfactory = CertificateFactory.getInstance(CertificateType);  
        Certificate generateCertificate = (Certificate)Certfactory.generateCertificate(new FileInputStream(FilePath));
        return generateCertificate;
    }
    
}
