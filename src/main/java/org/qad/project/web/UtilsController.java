package org.qad.project.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.qad.project.assets.MailTemplate;
import org.qad.project.models.ActiveUser;
import org.qad.project.models.User;
import org.springframework.stereotype.Service;


@Service
public class UtilsController {
	private static final Logger log = Logger.getLogger(UtilsController.class);
	public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS ");
	public static DateFormat formater2 = DateFormat.getDateTimeInstance(2, 2, new Locale("FR", "fr"));
	
	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";
	
	private static final boolean EnableEmails = true;
	

	public static Object encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static Object decrypt(String encrypted) {
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

	public String ToEncryptedJson(String obj) {
		return new JSONObject().put("encrypted",encrypt(obj)).toString();
	}
	
	public void addActiveUser(List<ActiveUser> lau, Optional<User> user, String token) {
		boolean containsOne = false;
        for (ActiveUser a : lau) {
        	if( a.getIdUSer().equals(user.get().getIdUser())) {
        		a.setJwt(token);
        		containsOne=true;
        	}
        }
        if (!containsOne) lau.add(new ActiveUser(user.get().getIdUser(), token));
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
	
	
	public static boolean sendMail(User USR, String SUBJECT, String CONTENT, String SENDER, String PASSWORD, String HOST, String PORT) {
		if(EnableEmails) {
			Properties properties = System.getProperties();
	        // Setup mail server
	        properties.put("mail.smtp.host", HOST);
	        properties.put("mail.smtp.port", PORT);
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");

	        // Get the Session object.// and pass username and password
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(SENDER, PASSWORD);
	            }
	        });

	        // Used to debug SMTP issues
	        //session.setDebug(true);
	        
	        String MailContent = MailTemplate.GetMailTemplate(USR, CONTENT);
	        

	        try {
	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(SENDER));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(USR.getEmail()));
	            message.setSubject(SUBJECT);
	            message.setContent(MailContent, "text/html; charset=utf-8");

	            // Send message
	            Transport.send(message);
	            log.info("Email sent to "+USR.getFullname()+" : "+SUBJECT);
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	        return true;
		}
		return false;
        
	}
	
	
}