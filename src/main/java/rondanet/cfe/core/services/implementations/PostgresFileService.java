package rondanet.cfe.core.services.implementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rondanet.cfe.core.entity.File;
import rondanet.cfe.core.repository.IFileRepository;
import rondanet.cfe.core.services.interfaces.IPostgresFileService;

@Service
public class PostgresFileService implements IPostgresFileService {

    @Autowired
    private IFileRepository fileRepository;

    @Override
    public List<File> findAll() {
        var files = (List<File>) fileRepository.findAll();
        return files;
    }

    @Override
    public File save(File file) {
       file = fileRepository.save(file);
       return file;
    }
}