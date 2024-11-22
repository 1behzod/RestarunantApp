package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDto;
import uz.behzod.restaurantApp.dto.branch.BranchDto;
import uz.behzod.restaurantApp.service.BranchService;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BranchController {

    BranchService branchService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody BranchDto branchDto) {
        return ResponseEntity.ok(branchService.create(branchDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@RequestBody BranchDto branchDto, @PathVariable Long id) {
        return ResponseEntity.ok(branchService.update(branchDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDetailDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.get(id));
    }
}
