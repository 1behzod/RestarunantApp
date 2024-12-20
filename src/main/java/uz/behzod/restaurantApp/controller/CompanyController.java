package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.company.CompanyDTO;
import uz.behzod.restaurantApp.dto.company.CompanyDetailDTO;
import uz.behzod.restaurantApp.dto.company.CompanyListDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.CompanyService;


@RestController
@RequestMapping("/v1/companies")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompanyController {

    CompanyService companyService;


    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.create(companyDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.update(id, companyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<CompanyListDTO>> getList(@ParameterObject BaseFilter filter) {
        return ResponseEntity.ok(companyService.getList(filter));
    }

}
