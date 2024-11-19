package uz.behzod.RestarunantApp.repository;


import uz.behzod.RestarunantApp.domain.branch.Branch;

public interface BranchRepository extends BaseRepository<Branch, Long> {

    boolean existsByCompanyId(Long companyId);


}
