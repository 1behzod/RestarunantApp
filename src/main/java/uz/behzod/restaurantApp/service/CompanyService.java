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
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.dto.address.AddressDetailDTO;
import uz.behzod.restaurantApp.dto.address.AddressListDTO;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.company.CompanyDTO;
import uz.behzod.restaurantApp.dto.company.CompanyDetailDTO;
import uz.behzod.restaurantApp.dto.company.CompanyListDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.filters.CompanyFilter;
import uz.behzod.restaurantApp.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class CompanyService {

    CompanyRepository companyRepository;

    private void validate(CompanyDTO companyDTO) {
        if (!StringUtils.hasLength(companyDTO.getName())) {
            throw new RuntimeException("Company name cannot be empty");
        }
        if (!StringUtils.hasLength(companyDTO.getTin())) {
            throw new RuntimeException("Company TIN cannot be empty");
        }
        if (!StringUtils.hasLength(companyDTO.getPinfl())) {
            throw new RuntimeException("Company PINFL cannot be empty");
        }
        if (companyDTO.getId() == null) {
            if (companyRepository.existsByTin(companyDTO.getTin())) {
                throw new RuntimeException("Company exists with TIN:" + companyDTO.getTin());
            }
            if (companyRepository.existsByPinfl(companyDTO.getPinfl())) {
                throw new RuntimeException("Company exists with PINFL:" + companyDTO.getPinfl());
            }
        }
        if (companyDTO.getId() != null) {
            if (companyRepository.existsByTinAndIdNot(companyDTO.getTin(), companyDTO.getId())) {
                throw new RuntimeException("Company exists with TIN:" + companyDTO.getTin());
            }
            if (companyRepository.existsByPinflAndIdNot(companyDTO.getPinfl(), companyDTO.getId())) {
                throw new RuntimeException("Company exists with PINFL:" + companyDTO.getPinfl());
            }
        }
    }

    @Transactional
    public Long create(CompanyDTO companyDTO) {
        this.validate(companyDTO);
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setBrand(companyDTO.getBrand());
        company.setTin(companyDTO.getTin());
        company.setPinfl(companyDTO.getPinfl());
        if (companyDTO.getAddress() != null) {
            Address address = new Address();
            address.setRegionId(companyDTO.getAddress().getRegionId());
            address.setDistrictId(companyDTO.getAddress().getDistrictId());
            address.setNeighbourhoodId(companyDTO.getAddress().getNeighbourhoodId());
            address.setStreet(companyDTO.getAddress().getStreet());
            company.setAddress(address);
        }
        return companyRepository.save(company).getId();
    }

    public CompanyDetailDTO get(Long id) {
        return companyRepository.findById(id).map(company -> {
            CompanyDetailDTO companyDetailDTO = new CompanyDetailDTO();
            companyDetailDTO.setId(company.getId());
            companyDetailDTO.setTin(company.getTin());
            companyDetailDTO.setName(company.getName());
            companyDetailDTO.setBrand(company.getBrand());
            companyDetailDTO.setPinfl(company.getPinfl());

            if (company.getAddress() != null) {
                AddressDetailDTO addressDetailDTO = new AddressDetailDTO();
                addressDetailDTO.setRegion(company.getAddress().getRegion().toCommonDTO());
                addressDetailDTO.setDistrict(company.getAddress().getDistrict().toCommonDTO());
                addressDetailDTO.setNeighbourhood(company.getAddress().getNeighbourhood().toCommonDTO());
                addressDetailDTO.setStreet(company.getAddress().getStreet());
                companyDetailDTO.setAddress(addressDetailDTO);
            }
            return companyDetailDTO;
        }).orElseThrow(() -> new RuntimeException("Company not found"));
    }


    @Transactional
    public Long update(Long id, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        companyDTO.setId(id);
        this.validate(companyDTO);

        company.setName(companyDTO.getName());
        company.setTin(companyDTO.getTin());
        company.setPinfl(companyDTO.getPinfl());
        company.setBrand(companyDTO.getBrand());
        if (companyDTO.getAddress() != null) {
            Address address = Optional.ofNullable(company.getAddress()).orElseGet(Address::new);
            address.setRegionId(companyDTO.getAddress().getRegionId());
            address.setDistrictId(companyDTO.getAddress().getDistrictId());
            address.setNeighbourhoodId(companyDTO.getAddress().getNeighbourhoodId());
            address.setStreet(companyDTO.getAddress().getStreet());
            company.setAddress(address);
        }
        return companyRepository.save(company).getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }

    public Page<CompanyListDTO> getList(CompanyFilter filter) {
        ResultList<Company> resultList = companyRepository.getResultList(filter);
        List<CompanyListDTO> result = resultList
                .getList()
                .stream()
                .map(company -> {
                    CompanyListDTO companyListDTO = new CompanyListDTO();
                    companyListDTO.setId(company.getId());
                    companyListDTO.setName(company.getName());
                    companyListDTO.setTin(company.getTin());
                    companyListDTO.setPinfl(company.getPinfl());
                    if (company.getAddress() != null) {
                        AddressListDTO addressListDTO = new AddressListDTO();
                        addressListDTO.setRegion(company.getAddress().getRegion().toCommonDTO());
                        addressListDTO.setDistrict(company.getAddress().getDistrict().toCommonDTO());
                        addressListDTO.setNeighbourhood(company.getAddress().getNeighbourhood().toCommonDTO());
                        companyListDTO.setAddress(addressListDTO);
                    }
                    return companyListDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }
}
