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
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "key", nullable = false, unique = true)
    String key;

    @NotNull
    @Column(name = "file_size", nullable = false)
    Long fileSize;

    @Column(name = "content_type")
    String contentType;

    @Column(name = "path")
    String path;

    @Column(name = "created_date")
    LocalDateTime createdDate = LocalDateTime.now();
}
