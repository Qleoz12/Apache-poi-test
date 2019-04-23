package validators;

import java.io.File;
import java.io.FileInputStream;
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

@Named
@javax.faces.view.ViewScoped
public class validator implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;
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
            		//create local file
    				File directorio = new File(repositorio);
    				if (!directorio.exists())
    					directorio.mkdirs();
    				File file = new File(nombreArchivo);
    				//write contecn from primeface upload to local file
    				OutputStream out = new FileOutputStream(file);
    				out.write(this.file.getContents());
    				out.close();
    				logger.info("El nombre del archivo cargado es: " + this.file.getFileName());
    				
    				
    				File file2 = new File(file.getAbsolutePath()); 
    				// Obtain a workbook from the excel file
    				Workbook workbook = WorkbookFactory.create(new FileInputStream(file2));
    				Sheet sheet = workbook.getSheetAt(0);
    				if(workbook.getNumberOfSheets()==1 && sheet.getSheetName().equals("cargaMasiva"))
    				{
    					FacesMessage message = new FacesMessage("Succesful", this.file.getFileName() + " is uploaded.");
                        FacesContext.getCurrentInstance().addMessage("messages", message);
    				}
    				else
    				{
    					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on workbook", "dont have a correct structure"));
    					
    				}
    				
    			} catch (Exception e) {
    				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error dev", e.getMessage()));
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
    
    public void checkDocument()
    {
    	logger.info("handleFileUpload init");
    	if(file != null) 
        {
        	enableButtonUpload=true;
        }else
        {
        	enableButtonUpload=false;
        }
        logger.info("ha quedado "+enableButtonUpload);
    }
    
    /***********************************************************************************************************/
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
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

	public Boolean getEnableButtonUpload() {
		return enableButtonUpload;
	}

	public void setEnableButtonUpload(Boolean enableButtonUpload) {
		this.enableButtonUpload = enableButtonUpload;
	}
	 /***********************************************************************************************************/
	
}