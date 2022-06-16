package rondanet.cfe.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import common.rondanet.catalogo.core.entity.Entidad;
import common.rondanet.pedidos.core.utils.serializer.CustomDateTimeDeserializer;
import common.rondanet.pedidos.core.utils.serializer.CustomDateTimeSerializer;
import org.bson.types.Binary;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Documento")
@CompoundIndexes({
        @CompoundIndex(name = "mensajeFechaHora_emisorRut", def = "{ 'mensajeFechaHora': 1, 'emisorRut': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_receptorRut", def = "{ 'mensajeFechaHora': 1, 'receptorRut': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_emisorRut_documentoTipo", def = "{ 'mensajeFechaHora': 1, 'emisorRut': 1, 'documentoTipo': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_receptorRut_documentoTipo", def = "{ 'mensajeFechaHora': 1, 'receptorRut': 1, 'documentoTipo': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_emisorRut_documentoTipo", def = "{ 'mensajeFechaHora': 1, 'emisorRut': 1, 'documentoTipo': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_receptorRut_documentoTipo", def = "{ 'mensajeFechaHora': 1, 'receptorRut': 1, 'documentoTipo': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_emisorRut_receptorRut_documentoTipo", def = "{ 'mensajeFechaHora': 1, 'emisorRut': 1, 'receptorRut': 1, 'documentoTipo': 1 }"),
        @CompoundIndex(name = "mensajeFechaHora_emisorRut_receptorRut", def = "{ 'mensajeFechaHora': 1,  'emisorRut': 1, 'receptorRut': 1 }"),
})
public class Documento extends Entidad {

    @Indexed(direction = IndexDirection.ASCENDING)
    private String emisorRut;

    private String emisorRazonSocial;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String receptorRut;

    private String receptorRazonSocial;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String documentoTipo;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String documentoSerie;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String documentoNumero;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String documentoNumeroInterno;

    private String codigoCfe;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime fechaFirma;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime fechaEmision;

    private Binary documento;

    public Documento() {
    }

    public String getEmisorRut() {
        return emisorRut;
    }

    public void setEmisorRut(String emisorRut) {
        this.emisorRut = emisorRut;
    }

    public String getEmisorRazonSocial() {
        return emisorRazonSocial;
    }

    public void setEmisorRazonSocial(String emisorRazonSocial) {
        this.emisorRazonSocial = emisorRazonSocial;
    }

    public String getReceptorRut() {
        return receptorRut;
    }

    public void setReceptorRut(String receptorRut) {
        this.receptorRut = receptorRut;
    }

    public String getReceptorRazonSocial() {
        return receptorRazonSocial;
    }

    public void setReceptorRazonSocial(String receptorRazonSocial) {
        this.receptorRazonSocial = receptorRazonSocial;
    }

    public String getDocumentoTipo() {
        return documentoTipo;
    }

    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
    }

    public String getDocumentoSerie() {
        return documentoSerie;
    }

    public void setDocumentoSerie(String documentoSerie) {
        this.documentoSerie = documentoSerie;
    }

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public String getDocumentoNumeroInterno() {
        return documentoNumeroInterno;
    }

    public void setDocumentoNumeroInterno(String documentoNumeroInterno) {
        this.documentoNumeroInterno = documentoNumeroInterno;
    }

    public String getCodigoCfe() {
        return codigoCfe;
    }

    public void setCodigoCfe(String codigoCfe) {
        this.codigoCfe = codigoCfe;
    }

    public DateTime getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(DateTime fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public DateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(DateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Binary getDocumento() {
        return documento;
    }

    public void setDocumento(Binary documento) {
        this.documento = documento;
    }
}
