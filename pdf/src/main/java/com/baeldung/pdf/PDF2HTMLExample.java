package com.baeldung.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PDF2HTMLExample {

	private static final String PDF = "pdf.pdf";
	private static final String HTML = "html.html";

	public static void main(String[] args) {
		try {
			//generateHTMLFromPDF(PDF);
			generatePDFFromHTML(HTML);
		} catch (IOException | ParserConfigurationException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private static void generateHTMLFromPDF(String pdfFileName) throws ParserConfigurationException, IOException {
		PDDocument pdf = PDDocument.load(getFile(pdfFileName));
		PDFDomTree parser = new PDFDomTree();
		Writer output = new PrintWriter("pdf.html", "utf-8");
		parser.writeText(pdf, output);
		output.close();
		if (pdf != null) {
			pdf.close();
		}
	}

	private static void generatePDFFromHTML(String htmlFileName) throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(newFile("html.pdf")));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(getFile(htmlFileName)));
		document.close();
	}

	private static File getFile(String fileName) {
		ClassLoader classLoader = PDF2HTMLExample.class.getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}

	private static File newFile(String fileName) {
		ClassLoader classLoader = PDF2HTMLExample.class.getClassLoader();
		String dir = classLoader.getResource("").getFile();
		return new File(dir, fileName);
	}
}
