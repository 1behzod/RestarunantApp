package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.repository.custom.CompanyRepositoryCustom;

public interface CompanyRepository extends BaseRepository<Company, Long>, CompanyRepositoryCustom {

    boolean existsByTin(String tin);

    boolean existsByTinAndIdNot(String tin, Long id);

    boolean existsByPinfl(String pinfl);

    boolean existsByPinflAndIdNot(String pinfl, Long id);
}
