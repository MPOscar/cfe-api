package rondanet.cfe.api;

import common.rondanet.catalogo.core.resources.Representacion;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import rondanet.cfe.core.security.IAuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import rondanet.cfe.core.services.interfaces.IMongoFileService;

import javax.ws.rs.WebApplicationException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

  Logger logger = LogManager.getLogger(DocumentoController.class);

  private final IAuthenticationFacade authenticationFacade;

  @Autowired
  IMongoFileService fileService;

  public DocumentoController(IAuthenticationFacade authenticationFacade) {
    this.authenticationFacade = authenticationFacade;
  }

  @PostMapping("")
  public ResponseEntity saveFileForBussisnes(@RequestParam("file") MultipartFile file) {
    try {
      this.fileService.guardarXml(file);
      return ok(new Representacion<String>(HttpStatus.OK.value(), "salvado"));
    } catch (Exception ex) {
      logger.log(Level.ERROR, "file controller @PostMapping(\"/empresa\") Error:", ex.getMessage(), ExceptionUtils.getStackTrace(ex));
      throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }


}