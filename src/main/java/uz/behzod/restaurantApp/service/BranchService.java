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
import uz.behzod.restaurantApp.dto.address.AddressDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDto;
import uz.behzod.restaurantApp.dto.branch.BranchDto;
import uz.behzod.restaurantApp.repository.BranchRepository;

import java.util.Optional;

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
            throw new RuntimeException("Company is required");
        }
        if (branchDto.getAddress() == null) {
            throw new RuntimeException("Address is required");
        }
        if (branchDto.getId() != null && branchRepository.existsByNameEqualsIgnoreCase(branchDto.getCompanyId(), branchDto.getName())) {
            throw new RuntimeException("Branch exists by this name: " + branchDto.getName());
        }
    }


    @Transactional
    public Long create(BranchDto branchDto) {
        this.validate(branchDto);
        Branch branch = new Branch();
        branch.setName(branchDto.getName());
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
            Address address = Optional.ofNullable(branch.getAddress()).orElseGet(Address::new);
            address.setRegionId(branchDto.getAddress().getRegionId());
            address.setDistrictId(branchDto.getAddress().getDistrictId());
            address.setStreet(branchDto.getAddress().getStreet());
            branch.setAddress(address);
        }
        return branchRepository.save(branch).getId();
    }

    @Transactional
    public Long delete(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new RuntimeException("Branch not found");
        }
        branchRepository.deleteById(id);
        return id;
    }

    @Transactional
    public BranchDetailDto get(Long id) {
        return branchRepository.findById(id).map(branch -> {
            BranchDetailDto branchDetailDto = new BranchDetailDto();
            branchDetailDto.setId(branch.getId());
            branchDetailDto.setName(branch.getName());
            branchDetailDto.setCompany(branch.getCompany().toCommonDTO());

            if (branch.getAddress() != null) {
                AddressDetailDTO addressDetailDTO = new AddressDetailDTO();
                addressDetailDTO.setRegion(branch.getAddress().getRegion().toCommonDTO());
                addressDetailDTO.setDistrict(branch.getAddress().getDistrict().toCommonDTO());
                addressDetailDTO.setNeighbourhood(branch.getAddress().getNeighbourhood().toCommonDTO());
                addressDetailDTO.setStreet(branch.getAddress().getStreet());
                branchDetailDto.setAddress(addressDetailDTO);
            }
            return branchDetailDto;
        }).orElseThrow(() -> new RuntimeException("Branch not found"));
    }


}
