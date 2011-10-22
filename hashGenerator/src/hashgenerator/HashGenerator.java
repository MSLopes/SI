package hashgenerator;

import HashGen.HashGen;
import HashGen.IHashGen;
import java.io.IOException;

/**
 *
 * @author MLopes
 */

public class HashGenerator {
    /**
     * @param Hash function, file to generate hash
     */
    public static void main(String[] args) throws IOException{ 
        if(args.length <2) throw new IllegalArgumentException("Arguments missing");
        IHashGen hg = new HashGen();
        hg.Init(args[0], args[1], 0);
        hg.Generate();
        System.out.println(hg.GetTumbPrint());
    }
}
