package uz.behzod.restaurantApp.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.behzod.restaurantApp.domain.file.FileUpload;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.file.FileDTO;
import uz.behzod.restaurantApp.dto.file.FileListDTO;
import uz.behzod.restaurantApp.filters.FileFilter;
import uz.behzod.restaurantApp.repository.FileUploadRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class FileUploadService extends BaseService {

    FileUploadRepository fileUploadRepository;
    private static final String STORAGE_DIRECTORY = "src/main/resources/uploads";

    @Transactional
    public Long upload(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        Path uploadDir = Paths.get(STORAGE_DIRECTORY);
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(key);
        file.transferTo(filePath);

        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(file.getOriginalFilename());
        fileUpload.setKey(key);
        fileUpload.setFileSize(file.getSize());
        fileUpload.setPath(filePath.toString());
        return fileUploadRepository.save(fileUpload).getId();
    }

    @Transactional
    public void delete(Long id) {
        FileUpload fileUpload = fileUploadRepository.findById(id).orElseThrow(() -> notFoundExceptionThrow(ENTITY_NOT_FOUND, FILE).get());
        File file = new File(fileUpload.getPath());
        if (file.exists()) {
            file.delete();
            fileUploadRepository.deleteById(id);
        } else {
            throw notFoundExceptionThrow(ENTITY_NOT_FOUND, FILE).get();
        }
    }

    public FileDTO get(Long id) {
        return fileUploadRepository.findById(id)
                .map(fileUpload -> {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setId(fileUpload.getId());
                    fileDTO.setName(fileUpload.getName());
                    fileDTO.setFileSize(fileUpload.getFileSize());
                    fileDTO.setContentType(fileUpload.getContentType());
                    fileDTO.setPath(fileUpload.getPath());
                    return fileDTO;
                }).orElseThrow(() -> notFoundExceptionThrow(ENTITY_NOT_FOUND, FILE).get());
    }

    public Page<FileListDTO> getList(FileFilter filter) {
        ResultList<FileUpload> resultList = fileUploadRepository.getResultList(filter);
        List<FileListDTO> result = resultList
                .getList()
                .stream()
                .map(fileUpload -> {
                    FileListDTO fileListDTO = new FileListDTO();
                    fileListDTO.setId(fileUpload.getId());
                    fileListDTO.setName(fileUpload.getName());
                    fileListDTO.setFileSize(fileUpload.getFileSize());
                    fileListDTO.setContentType(fileUpload.getContentType());
                    fileListDTO.setPath(fileUpload.getPath());
                    return fileListDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }

    public ResponseEntity<byte[]> download(Long id) throws IOException {
        FileUpload fileUpload = fileUploadRepository.findById(id).orElseThrow(() -> notFoundExceptionThrow(ENTITY_NOT_FOUND, FILE).get());
        Path path = Paths.get(fileUpload.getPath());
        if (!Files.exists(path)) {
            throw notFoundExceptionThrow(ENTITY_NOT_FOUND, FILE).get();
        }

        byte[] fileContent = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileUpload.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileContent.length));

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
