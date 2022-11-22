package com.example.football.repository;

import com.example.football.models.dto.ExportBestPlayer;
import com.example.football.models.entity.Player;
import javassist.Loader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository  extends JpaRepository<Player,Long> {

    Optional<Player> findByEmail(String email);

    @Query("select new com.example.football.models.dto.ExportBestPlayer" +
            "(p.firstName,p.lastName,p.position,p.team.name,p.team.stadiumName) " +
            "from Player as p " +
            "where p.birthDate between :start and :end " +
            "order by p.stat.shooting desc, p.stat.passing desc , p.stat.endurance desc, p.lastName")
    List<ExportBestPlayer> findBestPlayers(LocalDate start, LocalDate end);

    List<Player> findAllByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(LocalDate start, LocalDate end);

}
