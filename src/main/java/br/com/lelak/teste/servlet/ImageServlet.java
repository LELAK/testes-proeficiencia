package br.com.lelak.teste.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.lelak.teste.persistence.DAOFactory;
import br.com.lelak.teste.persistence.dao.InstrumentDAO;
import br.com.lelak.teste.util.ExtensionEnum;
import br.com.lelak.teste.util.FileUtils;
import br.com.lelak.teste.util.ImageManager;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private byte[] bytes;
       
    public ImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String instrumentId = request.getParameter("id");
		if(instrumentId != null && !instrumentId.trim().isEmpty()){
			bytes = instrumentDAO().findImageById(Long.parseLong(instrumentId));
		} 
		if(bytes == null){
			bytes = getDefaultImage();
		}
		ExtensionEnum extension = ExtensionEnum.fromBytes(bytes);
		response.setContentType(extension.getMimeType());
		ImageManager.writeOutputStream(response.getOutputStream(), bytes);
	}
	
	private byte[] getDefaultImage() throws IOException {
		String filename = getClass().getClassLoader().getResource("images/default.png").getFile();
		return FileUtils.toByteArray(filename);
	}

	private InstrumentDAO instrumentDAO() {
		return DAOFactory.createDAO(InstrumentDAO.class);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
