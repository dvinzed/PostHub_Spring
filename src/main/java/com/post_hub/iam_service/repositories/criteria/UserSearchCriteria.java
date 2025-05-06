package com.post_hub.iam_service.repositories.criteria;

import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.enums.UserSortField;
import com.post_hub.iam_service.model.request.User.UserSearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class UserSearchCriteria implements Specification<User> {

    private final UserSearchRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<User> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getUsername())) {
            predicates.add(cb.like(root.get(User.USERNAME_FIELD), "%" + request.getUsername() + "%"));
        }

        if (Objects.nonNull(request.getEmail())) {
            predicates.add(cb.like(root.get(User.EMAIL_FIELD), "%" + request.getEmail() + "%"));
        }

        if (Objects.nonNull(request.getRegistrationStatus())) {
            predicates.add(cb.equal(root.get("registrationStatus"), request.getRegistrationStatus()));
        }

        if (Objects.nonNull(request.getDeleted())) {
            predicates.add(cb.equal(root.get(User.DELETED_FIELD), request.getDeleted()));
        }

        if (Objects.nonNull(request.getKeyword())) {
            Predicate keywordPredicate = cb.or(
                    cb.like(root.get(User.USERNAME_FIELD), "%" + request.getKeyword() + "%"),
                    cb.like(root.get(User.EMAIL_FIELD), "%" + request.getKeyword() + "%")
            );
            predicates.add(keywordPredicate);
        }

        applySorting(root, cb, query);

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private void applySorting(Root<User> root, CriteriaBuilder cb, CriteriaQuery<?> query) {
        if (request instanceof com.post_hub.iam_service.model.request.User.UserSearchRequest userReq &&
                userReq.getSortField() != null) {

            UserSortField sortField = userReq.getSortField();

            switch (sortField) {
                case USERNAME -> query.orderBy(cb.asc(root.get(User.USERNAME_FIELD)));
                case EMAIL -> query.orderBy(cb.asc(root.get(User.EMAIL_FIELD)));
                default -> query.orderBy(cb.asc(root.get(User.ID_FIELD)));
            }
        } else {
            query.orderBy(cb.asc(root.get(User.ID_FIELD)));
        }
    }
}
