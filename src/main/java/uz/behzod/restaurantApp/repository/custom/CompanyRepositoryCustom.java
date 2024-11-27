package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Repository
public interface CompanyRepositoryCustom {

    ResultList<Company> getResultList(BaseFilter filter);
}
