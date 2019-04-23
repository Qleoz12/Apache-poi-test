package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class excelUtils {

	/** Logger for teh class*/
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(excelUtils.class);
	
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
	
	
	/**
	 * Procedimiento para chequear columna de una libro de excel
	 * 
	 * @author Dann Brown
	 * @param pathexcel,column
	 *            Instancia de la hoja de excel utilizada
	 * @return Object XSSFSheet con la modificación a la hoja
	 */
	public void verifyExcelColumnPositive(String filePath_to_excel,String column)
	{
		//custom  save file log 
		 Logger logExcelcolumnError =Logger.getLogger("logExcelcolumnError");
		try {
			File file = new File(filePath_to_excel);
			int minvalue=1000;
			if(file.exists())
			{
				// Obtain a workbook from the excel file
				Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
				
				// Get Sheet at index 0
				Sheet sheet = workbook.getSheetAt(0);
				logger.info("numero de registros del formato es:" + sheet.getPhysicalNumberOfRows());
				
				    //i=1  because title on excel have Zero
					for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) 
					{
						// Get Row at index 1
						Row row = sheet.getRow(i);
						Cell cell = row.getCell(CellReference.convertColStringToIndex(column));
						if(cell.getNumericCellValue()<minvalue)
						{	
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on column "+column, "the value is less than "+minvalue));
							logExcelcolumnError.info("Error on "+cell.getRowIndex()+"- "+cell.getColumnIndex());
						}
					}
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success "+column, "no problems"));	
					 workbook.close();
			}
				
		} catch (Exception e) {
			logger.error("(verifyExcelColumnPositive) No se ha podido ejecutar el proceso: " + e.getCause());
		}

	}
	
}//end class
