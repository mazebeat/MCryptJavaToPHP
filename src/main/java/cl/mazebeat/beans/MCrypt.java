package cl.mazebeat.beans;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Maze on 04-03-2015.
 */
public class MCrypt {
	private IvParameterSpec ivspec;
	private SecretKeySpec   keyspec;
	private Cipher          cipher;

	public MCrypt() {
		this.ivspec = new IvParameterSpec(Text.IV.getBytes());

		this.keyspec = new SecretKeySpec(Text.KEY.getBytes(), "AES");

		try {
			this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param data
	 *
	 * @return
	 */
	public static String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		}

		int len = data.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16)
				str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
			else
				str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
		}
		return str;
	}

	/**
	 * @param str
	 *
	 * @return
	 */
	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * @param source
	 *
	 * @return
	 */
	private static String padString(String source) {
		char paddingChar = ' ';
		int size = 16;
		int x = source.length() % size;
		int padLength = size - x;

		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}

		return source;
	}

	/**
	 * @return
	 */
	public IvParameterSpec getIvspec() {
		return ivspec;
	}

	/**
	 * @param ivspec
	 */
	public void setIvspec(IvParameterSpec ivspec) {
		this.ivspec = ivspec;
	}

	/**
	 * @return
	 */
	public SecretKeySpec getKeyspec() {
		return keyspec;
	}

	/**
	 * @param keyspec
	 */
	public void setKeyspec(SecretKeySpec keyspec) {
		this.keyspec = keyspec;
	}

	/**
	 * @return
	 */
	public Cipher getCipher() {
		return cipher;
	}

	/**
	 * @param cipher
	 */
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	/**
	 * @param text
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public byte[] encrypt(String text) throws Exception {
		if (text == null || text.length() == 0)
			throw new Exception("Empty string");

		byte[] encrypted = null;

		try {
			this.getCipher().init(Cipher.ENCRYPT_MODE, this.getKeyspec(), this.getIvspec());

			encrypted = this.getCipher().doFinal(padString(text).getBytes());
		} catch (Exception e) {
			throw new Exception("[encrypt] " + e.getMessage());
		}

		return encrypted;
	}

	/**
	 * @param code
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public byte[] decrypt(String code) throws Exception {
		if (code == null || code.length() == 0)
			throw new Exception("Empty string");

		byte[] decrypted = null;

		try {
			this.getCipher().init(Cipher.DECRYPT_MODE, this.getKeyspec(), this.getIvspec());

			decrypted = this.getCipher().doFinal(hexToBytes(code));
		} catch (Exception e) {
			throw new Exception("[decrypt] " + e.getMessage());
		}
		return decrypted;
	}
}