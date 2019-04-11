package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class excelUtils {

	
	private String getHashProtectedExcel() {
		String mensaje = "miclavesecreta";
		String encriptado = "";
		try {
			MessageDigest security = MessageDigest.getInstance("SHA");
			byte[] hash = security.digest(mensaje.getBytes("UTF-8"));
			encriptado = Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("Error al generar el código HASH: " + e.getCause());
		}
		return encriptado;
	}

	/**
	 * Procedimiento para proteger la hoja de excel con contraseña
	 * 
	 * @author Jorge Leiva
	 * @param hoja
	 *            Instancia de la hoja de excel utilizada
	 * @return Object XSSFSheet con la modificación a la hoja
	 */
	public XSSFSheet protegerHojaExcel(XSSFSheet hoja) {
		hoja.protectSheet(this.getHashProtectedExcel());
		return hoja;
	}
}
