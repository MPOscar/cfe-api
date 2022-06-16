package rondanet.cfe.core.services.interfaces;

import rondanet.cfe.core.entity.File;

import java.util.List;

public interface IPostgresFileService {

    List<File> findAll();

    File save(File city);
}