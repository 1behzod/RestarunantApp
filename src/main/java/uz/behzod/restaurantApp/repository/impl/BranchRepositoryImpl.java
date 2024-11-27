package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.custom.BranchRepositoryCustom;


public class BranchRepositoryImpl implements BranchRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<Branch> getResultList(BaseFilter filter) {
        ResultList<Branch> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select b from Branch b ");
        sql.append(" where b.deleted = false ");

        if (filter.getCompanyId() != null) {
            sql.append(" and b.company.id =:companyId ");
        }
        if (filter.isSearchNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(b.name)").append(filter.getLikeSearch());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select b", " select count(b.id)");

        sql.append(" order by b.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Branch> query = entityManager
                .createQuery(sql.toString(), Branch.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getCompanyId() != null) {
            query.setParameter("companyId", filter.getCompanyId());
            countQuery.setParameter("companyId", filter.getCompanyId());
        }
        if (filter.isSearchNotEmpty()) {
            query.setParameter("searchKey", filter.getSearchFor());
            countQuery.setParameter("searchKey", filter.getSearchFor());
        }

        Long count = countQuery.getSingleResult();
        if (count > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(count);
        }

        return resultList;
    }

}
