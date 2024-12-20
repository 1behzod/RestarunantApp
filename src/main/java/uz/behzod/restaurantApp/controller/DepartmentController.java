package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.department.DepartmentDTO;
import uz.behzod.restaurantApp.dto.department.DepartmentDetailDTO;
import uz.behzod.restaurantApp.dto.department.DepartmentListDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.DepartmentService;

@RestController
@RequestMapping("/v1/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.create(departmentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@RequestBody DepartmentDTO departmentDTO, @PathVariable Long id) {
        return ResponseEntity.ok(departmentService.update(departmentDTO, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentListDTO>> getList(@ParameterObject BaseFilter filter) {
        return ResponseEntity.ok(departmentService.getList(filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.ok().build();
    }


}