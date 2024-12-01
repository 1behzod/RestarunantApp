package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.behzod.restaurantApp.dto.file.FileDTO;
import uz.behzod.restaurantApp.service.FileUploadService;

import java.io.IOException;

@RestController
@RequestMapping("/uploads")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FileUploadController {

    FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileUploadService.save(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(fileUploadService.get(id));
    }
}
