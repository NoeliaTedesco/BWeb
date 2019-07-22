package context;

import javax.xml.bind.annotation.XmlRootElement;

import com.poiji.annotation.ExcelCell;


public class ArchivoExcel {

	@ExcelCell(value = 0)
	public String NombrePagina;

	@ExcelCell(value = 1)
	public String UrlPagina;

	@ExcelCell(value = 2)
	public String Solapa;

	@ExcelCell(value = 3)
	public String idContenido;

	@ExcelCell(value = 4)
	public String tipo;

	@ExcelCell(value = 5)
	public String Url;

	
	public ArchivoExcel() {

	}

	public ArchivoExcel(String UrlPagina, String NombrePagina, String idContenido, String tipo, String Url) {
		this.NombrePagina = NombrePagina;
		this.UrlPagina = UrlPagina;
		this.idContenido = idContenido;
		this.tipo = tipo;
		this.Url = Url;
	}

	public String getSolapa() {
		return Solapa;
	}

	public String getNombrePagina() {
		return NombrePagina;
	}

	public String getIdContenido() {
		return idContenido;
	}

	public String getTipo() {
		return tipo;
	}

	public String getUrlPagina() {
		return UrlPagina;
	}

	public String getUrl() {
		return Url;
	}


}
