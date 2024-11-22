package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.department.DepartmentDTO;
import uz.behzod.restaurantApp.dto.department.DepartmentDetailDTO;
import uz.behzod.restaurantApp.dto.department.DepartmentListDTO;
import uz.behzod.restaurantApp.filters.DepartmentFilter;
import uz.behzod.restaurantApp.repository.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {

    DepartmentRepository departmentRepository;


    private void validate(DepartmentDTO departmentDTO) {
        if (!StringUtils.hasLength(departmentDTO.getName())) {
            throw new RuntimeException("Department name is required");
        }
        if (departmentDTO.getBranchId() == null) {
            throw new RuntimeException("Branch is required");
        }
        if (departmentDTO.getId() != null && departmentRepository.existsByNameEqualsIgnoreCase(departmentDTO.getId(), departmentDTO.getName())) {
            throw new RuntimeException("Department exists with name " + departmentDTO.getName());
        }
    }

    @Transactional
    public Long create(DepartmentDTO departmentDTO) {
        this.validate(departmentDTO);
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setBranchId(departmentDTO.getBranchId());
        return departmentRepository.save(department).getId();
    }

    @Transactional
    public Long update(DepartmentDTO departmentDTO, Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        department.setId(id);
        this.validate(departmentDTO);

        department.setName(departmentDTO.getName());
        department.setBranchId(departmentDTO.getBranchId());
        return departmentRepository.save(department).getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    public DepartmentDetailDTO get(Long id) {
        return departmentRepository.findById(id).map(department -> {
            DepartmentDetailDTO departmentDetailDto = new DepartmentDetailDTO();
            departmentDetailDto.setId(department.getId());
            departmentDetailDto.setName(department.getName());
            departmentDetailDto.setBranchId(department.getBranchId());

            return departmentDetailDto;
        }).orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public Page<DepartmentListDTO> getList(DepartmentFilter filter) {
        ResultList<Department> resultList = departmentRepository.getResultList(filter);

        List<DepartmentListDTO> result = resultList
                .getList()
                .stream()
                .map(department -> {
                    DepartmentListDTO departmentListDTO = new DepartmentListDTO();
                    departmentListDTO.setId(department.getId());
                    departmentListDTO.setName(department.getName());
                    departmentListDTO.setBranchId(department.getBranchId());
                    return departmentListDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }
}



