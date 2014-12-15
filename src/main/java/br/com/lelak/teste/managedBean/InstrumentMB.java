package br.com.lelak.teste.managedBean;

import java.io.File;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.lelak.teste.model.Instrument;
import br.com.lelak.teste.persistence.DAOFactory;
import br.com.lelak.teste.persistence.dao.InstrumentDAO;
import br.com.lelak.teste.util.ExtensionEnum;
import br.com.lelak.teste.util.FileUtils;
import br.com.lelak.teste.util.ImageManager;

@ManagedBean
@ViewScoped
public class InstrumentMB extends Instrument {

	private static final long serialVersionUID = 3896691647663920684L;
	private static final String DEFAULT_IMAGE = "images" + File.separator + "default.png";
	private String tempImageName = DEFAULT_IMAGE;

	public List<Instrument> getList() {
		return instrumentDAO().findAll();
	}

	public void toSave() {
		instrumentDAO().save(this.clone());
		this.reset();
		onPostExecute();
	}

	private InstrumentDAO instrumentDAO() {
		return DAOFactory.createDAO(InstrumentDAO.class);
	}
	
	public void userChangeHandler() throws Exception {
		System.out.println(getUser());
	}

	public void fileUploadHandler(FileUploadEvent event) throws Exception {
		if(!tempImageName.equals(DEFAULT_IMAGE)){
			FileUtils.deleteFromSource(tempImageName);
		}
		byte[] imageAsByte = FileUtils.toByteArray(event);
		setImage(imageAsByte);

		ExtensionEnum extension = ExtensionEnum.fromBytes(imageAsByte);
		String imageName = extension.putExtensionIn("tempImage");
		imageName = FileUtils.createRandomName(imageName);
		ImageManager.writeTempImage(imageAsByte, imageName);
		tempImageName = "temp" + File.separator + imageName;
	}
	

	public String getTempImageName() {
		return tempImageName;
	}

	public void setTempImageName(String tempImageName) {
		this.tempImageName = tempImageName;
	}
	
	private void onPostExecute() {
		RequestContext.getCurrentInstance().update("tabView:formInstrument");
		RequestContext.getCurrentInstance().update("tabView:tableInstrument");
	}
	
	@Override
	protected void reset(){
		super.reset();
		tempImageName = DEFAULT_IMAGE;
	}

}
