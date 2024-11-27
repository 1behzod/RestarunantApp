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
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class DepartmentService extends BaseService {

    DepartmentRepository departmentRepository;


    private void validate(DepartmentDTO departmentDTO) {
        if (!StringUtils.hasLength(departmentDTO.getName())) {
            throw badRequestExceptionThrow("Department name is required").get();
        }
        if (departmentDTO.getBranchId() == null) {
            throw badRequestExceptionThrow("Branch is required").get();
        }
            /*if (departmentDTO.getId() != null && departmentRepository.existsByNameIgnoreCaseAndBranchId( departmentDTO.getName(), departmentDTO.getId())) {
                throw new RuntimeException("Department exists with name " + departmentDTO.getName());
            }*/

        if (departmentDTO.getId() == null) {
            if (departmentRepository.existsByNameIgnoreCaseAndBranchId(departmentDTO.getName(), departmentDTO.getBranchId())) {
                throw conflictExceptionThrow("Department already exists with name " + departmentDTO.getName()).get();
            }
        } else {
            if (departmentRepository.existsByNameIgnoreCaseAndBranchIdAndIdNot(
                    departmentDTO.getName(), departmentDTO.getBranchId(), departmentDTO.getId())) {
                throw conflictExceptionThrow("Department already exists with name " + departmentDTO.getName()).get();
            }
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
        Department department = departmentRepository.findById(id).orElseThrow(notFoundExceptionThrow("Department not found"));
        department.setId(id);
        this.validate(departmentDTO);

        department.setName(departmentDTO.getName());
        department.setBranchId(departmentDTO.getBranchId());
        return departmentRepository.save(department).getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw notFoundExceptionThrow("Department not found with id: " + id).get();
        }
        departmentRepository.deleteById(id);
    }

    public DepartmentDetailDTO get(Long id) {
        return departmentRepository.findById(id).map(department -> {
            DepartmentDetailDTO departmentDetailDto = new DepartmentDetailDTO();
            departmentDetailDto.setId(department.getId());
            departmentDetailDto.setName(department.getName());
            departmentDetailDto.setBranch(department.getBranch().toCommonDTO());
            return departmentDetailDto;
        }).orElseThrow(notFoundExceptionThrow("Department not found"));
    }

    public Page<DepartmentListDTO> getList(BaseFilter filter) {
        ResultList<Department> resultList = departmentRepository.getResultList(filter);

        List<DepartmentListDTO> result = resultList
                .getList()
                .stream()
                .map(department -> {
                    DepartmentListDTO departmentListDTO = new DepartmentListDTO();
                    departmentListDTO.setId(department.getId());
                    departmentListDTO.setName(department.getName());
                    departmentListDTO.setBranch(department.getBranch().toCommonDTO());
                    return departmentListDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }
}



