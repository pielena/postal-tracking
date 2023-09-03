package com.github.pielena.postal.tracking.persistence.repository;

import com.github.pielena.postal.tracking.persistence.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
}
