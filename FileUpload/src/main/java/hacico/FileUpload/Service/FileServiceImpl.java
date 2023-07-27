package hacico.FileUpload.Service;

import hacico.FileUpload.Model.FileModel;
import hacico.FileUpload.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Value("${file.upload.directory}")
    private String UPLOAD_DIR;

    @Override
    public void saveFile(MultipartFile file, String fileName) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        FileModel fileModel = new FileModel();
        fileModel.setName(fileName);
        fileRepository.save(fileModel);
    }

    @Override
    public Resource downloadFile(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
        Resource resource = new FileSystemResource(filePath.toFile());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("File not found or not readable.");
        }
    }

    @Override
    public List<FileModel> getAllDocuments() {
        return fileRepository.findAll();
    }

    @Override
    public void deleteFile(Long id) throws IOException {
        FileModel fileModel = fileRepository.findById(id).orElse(null);
        if (fileModel != null) {
            fileRepository.delete(fileModel);
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileModel.getName());
            Files.delete(filePath);
        } else {
            throw new RuntimeException("File not found with ID: " + id);
        }
    }
}
