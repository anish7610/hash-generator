import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


public class HashGenerator {
	private String algorithm;
	
	public HashGenerator(String algorithm) {
		this.algorithm = algorithm;
	}
	

	public String generateHash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
			return convertToHexString(hashBytes);
			
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String generateHashForFile(String filePath) {
		try  (FileInputStream fis = new FileInputStream(filePath);
			DigestInputStream dis = new DigestInputStream(fis, MessageDigest.getInstance(algorithm))) {
			
			byte[] buffer = new byte[8192];
			
			while (dis.read(buffer) != -1) {
				
			}
			
			byte[] hashBytes = dis.getMessageDigest().digest();
			return convertToHexString(hashBytes);
			
		} catch(IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String convertToHexString(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for(byte b: bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
