package uz.behzod.restaurantApp.repository;


import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.repository.custom.BranchRepositoryCustom;

public interface BranchRepository extends BaseRepository<Branch, Long>, BranchRepositoryCustom {

    boolean existsByNameEqualsIgnoreCase(Long companyId, String name);



}
