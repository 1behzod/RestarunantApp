package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.util.CollectionUtils;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.custom.UserRepositoryCustom;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<User> getResultList(BaseFilter filter) {
        ResultList<User> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select u from User u ");
        sql.append(" where u.deleted = false ");

        if (filter.getCompanyId() != null) {
            sql.append(" and u.companyId = :companyId ");
        }
        if (filter.getBranchId() != null) {
            sql.append(" and u.branchId = :branchId ");
        }
        if (filter.getDepartmentId() != null) {
            sql.append(" and u.departmentId = :departmentId ");
        }
        /*if (filter.getRole() != null) {
            sql.append(" and u.role = :role ");
        }*/
        if (filter.getStatus() != null) {
            sql.append(" and u.status = :status ");
        }
       /* if (!CollectionUtils.isEmpty(filter.getRoles())) {
            sql.append(" and u.role in (:roles) ");
        }*/
        if (filter.isSearchNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(u.name)").append(filter.getLikeSearch());
            sql.append(" or lower(u.username)").append(filter.getLikeSearch());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select u", " select count(u.id)");

        sql.append(" order by u.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<User> query = entityManager
                .createQuery(sql.toString(), User.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getCompanyId() != null) {
            query.setParameter("companyId", filter.getCompanyId());
            countQuery.setParameter("companyId", filter.getCompanyId());
        }
        if (filter.getBranchId() != null) {
            query.setParameter("branchId", filter.getBranchId());
            countQuery.setParameter("branchId", filter.getBranchId());
        }
        if (filter.getDepartmentId() != null) {
            query.setParameter("departmentId", filter.getDepartmentId());
            countQuery.setParameter("departmentId", filter.getDepartmentId());
        }
  /*      if (filter.getRole() != null) {
            query.setParameter("role", filter.getRole());
            countQuery.setParameter("role", filter.getRole());
        }
        if (filter.getStatus() != null) {
            query.setParameter("status", filter.getStatus());
            countQuery.setParameter("status", filter.getStatus());
        }
        if (filter.getRoles() != null && !filter.getRoles().isEmpty()) {
            query.setParameter("roles", filter.getRoles());
            countQuery.setParameter("roles", filter.getRoles());
        }*/
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
