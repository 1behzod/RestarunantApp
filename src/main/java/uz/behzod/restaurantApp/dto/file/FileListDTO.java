package uz.behzod.restaurantApp.dto.file;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
public class FileListDTO extends CommonDTO {

    Long fileSize;

    String contentType;

    String path;
}
