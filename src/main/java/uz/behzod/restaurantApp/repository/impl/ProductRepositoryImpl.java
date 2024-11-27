package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.product.Product;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.custom.ProductRepositoryCustom;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<Product> getResultList(BaseFilter filter) {
        ResultList<Product> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select p from Product p ");
        sql.append("where p.deleted = false");

        if (filter.getDepartmentId() != null) {
            sql.append(" and p.department = :department ");
        }
        if (filter.isSearchNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(p.name)").append(filter.getLikeSearch());
            sql.append(" or lower(p.barcode)").append(filter.getLikeSearch());
            sql.append(")").append(filter.getLikeSearch());
        }

        String countSql = sql.toString().replaceFirst("select p", " select count(p.id)");

        sql.append(" order by p.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Product> query = entityManager
                .createQuery(sql.toString(), Product.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getDepartmentId() != null) {
            query.setParameter("departmentId", filter.getDepartmentId());
            countQuery.setParameter("departmentId", filter.getDepartmentId());
        }
        if (filter.isSearchNotEmpty()) {
            query.setParameter("search", filter.getSearchFor());
            countQuery.setParameter("search", filter.getSearchFor());
        }

        Long count = countQuery.getSingleResult();
        if (count > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(count);
        }

        return resultList;

    }
}
