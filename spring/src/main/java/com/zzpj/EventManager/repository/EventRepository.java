package com.zzpj.EventManager.repository;

import com.zzpj.EventManager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.entranceFee = :goodPrice")
    List<Event> findAllFreeEvents(@Param("goodPrice") Double goodPrice);
}
