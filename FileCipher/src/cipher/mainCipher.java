/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cipher;

import FileCipher.FileCipher;
import KeyCipher.KeyCipher;
import KeyGenerator.KeyGeneratorCipher;
import ValidateCertChain.ValidateCertChain;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/**
 *
 * @author MLopes
 */
public class mainCipher {

    /**
     * @param args the command line arguments
     */
    private static String _filePath;
    private static String _filePathMetadata;
    private static String _filePathSecret;
    private static String _keyPath;
    private static String _operation;
    private static String _keyGenAlgorithm;
    private static String _certType;
    private static MetadataFormater _ff;
    private static FileCipher _fc;
    private static FileStreamOps _fso;
    private static String _password;
        
    public static void main(String[] args) throws CertificateException, FileNotFoundException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, UnrecoverableEntryException {
        if(args.length < 4) throw new IllegalArgumentException("Arguments missing -> Key_Path Certificate_Type File_Path Operation  [File_Path_Metadata] [File_Path_Secret] [KeyStore_Password] [Key_Generator_Algorithm]");
        _filePath = args[2];
        _keyPath = args[0];
        _operation = args[3];
        _keyGenAlgorithm = args.length >= 8 ? args[7] : "AES";
        _certType = args[1];
        _filePathMetadata = args.length >= 5 ? args[4] : "";
        _filePathSecret = args.length >= 6 ? args[5] : "";
        _password = args.length >= 7 ? args[6] : "";
        _ff = new MetadataFormater();
        _fc = new FileCipher();
        _fso = new FileStreamOps();
        if(_operation.equals("cipher")){
            Key k = new KeyGeneratorCipher().GetKey(_keyGenAlgorithm);
            Certificate cert = new KeyGetter().GetPublicCertificate(_keyPath, _certType);
            if(! new ValidateCertChain().Validate(cert)) throw new CertificateException("Invalid Certificate");
            _ff.SetCipheredSimetricKey(new KeyCipher(cert.getPublicKey().getAlgorithm()).Set(k, cert));
            _ff.SetPublicCertificate(cert);
            _fc.SetAlgorithm(k.getAlgorithm());
            _fso.WriteFileEager(_filePath.concat(".Metadata"), _ff.ToByteArray());           
            try{
                _fso.ReadFileLazyInit(_filePath);
                _fso.WriteFileLazyInit(_filePath.concat(".secret"));
                _fc.SetInit(k);
                byte[] b = _fso.ReadFileLazy();
                while(b != null){
                    _fso.WriteFileLazy(_fc.CripherDecipher(b));
                    b = _fso.ReadFileLazy();
                }
            }finally{
                _fso.ReadFileLazyClose();
                _fso.WriteFileLazyClose();
            }
            return;
        }
        if(_operation.equals("decipher")){
            _ff.SubmitToFormater(_fso.ReadFileEager(_filePathMetadata));
            KeyStore ks = new KeyGetter().GetKeyStore(_keyPath, _password, _certType);
            Enumeration<String> aliases = ks.aliases();
            PrivateKey pk = null;
            String alias = null;
            while(aliases.hasMoreElements()){
                Entry entry = ks.getEntry((alias = aliases.nextElement()), new KeyStore.PasswordProtection(_password.toCharArray()));
                if(entry instanceof PrivateKeyEntry){
                    PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) entry;
                    if(privateKeyEntry.getCertificate().equals(_ff.GetPublicCertificate())){
                        pk = privateKeyEntry.getPrivateKey();
                    break;
                    }
                }
            }
            if(pk == null) throw new CertificateException("Invalid Certificate");
            //_fc.SetAlgorithm(pk.getAlgorithm());
        try{
                _fso.ReadFileLazyInit(_filePathSecret);
                _fso.WriteFileLazyInit(_filePath);
                //_fc.GetInit(new KeyCipher(ks.getCertificate(alias).getPublicKey().getAlgorithm()).Get(
                //        _ff.GetCipheredSimetricKey(),ks.getCertificate(alias)));
                Key k = new KeyCipher(ks.getCertificate(alias).getPublicKey().getAlgorithm()).Get(
                        _ff.GetCipheredSimetricKey(),pk,_keyGenAlgorithm);
                _fc.SetAlgorithm(_keyGenAlgorithm);
                _fc.GetInit(k);
                byte[] b = _fso.ReadFileLazy();
                while(b != null){
                    _fso.WriteFileLazy(_fc.CripherDecipher(b));
                    b = _fso.ReadFileLazy();
                }
            }finally{
                _fso.ReadFileLazyClose();
                _fso.WriteFileLazyClose();
            }
            return;   
            
        }
        throw  new IllegalArgumentException("Unknown Operation");       
    }
}
