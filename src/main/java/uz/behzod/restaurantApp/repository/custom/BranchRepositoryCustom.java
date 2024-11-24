package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.branch.BranchFilter;

@Repository
public interface BranchRepositoryCustom {
    ResultList<Branch> getResultList(BranchFilter filter);
}
