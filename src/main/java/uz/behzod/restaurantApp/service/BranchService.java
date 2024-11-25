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
import uz.behzod.restaurantApp.domain.address.Address;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.dto.address.AddressDetailDTO;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDTO;
import uz.behzod.restaurantApp.dto.branch.BranchListDTO;
import uz.behzod.restaurantApp.filters.branch.BranchFilter;
import uz.behzod.restaurantApp.repository.BranchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class BranchService {

    BranchRepository branchRepository;

    public void validate(BranchDTO branchDto) {

        if (!StringUtils.hasLength(branchDto.getName())) {
            throw new RuntimeException("Name field is required");
        }
        if (branchDto.getCompanyId() == null) {
            throw new RuntimeException("Company is required");
        }
       /* if (branchDto.getAddress() == null) {
            throw new RuntimeException("Address is required");
        }*/
        /*if (branchDto.getId() != null && branchRepository.existsByNameIgnoreCaseAndCompanyId(branchDto.getName(), branchDto.getCompanyId())) {
            throw new RuntimeException("Branch exists by this name: " + branchDto.getName());
        }*/

        if (branchDto.getId() == null) {
            if (branchRepository.existsByNameIgnoreCaseAndCompanyId(branchDto.getName(), branchDto.getCompanyId())) {
                throw new RuntimeException("Branch already exists by this name: " + branchDto.getName());
            }
        } else {
            if (branchRepository.existsByNameIgnoreCaseAndCompanyIdAndIdNot(branchDto.getName(), branchDto.getCompanyId(), branchDto.getId())) {
                throw new RuntimeException("Branch already exists by this name: " + branchDto.getName());
            }
        }
    }

    @Transactional
    public Long create(BranchDTO branchDto) {
        this.validate(branchDto);

        Branch branch = new Branch();
        branch.setName(branchDto.getName());
        branch.setCompanyId(branchDto.getCompanyId());

        if (branchDto.getAddress() != null) {
            Address address = new Address();
            address.setRegionId(branchDto.getAddress().getRegionId());
            address.setDistrict(branchDto.getAddress().getDistrictId());
            address.setNeighbourhoodId(branchDto.getAddress().getNeighbourhoodId());
            address.setStreet(branchDto.getAddress().getStreet());
            branch.setAddress(address);
        }
        return branchRepository.save(branch).getId();
    }

    @Transactional
    public Long update(Long id, BranchDTO branchDto) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        branchDto.setId(id);
        this.validate(branchDto);
        branch.setName(branchDto.getName());
        branch.setCompanyId(branchDto.getCompanyId());

        if (branchDto.getAddress() != null) {
            Address address = Optional.ofNullable(branch.getAddress()).orElseGet(Address::new);
            address.setRegionId(branchDto.getAddress().getRegionId());
            address.setDistrictId(branchDto.getAddress().getDistrictId());
            address.setNeighbourhoodId(branchDto.getAddress().getNeighbourhoodId());
            address.setStreet(branchDto.getAddress().getStreet());
            branch.setAddress(address);
        }
        return branchRepository.save(branch).getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new RuntimeException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

    public BranchDetailDTO get(Long id) {
        return branchRepository.findById(id).map(branch -> {
            BranchDetailDTO branchDetailDto = new BranchDetailDTO();
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

    public Page<BranchListDTO> getList(BranchFilter filter) {
        ResultList<Branch> resultList = branchRepository.getResultList(filter);
        List<BranchListDTO> result = resultList
                .getList()
                .stream()
                .map(branch -> {
                    BranchListDTO branchListDTO = new BranchListDTO();
                    branchListDTO.setId(branch.getId());
                    branchListDTO.setCompanyId(branch.getCompany().getId());
                    branchListDTO.setName(branch.getName());
                    if (branch.getAddress() != null) {
                        AddressDetailDTO addressDetailDTO = new AddressDetailDTO();
                        addressDetailDTO.setRegion(branch.getAddress().getRegion().toCommonDTO());
                        addressDetailDTO.setDistrict(branch.getAddress().getDistrict().toCommonDTO());
                        addressDetailDTO.setNeighbourhood(branch.getAddress().getNeighbourhood().toCommonDTO());
                        addressDetailDTO.setStreet(branch.getAddress().getStreet());
                    }
                    return branchListDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }


}
