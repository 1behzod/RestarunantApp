package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.custom.MenuRepositoryCustom;

public class MenuRepositoryImpl implements MenuRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<Menu> getResultList(BaseFilter filter) {

        ResultList<Menu> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select m from Menu m ");
        sql.append("where m.deleted = false ");

        if (filter.getBranchId() != null) {
            sql.append(" and m.branch.id = :branchId ");
        }
        if (filter.isSearchNotEmpty()) {
            sql.append(" and ");
            sql.append(" lower(m.name)").append(filter.getLikeSearch());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select m", "select count(m.id)");

        sql.append(" order by m.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Menu> query = entityManager
                .createQuery(sql.toString(), Menu.class)
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
