package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.branch.BranchDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchListDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.BranchService;

@RestController
@RequestMapping("/v1/branches")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BranchController {

    BranchService branchService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.create(branchDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@RequestBody BranchDTO branchDTO, @PathVariable Long id) {
        return ResponseEntity.ok(branchService.update(id, branchDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<BranchListDTO>> getList(@ParameterObject BaseFilter filter) {
        return ResponseEntity.ok(branchService.getList(filter));

    }
}
