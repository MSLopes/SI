/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileCipher;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author MLopes
 */
public class FileCipher implements IFileCipher {

    private String _algorithm;
    private Cipher _cipher;
    private int MAXBYTES;
    
    public FileCipher(String Algorithm) {
        _algorithm = Algorithm;
        MAXBYTES = 1024;
    }
    
    private byte[] CripherDecipher(byte[] input){
        for(int idx = 0; idx < input.length; idx+=MAXBYTES){
        if((idx + MAXBYTES) >= input.length){
            try {
                return _cipher.doFinal(input, idx, MAXBYTES);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            _cipher.update(input,idx,MAXBYTES);
        }    
        return null;
    }
    
    
    
    @Override
    public byte[] Get(byte[] input, Key Key) {
        if(_algorithm == null) throw new IllegalStateException("Algorithm must be defined.");
        try {
            _cipher = Cipher.getInstance(_algorithm);
            _cipher.init(Cipher.DECRYPT_MODE , Key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CripherDecipher(input);
    }

    @Override
    public byte[] Set(byte[] input, Key Key) {
                if(_algorithm == null) throw new IllegalStateException("Algorithm must be defined.");
        try {
            _cipher = Cipher.getInstance(_algorithm);
            _cipher.init(Cipher.ENCRYPT_MODE , Key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FileCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CripherDecipher(input);
    }

    @Override
    public void SetAlgorithm(String Algorithm) {
        _algorithm = Algorithm;
    }

    
}
