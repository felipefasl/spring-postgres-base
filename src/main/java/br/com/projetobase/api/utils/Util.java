package br.com.projetobase.api.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.PathVariable;

public class Util {

	/**
	 * @author Felipe
	 */
	public static String removeAcento(String str) {
	    str = Normalizer.normalize(str, Normalizer.Form.NFD);
	    str = str.replaceAll("[^\\p{ASCII}]", "");
	    return str;
	}

	/**
	 * @author Felipe
	 */
	public static byte[] redimensionarImagem(@PathVariable("dataInicial") byte[] foto) throws IOException {
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(foto));

		BufferedImage ret = (BufferedImage) img;
		int largura, altura;
		int larguraFoto, alturaFoto;

		larguraFoto = img.getWidth();
		alturaFoto = img.getHeight();
		largura = 320;
		altura = 240;

		if (larguraFoto > largura || alturaFoto > altura) {
			BufferedImage tmp = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
			Graphics2D g3 = tmp.createGraphics();
			g3.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g3.drawImage(ret, 0, 0, largura, altura, null);
			g3.dispose();
			ret = tmp;
			ByteArrayOutputStream fotoArray = new ByteArrayOutputStream();
			ImageIO.write(ret, "jpg", fotoArray);
			fotoArray.flush();
			return fotoArray.toByteArray();
		} else {
			return foto;
		}
	}
	
	/**
	 * @author Felipe
	 */
	public static String formatarCPF(String cpf) {		
		String bloco1 = cpf.substring(0, 3);
		String bloco2 = cpf.substring(3, 6);
		String bloco3 = cpf.substring(6, 9);
		String bloco4 = cpf.substring(9, 11);
		return bloco1+"."+bloco2+"."+bloco3+"-"+bloco4;
	}
}
