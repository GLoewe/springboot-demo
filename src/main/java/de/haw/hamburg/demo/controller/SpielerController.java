package de.haw.hamburg.demo.controller;

import de.haw.hamburg.demo.exception.ResourceNotFoundException;
import de.haw.hamburg.demo.model.Spieler;
import de.haw.hamburg.demo.repository.TeamRepository;
import de.haw.hamburg.demo.repository.SpielerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SpielerController {

    @Autowired
    private SpielerRepository spielerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/team/{teamId}/spieler")
    public List<Spieler> getSpielerByTeamId(@PathVariable Long teamId) {
        return spielerRepository.findByTeamId(teamId);
    }

    @GetMapping("/team/{teamId}/spieler/{spielerId}")
    public Spieler getTeam(@PathVariable Long spielerId) {
        Optional<Spieler> spieler = spielerRepository.findById(spielerId);

        if (!spieler.isPresent())
            throw new ResourceNotFoundException("Team not found with id " + spielerId);
        return spieler.get();
    }

    @PostMapping("/team/{teamId}/spieler")
    public Spieler addSpieler(@PathVariable Long teamId,
                              @Valid @RequestBody Spieler spieler) {
        return teamRepository.findById(teamId)
                .map(question -> {
                    spieler.setTeam(question);
                    return spielerRepository.save(spieler);
                }).orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + teamId));
    }

    @PutMapping("/team/{teamId}/spieler/{spielerId}")
    public Spieler updateSpieler(@PathVariable Long teamId,
                                 @PathVariable Long spielerId,
                                 @Valid @RequestBody Spieler spielerRequest) {
        if(!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Team not found with id " + teamId);
        }

        return spielerRepository.findById(spielerId)
                .map(spieler -> {
                    spieler.setFirstname(spielerRequest.getFirstname());
                    spieler.setLastname(spielerRequest.getLastname());
                    return spielerRepository.save(spieler);
                }).orElseThrow(() -> new ResourceNotFoundException("Spieler not found with id " + spielerId));
    }

    @DeleteMapping("/team/{teamId}/spieler/{spielerId}")
    public ResponseEntity<?> deleteSpieler(@PathVariable Long teamId,
                                           @PathVariable Long spielerId) {
        if(!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Team not found with id " + teamId);
        }

        return spielerRepository.findById(spielerId)
                .map(answer -> {
                    spielerRepository.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + spielerId));

    }
}

