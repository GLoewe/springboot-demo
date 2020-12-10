package de.haw.hamburg.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.haw.hamburg.demo.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
