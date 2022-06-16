package rondanet.cfe.core.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IMongoFileService {

    public abstract void guardarXml(MultipartFile file) throws IOException;

}
