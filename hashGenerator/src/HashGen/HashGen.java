package HashGen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MLopes
 */
public class HashGen implements IHashGen{
    private boolean _inited;
    private int MAXBYTES = 1024;
    private InputStream _is; 
    private MessageDigest _md;
    private byte[] _forToString;

    public HashGen() {
        _inited = false;
    }
    
    public byte[] Generate() throws IOException{
        if(!_inited) throw new IllegalStateException("Object isn't inited!");
        int exit = 0;
        byte[] sub = new byte[MAXBYTES];
        do{
            exit = _is.read(sub);
            _md.update(sub,0,exit);
        }while(exit == MAXBYTES);
        return (_forToString = _md.digest());
    }
    
    public String GetTumbPrint(){
        if(_forToString == null) throw new IllegalStateException("Object isn't inited or Generate Method hasn't been called.");
        StringBuilder sb = new StringBuilder();
        for(byte b : _forToString){
            sb.append(String.format(" %02X", b));
        }
        return sb.toString();
    }

    @Override
    public void Init(String hash, String fileToHash, int bufferSize) {
        try {
            if(bufferSize > 0) MAXBYTES = bufferSize;
            _md = MessageDigest.getInstance(hash);
            _is = new FileInputStream(fileToHash);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HashGen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashGen.class.getName()).log(Level.SEVERE, null, ex);
        }
        _inited = true;
    }
}
