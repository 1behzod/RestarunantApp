package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.repository.BranchRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BranchService {

    BranchRepository branchRepository;

    public void validate(Branch branch) {

        if (branch.getName() == null || branch.getName().isEmpty()) {
            throw new RuntimeException("Name field is required");
        }
        if (branch.getCompanyId() == null) {
            throw new RuntimeException("Branch must be associated with a valid company");
        }
        if (branch.getAddress() == null) {
            throw new RuntimeException("Branch address is required");
        }
        if (branch.getId() != null && branchRepository.existsByCompanyId(branch.getCompanyId())) {
            throw new RuntimeException("Branch already exists");
        }
    }


    @Transactional
    public void delete(Branch branch) {
        if (!branchRepository.existsById(branch.getId())) {
            throw new RuntimeException("Branch not found");
        }
        branchRepository.delete(branch);
    }

    @Transactional
    public Branch create(Branch newBranch) {
        this.validate(newBranch);
        Branch branch = new Branch();
        branch.setName(newBranch.getName());
        branch.setCompanyId(newBranch.getCompanyId());
        branch.setAddress(newBranch.getAddress());
        return branchRepository.save(branch);

    }

}
