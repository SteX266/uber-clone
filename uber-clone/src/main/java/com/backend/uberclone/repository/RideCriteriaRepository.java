package com.backend.uberclone.repository;

import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RidePage;
import com.backend.uberclone.model.RideSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RideCriteriaRepository {


    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public RideCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Ride> findAllWithFilters(RidePage RidePage,
                                         RideSearchCriteria RideSearchCriteria){
        CriteriaQuery<Ride> criteriaQuery = criteriaBuilder.createQuery(Ride.class);
        Root<Ride> RideRoot = criteriaQuery.from(Ride.class);
        Predicate predicate = getPredicate(RideSearchCriteria, RideRoot);
        criteriaQuery.where(predicate);
        setOrder(RidePage, criteriaQuery, RideRoot);

        TypedQuery<Ride> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(RidePage.getPageNumber() * RidePage.getPageSize());
        typedQuery.setMaxResults(RidePage.getPageSize());

        Pageable pageable = getPageable(RidePage);

        long RidesCount = getRidesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, RidesCount);
    }

    private Predicate getPredicate(RideSearchCriteria RideSearchCriteria,
                                   Root<Ride> RideRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(RideSearchCriteria.getFirstName())){
            predicates.add(
                    criteriaBuilder.like(RideRoot.get("firstName"),
                            "%" + RideSearchCriteria.getFirstName() + "%")
            );
        }
        if(Objects.nonNull(RideSearchCriteria.getLastName())){
            predicates.add(
                    criteriaBuilder.like(RideRoot.get("lastName"),
                            "%" + RideSearchCriteria.getLastName() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(RidePage RidePage,
                          CriteriaQuery<Ride> criteriaQuery,
                          Root<Ride> RideRoot) {
        if(RidePage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(RideRoot.get(RidePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(RideRoot.get(RidePage.getSortBy())));
        }
    }

    private Pageable getPageable(RidePage RidePage) {
        Sort sort = Sort.by(RidePage.getSortDirection(), RidePage.getSortBy());
        return PageRequest.of(RidePage.getPageNumber(),RidePage.getPageSize(), sort);
    }

    private long getRidesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Ride> countRoot = countQuery.from(Ride.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
