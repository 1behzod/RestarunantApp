package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.DepartmentFilter;
import uz.behzod.restaurantApp.repository.custom.DepartmentRepositoryCustom;

public class DepartmentRepositoryImpl implements DepartmentRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<Department> getResultList(DepartmentFilter filter) {
        ResultList<Department> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select d from Department d ");
        sql.append("where 1 = 1 ");

        if (filter.getBranchId() != null) {
            sql.append(" and d.branchId = :branchId ");
        }
        if (filter.isSearchNotEmpty()) {
            sql.append("and (");
            sql.append(" lower(d.name)").append(filter.getLikeSearch());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select d", "select count(d.id) ");
        sql.append("order by d ").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Department> query = entityManager
                .createQuery(sql.toString(), Department.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getBranchId() != null) {
            query.setParameter("branchId", filter.getBranchId());
            countQuery.setParameter("branchId", filter.getBranchId());

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
