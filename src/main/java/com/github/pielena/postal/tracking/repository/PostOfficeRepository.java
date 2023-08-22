package com.github.pielena.postal.tracking.repository;

import com.github.pielena.postal.tracking.entity.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOffice, Integer> {

    Optional<PostOffice> findByIndex(int index);
}
