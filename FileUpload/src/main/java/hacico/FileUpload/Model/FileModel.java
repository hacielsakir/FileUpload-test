package hacico.FileUpload.Model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Transient
    private MultipartFile file; // Transient field to hold the uploaded file temporarily.

    // Constructor, getters, and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
