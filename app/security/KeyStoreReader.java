package security;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * 
 * Cita is keystore fajla
 */
public class KeyStoreReader {

	private String KEY_STORE_FILE = "./data/marija.jks";
	private char[] password = "sgns".toCharArray();
	private char[] keyPass  = "marija1".toCharArray();
	
	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public char[] getKeyPass() {
		return keyPass;
	}

	public void setKeyPass(char[] keyPass) {
		this.keyPass = keyPass;
	}

	public String getKeyStoreFile() {
		return KEY_STORE_FILE;
	}
	
	public void setKeyStoreFile(String key_store_file){
		this.KEY_STORE_FILE= key_store_file;
	}


	
	
	public void testIt() {
		readKeyStore();
	}
	
	public HashMap<String,Certificate> readKeyStore(){
		HashMap<String,Certificate> sertifikati = new HashMap<String,Certificate>();
	
		try {
			Enumeration<String> alijasi = null;
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			if(in.available()>0)
			{
				ks.load(in, password);
			//citamo par sertifikat privatni kljuc
				alijasi = ks.aliases();
			
			while(alijasi.hasMoreElements()){
				String alijas = alijasi.nextElement();
				System.out.println("sertifikat za "+alijas+ ":");
				if(ks.isKeyEntry(alijas)) {
					Certificate cert = ks.getCertificate(alijas);
					System.out.println(cert);
					sertifikati.put(alijas, cert);
				}
				else
					//System.out.println("Nema sertifikata za "+alijas);
				//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				if(ks.isCertificateEntry(alijas)) {
					Certificate cert = ks.getCertificate(alijas);
					System.out.println(cert);
					sertifikati.put(alijas, cert);
				}
				else
					System.out.println("Nema sertifikata za "+alijas);
				
			}
			
			}
			in.close();
		
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sertifikati;

	}
	
	public static void main(String[] args) {
		KeyStoreReader test = new KeyStoreReader();
		test.testIt();
	}
}
