package uz.behzod.RestarunantApp.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.behzod.RestarunantApp.domain.company.Company;
import uz.behzod.RestarunantApp.repository.CompanyRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyService {

    CompanyRepository companyRepository;

    @Transactional
    public Company createCompany(Company company) {
        if (companyRepository.existsById(company.getId())) {
            throw new IllegalArgumentException("Company already exists");
        }
        return companyRepository.save(company);
    }

    @Transactional
    public Company updateCompany(Company updatedCompany, Long Id) {
        Company company = companyRepository.findById(Id).orElseThrow(() ->
                new RuntimeException("Company not found"));

        company.setName(updatedCompany.getName());
        company.setAddress(updatedCompany.getAddress());
        company.setTin(updatedCompany.getTin());
        company.setPinfl(updatedCompany.getPinfl());
        company.setBrand(updatedCompany.getBrand());
        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Long Id) {
        if (!companyRepository.existsById(Id)) {
            throw new RuntimeException("Company not found with id: " + Id);
        }
        companyRepository.deleteById(Id);

    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
