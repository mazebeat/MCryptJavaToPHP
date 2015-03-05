package cl.mazebeat.main;

import cl.mazebeat.beans.MCrypt;
import cl.mazebeat.beans.MessageUtils;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * MCrypt Java to PHP / PHP to Java Class
 */
public class MCryptJavaToPHP {

	public static void main(String[] args) {
		String encrypted;
		String decrypted;

		MCrypt mcrypt = new MCrypt();

		/* Encrypt */
		try {
			encrypted = MCrypt.bytesToHex(mcrypt.encrypt("1"));
			MessageUtils.info(encrypted);
		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
		}

		/* Decrypt */
		try {
			decrypted = new String(mcrypt.decrypt("cd68c63e82f1bb7a2f57a8d2ea72dc77"));
			MessageUtils.info(decrypted);
		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
		}

		//
		try {
			String baseurl = "http://www.amicar.cl/?";

			HashMap<String, String> params = new HashMap<String, String>();
			//String value stored along with the key value in hash map
			params.put("cliente", MCrypt.bytesToHex(mcrypt.encrypt("1")));
			params.put("cotizacion", MCrypt.bytesToHex(mcrypt.encrypt("2")));
			params.put("campana", MCrypt.bytesToHex(mcrypt.encrypt("camara")));

			System.out.println(fullURL(baseurl, params));

		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
		}
	}

	public static String fullURL(String urlBase, HashMap<String, String> params) {
		for (Entry<String, String> entry : params.entrySet()) {
			String param = entry.getKey() + "=" + entry.getValue() + "&";
			urlBase = urlBase.concat(param);
		}

		if (urlBase.endsWith("&")) {
			urlBase = urlBase.substring(0, urlBase.length() - 1);
		}

		return urlBase;
	}
}
