package local.home.model_HN;

import org.springframework.data.repository.PagingAndSortingRepository;
import local.home.model.AccountsEntity;

public interface AccountsRepository extends PagingAndSortingRepository<AccountsEntity, Long>
{
	
}