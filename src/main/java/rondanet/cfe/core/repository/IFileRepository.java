package rondanet.cfe.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rondanet.cfe.core.entity.File;

@Repository
public interface IFileRepository extends CrudRepository<File, Long> {

}