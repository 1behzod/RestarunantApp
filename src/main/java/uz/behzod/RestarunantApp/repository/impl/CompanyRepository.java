package uz.behzod.RestarunantApp.repository.impl;

import uz.behzod.RestarunantApp.domain.company.Company;
import uz.behzod.RestarunantApp.repository.BaseRepository;

import java.util.Optional;

public interface CompanyRepository extends BaseRepository<Company, Long> {
    Optional<Company> findByName(String name);

    Optional<Company> findByTin(String tin);

}
