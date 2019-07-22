package context;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.poiji.annotation.ExcelCell;


@XmlRootElement
public class Usuario {
	
	
	private String usser;
	private String password;
	
	public Usuario(String usser, String password) {
		this.usser = usser;
		this.password = password;
	}
	
	public Usuario() {
		
	}
	
	public String getUsser() {
		return usser;
	}
	
	@XmlElement
	public void setUsser(String usser) {
		this.usser = usser;
	}
	
	public String getPassword() {
		return password;
	}
	

	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}

