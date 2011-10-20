/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyExchanger;

import ValidateCertChain.ValidateCertChain;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;

/**
 *
 * @author MLopes
 */
public class KeyCipher implements IKeyCipher{

    ValidateCertChain _vc;
    Cipher _cipher;
    String _algorithm;

    public KeyCipher(String Algorithm){
        _vc = new ValidateCertChain();
        _algorithm = Algorithm;
    }
    
        
    @Override
    public byte[] Set(Key key, Certificate cert) {
         if(_algorithm == null) throw new IllegalStateException("Algorithm must be defined.");
        if(_vc.Validate(cert)) throw new SecurityException("Invalid Certificate");
        try {
            _cipher = Cipher.getInstance(_algorithm);
            _cipher.init(Cipher.WRAP_MODE, cert);
            _cipher.wrap(key);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(KeyCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(KeyCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
                Logger.getLogger(KeyCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Key Get(byte[] input, Certificate cert) {
        try {
            return _cipher.unwrap(input, _algorithm, Cipher.SECRET_KEY);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(KeyCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void SetAlgorithm(String Algorithm) {
        _algorithm = Algorithm;  
    }
}
