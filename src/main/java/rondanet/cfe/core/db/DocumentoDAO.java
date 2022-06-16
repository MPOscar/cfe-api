package rondanet.cfe.core.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import rondanet.cfe.core.entity.Documento;
import rondanet.cfe.core.repository.IUpocDocumentoRepository;

@Component
public class DocumentoDAO {

	Logger logger = LogManager.getLogger(DocumentoDAO.class);

	@Autowired
	IUpocDocumentoRepository cfeDocumentoRepository;

	private final MongoOperations mongoOperations;

	public DocumentoDAO(@Qualifier("mongoTemplateUpoc") MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public Documento save(Documento cfeDocumento) {
		cfeDocumento = cfeDocumentoRepository.save(cfeDocumento);
		if (cfeDocumento.getSId() == null){
			cfeDocumento.setSId(cfeDocumento.getId());
			cfeDocumento = cfeDocumentoRepository.save(cfeDocumento);
		}
		return cfeDocumento;
	}

}