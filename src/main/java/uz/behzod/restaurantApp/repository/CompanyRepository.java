package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.company.Company;

public interface CompanyRepository extends BaseRepository<Company, Long> {

    boolean existsByTin(String tin);

    boolean existsByTinAndIdNot(String tin, Long id);

    boolean existsByPinfl(String pinfl);

    boolean existsByPinflAndIdNot(String pinfl, Long id);

}
