package de.haw.hamburg.demo.controller;

import de.haw.hamburg.demo.model.Team;
import de.haw.hamburg.demo.repository.TeamRepository;
import de.haw.hamburg.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/team")
    public Page<Team> getTeam(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @GetMapping("/team/{teamId}")
    public Team getTeam(@PathVariable Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);

        if (!team.isPresent())
            throw new ResourceNotFoundException("Team not found with id " + teamId);
        return team.get();
    }

    @PostMapping("/team")
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamRepository.save(team);
    }

    @PutMapping("/team/{teamId}")
    public Team updateTeam(@PathVariable Long teamId,
                           @Valid @RequestBody Team teamRequest) {
        return teamRepository.findById(teamId)
                .map(team -> {
                    team.setTeamname(teamRequest.getTeamname());
                    return teamRepository.save(team);
                }).orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + teamId));
    }

    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamId) {
        return teamRepository.findById(teamId)
                .map(team -> {
                    teamRepository.delete(team);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + teamId));
    }
}

