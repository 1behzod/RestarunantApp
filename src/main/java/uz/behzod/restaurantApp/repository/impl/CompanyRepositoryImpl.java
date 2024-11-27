package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.custom.CompanyRepositoryCustom;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<Company> getResultList(BaseFilter filter) {

        ResultList<Company> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select c from Company c ");
        sql.append(" where c.deleted = false ");

        if (filter.getRegionId() != null) {
            sql.append(" and c.address.regionId =:regionId ");
        }
        if (filter.getDistrictId() != null) {
            sql.append(" and c.address.districtId =:districtId ");
        }
        if (filter.getNeighborhoodId() != null) {
            sql.append(" and c.address.neighborhoodId =:neighborhoodId ");
        }
        if (filter.isSearchNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(c.name)").append(filter.getLikeSearch());
            sql.append(" or lower(c.brand)").append(filter.getLikeSearch());
            sql.append(" or lower(c.tin)").append(filter.getLikeSearch());
            sql.append(" or lower(c.pinfl)").append(filter.getLikeSearch());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select c", " select count(c.id)");

        sql.append(" order by c.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Company> query = entityManager
                .createQuery(sql.toString(), Company.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getRegionId() != null) {
            query.setParameter("regionId", filter.getRegionId());
            countQuery.setParameter("regionId", filter.getRegionId());
        }
        if (filter.getDistrictId() != null) {
            query.setParameter("districtId", filter.getDistrictId());
            countQuery.setParameter("districtId", filter.getDistrictId());
        }
        if (filter.getNeighborhoodId() != null) {
            query.setParameter("neighborhoodId", filter.getNeighborhoodId());
            countQuery.setParameter("neighborhoodId", filter.getNeighborhoodId());
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
