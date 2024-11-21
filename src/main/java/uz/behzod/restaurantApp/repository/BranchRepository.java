package uz.behzod.restaurantApp.repository;


import uz.behzod.restaurantApp.domain.branch.Branch;

public interface BranchRepository extends BaseRepository<Branch, Long> {

    boolean existsByNameEqualsIgnoreCase(Long companyId, String name);


}
