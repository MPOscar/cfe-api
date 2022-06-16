package rondanet.cfe.core.services.implementations;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import rondanet.cfe.core.db.DocumentoDAO;
import rondanet.cfe.core.entity.File;
import rondanet.cfe.core.entity.Documento;
import rondanet.cfe.core.services.interfaces.IPostgresFileService;
import rondanet.cfe.core.services.interfaces.IMongoFileService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class MongoFileService implements IMongoFileService {

	Logger logger = LogManager.getLogger(MongoFileService.class);

	@Autowired
	DocumentoDAO documentoDAO;

	@Autowired
	IPostgresFileService postgresFileService;

	private static DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss.SSSZZ");

	private static DateTimeFormatter shortFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");

	public MongoFileService() {
	}

	@Override
	public void guardarXml(MultipartFile file) throws IOException {
		salvarArchivoEnMongo(file);
	}

	public void salvarArchivoComprimidoEnMongo(MultipartFile file) {
		try {
			Document xmlDocument = obtenerDocumentoXml(file);
			ZipOutputStream zip = compressFile(xmlDocument);
			Documento documento = new Documento();
			//documento.setDocumento(new Binary(BsonBinarySubType.BINARY, data));
			documentoDAO.save(documento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void salvarArchivoEnMongo(MultipartFile file) {
		try {
			Document documentoXml = obtenerDocumentoXml(file);
			Documento documento = obtenerDocumentoDelXml(documentoXml);
			byte[] data = file.getBytes();
			documento.setDocumento(new Binary(BsonBinarySubType.BINARY, data));
			documentoDAO.save(documento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public ZipOutputStream compressFile(Document xmlDocument) throws IOException, TransformerException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(baos);
		ZipEntry zipEntry;
		zipEntry = new ZipEntry(xmlDocument.getLocalName() + ".xml");
		zip.putNextEntry(zipEntry);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(bos);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xmlDocument);
		transformer.transform(source, result);
		zip.write(bos.toByteArray());
		zip.closeEntry();
		return zip;
	}

	public void salvarArchivoEnPostgres(byte[] data) {
		File file = new File();
		file.setDocumento(data);
		postgresFileService.save(file);
	}

	public void convertirXmlToString(MultipartFile file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDocument = builder.parse(file.getInputStream());
			convertXmlToString(xmlDocument);
			leerXml(xmlDocument);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document obtenerDocumentoXml(MultipartFile file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDocument = builder.parse(file.getInputStream());
		return xmlDocument;
	}

	private static void convertXmlToString(Document xmlDocument) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
			String xmlString = writer.getBuffer().toString();
			System.out.println(xmlString);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void leerXml(Document xmlDocument) {
		xmlDocument.getDocumentElement().normalize();
		NodeList nodeCodigo = xmlDocument.getElementsByTagName("ns0:IdDoc");
		Node codigo = nodeCodigo.item(0);
		if (codigo.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) codigo;
			System.out.println("Codigo: "+ eElement.getElementsByTagName("ns0:TipoCFE").item(0).getTextContent());
			System.out.println("Serie: "+ eElement.getElementsByTagName("ns0:Serie").item(0).getTextContent());
			System.out.println("Nro: "+ eElement.getElementsByTagName("ns0:Nro").item(0).getTextContent());
			System.out.println("NroInterno: "+ eElement.getElementsByTagName("ns0:NroInterno").item(0).getTextContent());
			System.out.println("FchEmis: "+ eElement.getElementsByTagName("ns0:FchEmis").item(0).getTextContent());
		}
		NodeList nodeEmisor = xmlDocument.getElementsByTagName("ns0:Emisor");
		Node emisor = nodeEmisor.item(0);
		if (emisor.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) emisor;
			System.out.println("RUCEmisor: "+ eElement.getElementsByTagName("ns0:RUCEmisor").item(0).getTextContent());
			System.out.println("RznSoc: "+ eElement.getElementsByTagName("ns0:RznSoc").item(0).getTextContent());
		}

		NodeList nodeReceptor = xmlDocument.getElementsByTagName("ns0:Receptor");
		Node receptor = nodeReceptor.item(0);
		if (receptor.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) receptor;
			System.out.println("RUCReceptor: "+ eElement.getElementsByTagName("ns0:DocRecep").item(0).getTextContent());
			System.out.println("RznSocRecep: "+ eElement.getElementsByTagName("ns0:RznSocRecep").item(0).getTextContent());
		}
	}

	private Documento obtenerDocumentoDelXml(Document xmlDocument) {
		Documento documento = new Documento();
		xmlDocument.getDocumentElement().normalize();
		NodeList nodeResguardo = xmlDocument.getElementsByTagName("ns0:eResg");
		Node resguardo = nodeResguardo.item(0);
		if (resguardo.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) resguardo;
			String fecha = obtenerValorDelCampoDelXml(eElement, "ns0:TmstFirma");
			DateTime fechaFirma = obtenerFecha(fecha, false);
			documento.setFechaFirma(fechaFirma);
		}

		NodeList nodeCodigo = xmlDocument.getElementsByTagName("ns0:IdDoc");
		Node codigo = nodeCodigo.item(0);
		if (codigo.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) codigo;
			String codigoCfe = obtenerValorDelCampoDelXml(eElement, "ns0:TipoCFE");
			String serie = obtenerValorDelCampoDelXml(eElement, "ns0:Serie");
			String numero = obtenerValorDelCampoDelXml(eElement, "ns0:Nro");
			String numeroInterno = obtenerValorDelCampoDelXml(eElement, "ns0:NroInterno");
			String fechaEmision = obtenerValorDelCampoDelXml(eElement, "ns0:FchEmis");

			DateTime fechaEmisionDocumento = obtenerFecha(fechaEmision, true);
			documento.setFechaEmision(fechaEmisionDocumento);

			documento.setDocumentoSerie(serie);
			documento.setCodigoCfe(codigoCfe);
			documento.setDocumentoNumero(numero);
			documento.setDocumentoNumeroInterno(numeroInterno);
			documento.setCodigoCfe(codigoCfe);
			documento.setCodigoCfe(codigoCfe);
			documento.setCodigoCfe(codigoCfe);
			documento.setCodigoCfe(codigoCfe);
		}
		NodeList nodeEmisor = xmlDocument.getElementsByTagName("ns0:Emisor");
		Node emisor = nodeEmisor.item(0);
		if (emisor.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) emisor;
			String emisorRut = obtenerValorDelCampoDelXml(eElement, "ns0:RUCEmisor");
			String emisorRazonSocial = obtenerValorDelCampoDelXml(eElement, "ns0:RznSoc");
			documento.setEmisorRut(emisorRut);
			documento.setEmisorRazonSocial(emisorRazonSocial);
		}

		NodeList nodeReceptor = xmlDocument.getElementsByTagName("ns0:Receptor");
		Node receptor = nodeReceptor.item(0);
		if (receptor.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) receptor;
			String receptorRut = obtenerValorDelCampoDelXml(eElement, "ns0:DocRecep");
			String receptorRazonSocial = obtenerValorDelCampoDelXml(eElement, "ns0:RznSocRecep");
			documento.setReceptorRut(receptorRut);
			documento.setReceptorRazonSocial(receptorRazonSocial);
		}
		return documento;
	}

	public String obtenerValorDelCampoDelXml(Element eElement, String nombreDelCampo) {
		String valor = "";
		try {
			valor = eElement.getElementsByTagName(nombreDelCampo).item(0).getTextContent();
		} catch (DOMException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public DateTime obtenerFecha(String fecha, boolean shortFormat) {
		DateTime dateTime = null;
		try {
			dateTime = shortFormat ? shortFormatter.parseDateTime(fecha) : formatter.parseDateTime(fecha);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return dateTime;
	}
}