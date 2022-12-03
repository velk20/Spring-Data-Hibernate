package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportHighestPriceTasksDto;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select new softuni.exam.models.dto.ExportHighestPriceTasksDto(" +
            "t.car.carMake,t.car.carModel,t.car.kilometers,t.mechanic.firstName,t.mechanic.lastName,t.id," +
            "t.car.engine,t.price) from Task  as" +
            " t where t.car.carType = :carType " +
            "order by t.price desc")
    List<ExportHighestPriceTasksDto> findHighestPriceTasks(CarType carType);
}
