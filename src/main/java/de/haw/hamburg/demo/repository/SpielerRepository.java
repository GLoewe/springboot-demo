package de.haw.hamburg.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.haw.hamburg.demo.model.Spieler;
import java.util.List;

@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {
    List<Spieler> findByTeamId(Long teamId);}

