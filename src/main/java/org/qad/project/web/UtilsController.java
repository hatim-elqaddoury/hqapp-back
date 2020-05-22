package org.qad.project.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import org.qad.project.models.ActiveUser;
import org.qad.project.models.User;

public class UtilsController {
	private static final Logger log = Logger.getLogger(UtilsController.class);
	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS ");
	DateFormat formater2 = DateFormat.getDateTimeInstance(2, 2, new Locale("FR", "fr"));
	
	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";

	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception var5) {
			log.error(var5.getMessage());
			return null;
		}
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public void addActiveUser(List<ActiveUser> lau, Optional<User> user, String token) {
		boolean containsOne = false;
		
		Iterator var5 = lau.iterator();

		while (var5.hasNext()) {
			ActiveUser a = (ActiveUser) var5.next();
			if (a.getIdUSer().equals(((User) user.get()).getIdUser())) {
				containsOne = true;
			}
		}

		if (!containsOne) {
			lau.add(new ActiveUser(((User) user.get()).getIdUser(), token));
		}

	}

	public byte[] compressBytes(byte[] data) {
		try {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];

			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}

			try {
				outputStream.close();
			} catch (IOException var6) {
				log.error(var6.getMessage());
			}

			log.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];

		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}

			outputStream.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (DataFormatException e) {
			log.error(e.getMessage());
		}

		return outputStream.toByteArray();
	}
}