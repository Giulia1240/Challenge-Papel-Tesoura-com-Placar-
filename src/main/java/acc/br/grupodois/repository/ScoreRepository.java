package acc.br.grupodois.repository;

import acc.br.grupodois.entities.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<Score,Integer> {
}
