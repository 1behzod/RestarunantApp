package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.Department;


public interface DepartmentRepository extends BaseRepository<Department, Long> {
    boolean existsByName(String name);
}
