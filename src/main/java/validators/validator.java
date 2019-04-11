package validators;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.NamedEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.faces.view.ViewScoped
public class validator implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;

	private String xlsx_name;
	private int  sheetsCount;
	private int rows;
	private int columns;

	
}