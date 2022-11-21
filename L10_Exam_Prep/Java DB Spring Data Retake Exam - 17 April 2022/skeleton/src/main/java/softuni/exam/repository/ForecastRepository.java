package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportForecastDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DayOfWeek;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    Optional<Forecast> findByDayOfWeekAndCity(DayOfWeek dayOfWeek, City city);

    @Query("select new softuni.exam.models.dto.ExportForecastDTO(" +
            "f.city.cityName,round (f.minTemperature,2),round (f.maxTemperature,2),f.sunrise,f.sunset) from " +
            "Forecast as" +
            " f " +
            "where f.dayOfWeek = :dayOfWeek and f.city.population < 150000 " +
            "order by f.maxTemperature desc, f.id")
    List<ExportForecastDTO> exportAllByDayOfWeek(DayOfWeek dayOfWeek);
}
