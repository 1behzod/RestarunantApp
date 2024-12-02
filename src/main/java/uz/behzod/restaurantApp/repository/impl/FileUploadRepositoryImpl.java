package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uz.behzod.restaurantApp.domain.file.FileUpload;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.custom.FileUploadRepositoryCustom;

public class FileUploadRepositoryImpl implements FileUploadRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResultList<FileUpload> getResultList(BaseFilter filter) {
        ResultList<FileUpload> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select f from FileUpload f ");
        sql.append("where 1=1 ");


        if (filter.isSearchNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(f.name)").append(filter.getLikeSearch());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select f", "select count(f.id)");

        sql.append(" order by f.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<FileUpload> query = entityManager.createQuery(sql.toString(), FileUpload.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);


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
