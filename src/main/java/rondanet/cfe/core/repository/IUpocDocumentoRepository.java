package rondanet.cfe.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rondanet.cfe.core.entity.Documento;

public interface IUpocDocumentoRepository extends MongoRepository<Documento, String> {


}
