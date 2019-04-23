package menu;

import java.io.Serializable;

import javax.inject.Named;

@Named
@javax.faces.view.ViewScoped
public class mainController implements Serializable {
	
	private static final long serialVersionUID = 3973801993975443037L;
	
	private String primeName;

	public String getPrimeName() {
		return primeName;
	}

	public void setPrimeName(String primeName) {
		this.primeName = primeName;
	}
	
}
