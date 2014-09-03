package org.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.parsers.IEEEParser;
import org.parsers.PatentParser;
import org.parsers.PubmedParser;
import org.utils.jaxb.Article;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SwitchBoard {

	public static Collection<Article> generateArticlesByStream(
			InputStream inputStream, String name) {

		Collection<Article> collections = new ArrayList<Article>();
		if (name.contains("patent")) {
			PatentParser patentParser = new PatentParser(inputStream);
			patentParser.parse();
			collections.addAll(patentParser.getArticle());
		} else if (name.contains("pubmed")) {
			XMLReader xr;
			try {
				xr = XMLReaderFactory.createXMLReader();
				PubmedParser handler = new PubmedParser();
				xr.setContentHandler(handler);
				xr.setErrorHandler(handler);
				xr.parse(new InputSource(inputStream));
				collections.addAll(handler.getArtciles());
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (name.contains("ieee")) {
			XMLReader xr;
			try {
				xr = XMLReaderFactory.createXMLReader();
				IEEEParser handler = new IEEEParser();
				xr.setContentHandler(handler);
				xr.setErrorHandler(handler);
				xr.parse(new InputSource(inputStream));
				collections.addAll(handler.getArtciles());
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return collections;

	}

	public static Collection<Article> generateArticlesByZip(
			InputStream inputStream) {

		ZipInputStream zis = new ZipInputStream(inputStream);
		Collection<Article> collections = new ArrayList<Article>();
		ZipEntry ze;
		try {

			while ((ze = zis.getNextEntry()) != null) {
				String name = ze.getName();
				if (name.contains("patent")) {
					PatentParser patentParser = new PatentParser(zis);
					patentParser.parse();
					collections.addAll(patentParser.getArticle());
				} else if (name.contains("pubmed")) {
					XMLReader xr;
					try {
						xr = XMLReaderFactory.createXMLReader();
						PubmedParser handler = new PubmedParser();
						xr.setContentHandler(handler);
						xr.setErrorHandler(handler);
						xr.parse(new InputSource(zis));
						collections.addAll(handler.getArtciles());
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (name.contains("ieee")) {
					XMLReader xr;
					try {
						xr = XMLReaderFactory.createXMLReader();
						IEEEParser handler = new IEEEParser();
						xr.setContentHandler(handler);
						xr.setErrorHandler(handler);
						xr.parse(new InputSource(zis));
						collections.addAll(handler.getArtciles());
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Done");

		return collections;

	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(
				"/Users/weiweihou/Desktop/pubmedservice/patent/testFolder/patent.json");
		FileInputStream fileStream = new FileInputStream(file);
		ArrayList<Article> articles = (ArrayList<Article>) SwitchBoard
				.generateArticlesByStream(fileStream, file.getName());
		System.out.print(articles.get(0).getArticleMeta().getTitle());

	}
}
