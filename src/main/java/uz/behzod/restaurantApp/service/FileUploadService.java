package uz.behzod.restaurantApp.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.behzod.restaurantApp.domain.file.FileUpload;
import uz.behzod.restaurantApp.dto.file.FileDTO;
import uz.behzod.restaurantApp.repository.FileUploadRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import static uz.behzod.restaurantApp.dto.file.FileDTO.toFileDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class FileUploadService {

    FileUploadRepository fileUploadRepository;
    private static final String STORAGE_DIRECTORY = "src/main/resources/uploads";

    @Transactional
    public Long save(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();
        Path uploadDir = Paths.get(STORAGE_DIRECTORY);
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(uniqueFileName);
        file.transferTo(filePath);

        FileUpload fileUpload = new FileUpload();
        fileUpload.setOriginalFileName(file.getOriginalFilename());
        fileUpload.setUniqueFileName(uniqueFileName);
        fileUpload.setFileSize(file.getSize());
        fileUpload.setUploadDate(LocalDateTime.now());
        return fileUploadRepository.save(fileUpload).getId();
    }

    public FileDTO get(Long Id) {
        FileUpload fileUpload = fileUploadRepository.findById(Id).orElseThrow(
                () -> new IllegalArgumentException("File not found "));
        return toFileDTO(fileUpload);
    }
}
