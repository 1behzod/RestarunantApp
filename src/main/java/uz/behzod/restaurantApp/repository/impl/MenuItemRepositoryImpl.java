package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.menu.MenuItemFilter;
import uz.behzod.restaurantApp.repository.custom.MenuItemRepositoryCustom;

public class MenuItemRepositoryImpl implements MenuItemRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<MenuItem> getResultList(MenuItemFilter filter) {
        ResultList<MenuItem> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select m from MenuItem ");
        sql.append("where m.deleted = false ");

        if (filter.getMenuId() != null) {
            sql.append("and m.menuId = :menuId ");
        }
        if (filter.isSearchNotEmpty()) {
            sql.append("and (");
            sql.append(" lower m.name)").append(filter.getLikeSearch());
            sql.append(")");
        }

        String countSql = sql.toString().replaceFirst("select m ", "select count(m.id) ");
        sql.append("order by m.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<MenuItem> query = entityManager
                .createQuery(sql.toString(), MenuItem.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getMenuId() != null) {
            query.setParameter("menuId", filter.getMenuId());
            countQuery.setParameter("menuId", filter.getMenuId());
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
