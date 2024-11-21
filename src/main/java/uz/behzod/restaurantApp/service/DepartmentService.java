package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.dto.department.DepartmentDTO;
import uz.behzod.restaurantApp.dto.department.DepartmentDetailDTO;
import uz.behzod.restaurantApp.repository.DepartmentRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {

    DepartmentRepository departmentRepository;


    private void validate(DepartmentDTO departmentDTO) {
        if (!StringUtils.hasLength(departmentDTO.getName())) {
            throw new RuntimeException("Department name cannot be empty");
        }
        if (departmentDTO.getBranchId() == null) {
            throw new RuntimeException("Department branchId cannot be null");
        }
        if (departmentRepository.existsByName(departmentDTO.getName())) {
            throw new RuntimeException("Department name must be unique");
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
    public Long delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
        return id;
    }

    @Transactional
    public DepartmentDetailDTO get(Long id) {
        return departmentRepository.findById(id).map(department -> {
            DepartmentDetailDTO departmentDetailDto = new DepartmentDetailDTO();
            departmentDetailDto.setId(department.getId());
            departmentDetailDto.setName(department.getName());
            departmentDetailDto.setBranchId(department.getBranchId());

            return departmentDetailDto;
        }).orElseThrow(() -> new RuntimeException("Department not found"));
    }
}



