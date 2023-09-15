package com.github.pielena.postal.tracking.persistence.repository;

import com.github.pielena.postal.tracking.dto.OperationSearchDtoRq;
import com.github.pielena.postal.tracking.enums.State;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID>, JpaSpecificationExecutor<Operation> {

    interface Specs {
        static Specification<Operation> byRequest(OperationSearchDtoRq request) {
            return (root, query, builder) -> {
                List<Predicate> predicates = new ArrayList<>();

                Optional.ofNullable(request.getItemId())
                        .ifPresent(itemId -> predicates.add(builder.equal(root.get("item").get("id"), UUID.fromString(itemId))));

                Optional.ofNullable(request.getIndex())
                        .ifPresent(index -> predicates.add(builder.equal(root.get("postOffice").get("index"), index)));

                Optional.ofNullable(request.getState())
                        .ifPresent(state -> predicates.add(builder.equal(root.get("state"), State.valueOf(state.toUpperCase()))));

                Optional.ofNullable(request.getIsDestination())
                        .ifPresent(isDest -> predicates.add(builder.equal(root.get("isDestination"), isDest)));

                Optional.ofNullable(request.getDateFrom())
                        .ifPresent(dateFrom -> predicates.add(builder.greaterThanOrEqualTo(root.get("date"), dateFrom)));

                Optional.ofNullable(request.getDateTo())
                        .ifPresent(dateTo -> predicates.add(builder.lessThanOrEqualTo(root.get("date"), dateTo)));

                return builder.and(predicates.toArray(Predicate[]::new));
            };
        }
    }
}
