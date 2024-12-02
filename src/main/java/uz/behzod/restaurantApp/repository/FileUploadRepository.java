package uz.behzod.restaurantApp.repository;


import uz.behzod.restaurantApp.domain.file.FileUpload;
import uz.behzod.restaurantApp.repository.custom.FileUploadRepositoryCustom;

public interface FileUploadRepository extends BaseRepository<FileUpload, Long>, FileUploadRepositoryCustom {
}
