package br.com.lelak.teste.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageManager {

	public static void writeTempImage(byte[] bytes, String imageName) throws IOException {
		String tempImageFilename = FileUtils.getTempFilename(imageName);
		writeImage(bytes, tempImageFilename);
	}

	public static void writeImage(byte[] bytes, String filename) throws IOException {
		FileUtils.mkDir(filename);
		ExtensionEnum extension = ExtensionEnum.fromBytes(bytes);
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
		ImageIO.write(bufferedImage, extension.getExtension(), new File(filename));
	}


	public static void writeOutputStream(OutputStream outputStream, byte[] bytes) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
		ExtensionEnum extension = ExtensionEnum.fromBytes(bytes);
		ImageIO.write(bufferedImage, extension.getExtension(), outputStream);
	}

}
