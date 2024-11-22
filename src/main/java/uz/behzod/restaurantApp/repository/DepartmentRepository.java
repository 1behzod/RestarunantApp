package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.repository.custom.DepartmentRepositoryCustom;


public interface DepartmentRepository extends BaseRepository<Department, Long>, DepartmentRepositoryCustom {
    boolean existsByNameEqualsIgnoreCase(Long branchId, String name);


}
