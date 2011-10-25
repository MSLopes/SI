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
        if(args.length <2) throw new IllegalArgumentException("Arguments missing -> Hash_Algorithm File_Path [Buffer_Size]");
        IHashGen hg = new HashGen();
        hg.Init(args[0], args[1], args.length > 2 ? Integer.parseInt(args[2]):0);
        hg.Generate();
        System.out.println(hg.GetTumbPrint());
    }
}
