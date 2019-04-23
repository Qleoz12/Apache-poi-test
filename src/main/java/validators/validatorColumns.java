package validators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import utils.excelUtils;

@Named
@javax.faces.view.ViewScoped
public class validatorColumns implements Serializable  {
	
	private static final long serialVersionUID = 3973801993975443047L;
	/*********************************/
	/** Logger de la clase */
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(validator.class);
	/*******************************/
	private String xlsx_name;
	private int  sheetsCount;
	private int rows;
	private int columns;
	/*******************************/
	private Boolean enableButtonUpload=false;
	private UploadedFile file=null;
	
	public void upload() {
    	String repositorio = "C:\\tmp\\cargasmasivas_check\\";
    	excelUtils excelUtils= new excelUtils();
    	Set<String> extensions = new HashSet<String>();
    	extensions.add("xls");
    	extensions.add("xlsx");
        if(file != null && file.getSize()>0 ) 
        {	
        	String nombre = this.file.getFileName().substring(0, this.file.getFileName().lastIndexOf("."));
			String extension = this.file.getFileName().substring(this.file.getFileName().lastIndexOf("."));
			Random valorAleatorio = new Random();
			int valorAleatorioObtenido = valorAleatorio.nextInt();
			String nombreArchivo = repositorio + nombre + "_" + valorAleatorioObtenido + extension;
        	if(extensions.contains(extension.substring(1)))
        	{	
        		try {
					File file = new File(nombreArchivo);
	        		//write content from primeface upload to local file
					OutputStream out= new FileOutputStream(file);
					out.write(this.file.getContents());
					out.close();
					excelUtils.verifyExcelColumnPositive(file.getAbsolutePath(),"C");
				} catch (IOException e) 
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}else
        	{
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error file","the file extension isnt correct"));
        	}
			
    		}else{
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error file","the file is null"));
    		}
    }
     
    
    public void handleFileUpload(FileUploadEvent event) {
    	logger.info("handleFileUpload init");
    	FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage("messages", msg);
        
    }

    /******************************************************/
	public String getXlsx_name() {
		return xlsx_name;
	}


	public void setXlsx_name(String xlsx_name) {
		this.xlsx_name = xlsx_name;
	}


	public int getSheetsCount() {
		return sheetsCount;
	}


	public void setSheetsCount(int sheetsCount) {
		this.sheetsCount = sheetsCount;
	}


	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public int getColumns() {
		return columns;
	}


	public void setColumns(int columns) {
		this.columns = columns;
	}


	public UploadedFile getFile() {
		return file;
	}


	public void setFile(UploadedFile file) {
		this.file = file;
	}
    
	/******************************************************/ 
    
}
