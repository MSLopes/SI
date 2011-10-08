package HashGen;

import java.io.IOException;

/**
 *
 * @author MLopes
 */
public interface IHashGen {
    void Init(String hash, String fileToHash, int bufferSize);
    byte[] Generate()throws IOException;
    String GetTumbPrint();
}
