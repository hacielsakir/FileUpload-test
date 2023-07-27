package hacico.FileUpload.Service;

import hacico.FileUpload.Model.FileModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface FileService {
    void saveFile(MultipartFile file, String fileName) throws IOException;
    Resource downloadFile(String filename) throws IOException;
    List<FileModel> getAllDocuments();
    void deleteFile(Long id) throws IOException;
}
