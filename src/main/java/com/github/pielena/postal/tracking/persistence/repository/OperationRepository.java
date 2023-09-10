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
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID>, JpaSpecificationExecutor<Operation> {

    interface Specs {
        static Specification<Operation> byRequest(OperationSearchDtoRq request) {
            return (root, query, builder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (request.getItemId() != null) {
                    predicates.add(builder.equal(root.get("item").get("id"), UUID.fromString(request.getItemId())));
                }
                if (request.getIndex() != null) {
                    predicates.add(builder.equal(root.get("postOffice").get("index"), request.getIndex()));
                }
                if (request.getState() != null) {
                    predicates.add(builder.equal(root.get("state"), State.valueOf(request.getState().toUpperCase())));
                }
                if (request.getIsDestination() != null) {
                    predicates.add(builder.equal(root.get("isDestination"), request.getIsDestination()));
                }
                if (request.getDateFrom() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("date"), request.getDateFrom()));
                }
                if (request.getDateTo() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("date"), request.getDateTo()));
                }
                return builder.and(predicates.toArray(Predicate[]::new));
            };
        }
    }
}
