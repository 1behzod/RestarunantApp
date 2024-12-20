package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.behzod.restaurantApp.dto.file.FileDTO;
import uz.behzod.restaurantApp.dto.file.FileListDTO;
import uz.behzod.restaurantApp.filters.FileFilter;
import uz.behzod.restaurantApp.service.FileUploadService;

import java.io.IOException;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileUploadController {

    FileUploadService fileUploadService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileUploadService.upload(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(fileUploadService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<FileListDTO>> getList(@ParameterObject FileFilter filter) {
        return ResponseEntity.ok(fileUploadService.getList(filter));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws IOException {
        return fileUploadService.download(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fileUploadService.delete(id);
        return ResponseEntity.ok().build();
    }
}
