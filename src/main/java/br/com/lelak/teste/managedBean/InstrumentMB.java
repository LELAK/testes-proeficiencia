package br.com.lelak.teste.managedBean;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import br.com.lelak.teste.model.Instrument;
import br.com.lelak.teste.model.User;
import br.com.lelak.teste.util.ExtensionEnum;
import br.com.lelak.teste.util.FileUtils;
import br.com.lelak.teste.util.ImageManager;

@ManagedBean
@ViewScoped
public class InstrumentMB extends AbstractManagedBean<Instrument> {


	private static final long serialVersionUID = 3896691647663920684L;
	private static final String DEFAULT_IMAGE = "images" + File.separator + "default.png";
	private String tempImageFilename = DEFAULT_IMAGE;
	private Long userId;
	
	@ManagedProperty(value = "#{instrument}")
	private Instrument instrumentForm;

	public String getTempImageFilename() {
		return tempImageFilename;
	}

	public void setTempImageFilename(String tempImageName) {
		this.tempImageFilename = tempImageName;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Instrument getInstrumentForm() {
		return instrumentForm;
	}

	public void setInstrumentForm(Instrument instrumentForm) {
		this.instrumentForm = instrumentForm;
	}
	

	@Override
	public List<Instrument> getList() {
		return instrumentDAO().findAll();
	}

	@Override
	public void toSave() {
		Instrument clone = instrumentForm.clone();
		if(userId != null){
			User user = userDAO().findById(userId);
			clone.setUser(user);
		}
		
		instrumentDAO().save(clone);
		
		toReset();
	}

	@Override
	public void toReset() {
		userId = null;
		tempImageFilename = DEFAULT_IMAGE;
		instrumentForm.reset();
		updateInstrumentView();
	}
	
	public void fileUploadHandler(FileUploadEvent event) throws Exception {
		if(!tempImageFilename.equals(DEFAULT_IMAGE)){
			deleteOldTempImage();
		}
		byte[] imageAsByte = FileUtils.toByteArray(event);
		instrumentForm.setImage(imageAsByte);

		tempImageFilename = writeTempImage(imageAsByte);
	}

	private void deleteOldTempImage() {
		FileUtils.deleteFromSource(tempImageFilename);
	}

	private String writeTempImage(byte[] imageAsByte) throws IOException {
		ExtensionEnum extension = ExtensionEnum.fromBytes(imageAsByte);
		String imageName = extension.putExtensionIn("tempImage");
		imageName = FileUtils.createRandomName(imageName);
		ImageManager.writeTempImage(imageAsByte, imageName);
		return "temp" + File.separator + imageName;
	}
}
