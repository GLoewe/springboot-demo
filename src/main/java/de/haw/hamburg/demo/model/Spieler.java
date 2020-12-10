package de.haw.hamburg.demo.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "spieler")
public class Spieler extends AuditModel {
    @Id
    @GeneratedValue(generator = "spieler_generator")
    @SequenceGenerator(
            name = "spieler_generator",
            sequenceName = "spieler_sequence",
            initialValue = 1000
    )
    private Long id;
    @NotBlank
    @Column(columnDefinition = "text")
    private String firstname;
    @NotBlank
    @Column(columnDefinition = "text")
    private String lastname;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

