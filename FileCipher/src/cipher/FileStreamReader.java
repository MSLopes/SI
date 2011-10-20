/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author MLopes
 */
public class FileStreamReader {

    private File _if;
    private InputStream _is;
    private OutputStream _os;
    private byte[] _ib;
    private int MAXBYTES;

    public FileStreamReader() {
        MAXBYTES = 1024;
    }
        
    public byte[] ReadFileEager(String FilePath) throws FileNotFoundException, IOException{
        _if = new File(FilePath);
        _is = new FileInputStream(FilePath);
        _ib = new byte[(int)_if.length()];
        _is.read(_ib);
        _is.close();
        return _ib;
    }
    
    public void ReadFileLazyInit(String FilePath) throws FileNotFoundException{
        _is = new FileInputStream(FilePath);
    }
    public byte[] ReadFileLazy() throws IOException{
        _ib = new byte[MAXBYTES];
        _is.read(_ib);
        return _ib;
    }
    public void ReadFileLazyClose() throws IOException{
        _is.close();
    }
    
    
    
    public void WriteFileEager(String FilePath, byte[] input) throws FileNotFoundException, IOException{
        _os = new FileOutputStream(FilePath);
        _os.write(input);
        _os.close();
    }
    
    public void WriteFileLazyInit(String FilePath) throws IOException{
        _os = new FileOutputStream(FilePath);
    }
    public void WriteFileLazy(byte[] input) throws FileNotFoundException, IOException{      
        _os.write(input);
    }
    public void WriteFileLazyClose() throws IOException{
        _os.close();
    }
}
