package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.department.DepartmentFilter;

@Repository
public interface DepartmentRepositoryCustom {

    ResultList<Department> getResultList(DepartmentFilter filter);
}
