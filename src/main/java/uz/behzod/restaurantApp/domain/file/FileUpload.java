package uz.behzod.restaurantApp.domain.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.SimpleEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "file_upload")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUpload extends SimpleEntity {

    @NotNull
    @Column(name = "original_name", nullable = false)
    String originalFileName;

    @NotNull
    @Column(name = "unique_file_name", nullable = false, unique = true)
    String uniqueFileName;

    @NotNull
    @Column(name = "file_size", nullable = false)
    Long fileSize;

    @NotNull
    @Column(name = "upload_date", nullable = false)
    LocalDateTime uploadDate;
}
