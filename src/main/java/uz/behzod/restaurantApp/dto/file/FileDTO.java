package uz.behzod.restaurantApp.dto.file;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.file.FileUpload;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
public class FileDTO extends CommonDTO {

    String originalFileName;

    String uniqueFileName;

    Long fileSize;

    LocalDateTime uploadDate;

    public static FileDTO toFileDTO(FileUpload fileUpload) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setOriginalFileName(fileUpload.getOriginalFileName());
        fileDTO.setUniqueFileName(fileUpload.getUniqueFileName());
        fileDTO.setFileSize(fileUpload.getFileSize());
        fileDTO.setUploadDate(fileUpload.getUploadDate());
        return fileDTO;
    }

}
