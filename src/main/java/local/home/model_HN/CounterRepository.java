package local.home.model_HN;

import org.springframework.data.repository.PagingAndSortingRepository;
import local.home.model.CounterEntity;

public interface CounterRepository extends PagingAndSortingRepository<CounterEntity, Long>
{
	
}