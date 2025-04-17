package simdev.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import simdev.demo.models.Tasks;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long>{
    Tasks getByName(String name);
}
