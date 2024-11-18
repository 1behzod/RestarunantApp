package uz.behzod.RestarunantApp.repository.impl;


import uz.behzod.RestarunantApp.domain.branch.Branch;
import uz.behzod.RestarunantApp.repository.BaseRepository;

import java.util.List;

public interface BranchRepository extends BaseRepository<Branch, Long> {

    boolean existsByCompanyId(Long companyId);

    List<Branch> findAllByCompanyId(Long companyId);


}
