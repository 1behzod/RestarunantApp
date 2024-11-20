package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.address.Address;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.dto.address.AddressDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDto;
import uz.behzod.restaurantApp.repository.BranchRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BranchService {

    BranchRepository branchRepository;

    public void validate(BranchDto branchDto) {

        if (!StringUtils.hasLength(branchDto.getName())) {
            throw new RuntimeException("Name field is required");
        }
        if (branchDto.getCompanyId() == null) {
            throw new RuntimeException("Branch must be associated with a valid company");
        }
        if (branchDto.getAddress() == null) {
            throw new RuntimeException("Branch address is required");
        }
        if (branchDto.getId() != null && branchRepository.existsByCompanyId(branchDto.getCompanyId())) {
            throw new RuntimeException("Branch already exists");
        }
    }


    @Transactional
    public Long create(BranchDto branchDto) {
        this.validate(branchDto);
        Branch branch = new Branch();
        branch.setName(branchDto.getName());
        branch.setId(branchDto.getId());
        branch.setCompanyId(branchDto.getCompanyId());

        if (branchDto.getAddress() != null) {
            Address address = new Address();
            address.setRegionId(branchDto.getAddress().getRegionId());
            address.setDistrict(branchDto.getAddress().getDistrictId());
            address.setNeighbourhoodId(branchDto.getAddress().getNeighbourhoodId());
            address.setStreet(branch.getAddress().getStreet());
            branch.setAddress(address);
        }
        return branchRepository.save(branch).getId();
    }

    @Transactional
    public Long update(BranchDto branchDto, Long id) {
        Branch branch = branchRepository.findById(branchDto.getId()).orElseThrow(() -> new RuntimeException("Branch not found"));
        branchDto.setId(id);
        this.validate(branchDto);

        branch.setName(branchDto.getName());
        branch.setCompanyId(branchDto.getCompanyId());

        if (branchDto.getAddress() != null) {
            Address address = new Address();
            address.setRegionId(branchDto.getAddress().getRegionId());
            address.setDistrictId(branchDto.getAddress().getDistrictId());
            address.setStreet(branchDto.getAddress().getStreet());
            branch.setAddress(address);
        }
        return branchRepository.save(branch).getId();
    }

    @Transactional
    public void delete(Branch branch) {
        if (!branchRepository.existsById(branch.getId())) {
            throw new RuntimeException("Branch not found");
        }
        branchRepository.delete(branch);
    }

    public BranchDto get(Long id) {}


}
