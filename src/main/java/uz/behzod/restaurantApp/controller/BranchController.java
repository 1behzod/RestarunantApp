package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDTO;
import uz.behzod.restaurantApp.dto.branch.BranchListDTO;
import uz.behzod.restaurantApp.filters.branch.BranchFilter;
import uz.behzod.restaurantApp.service.BranchService;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BranchController {

    BranchService branchService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody BranchDTO branchDto) {
        return ResponseEntity.ok(branchService.create(branchDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@RequestBody BranchDTO branchDto, @PathVariable Long id) {
        return ResponseEntity.ok(branchService.update(id, branchDto));
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
    public ResponseEntity<Page<BranchListDTO>> getList(BranchFilter filter) {
        return ResponseEntity.ok(branchService.getList(filter));

    }
}
