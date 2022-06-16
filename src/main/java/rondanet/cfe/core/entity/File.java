package rondanet.cfe.core.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] documento;

    public File() {
    }

    public Long getId() {
        return id;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

}