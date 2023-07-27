package hacico.FileUpload.Controller;

import hacico.FileUpload.Model.FileModel;
import hacico.FileUpload.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            fileService.saveFile(file, fileName);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload the file.");
        }
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Resource resource = fileService.downloadFile(filename);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/documents")
    public List<FileModel> getAllDocuments() {
        return fileService.getAllDocuments();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete the file.");
        }
    }
}
