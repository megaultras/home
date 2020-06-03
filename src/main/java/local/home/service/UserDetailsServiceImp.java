package local.home.service;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User;

import com.wizarius.orm.database.DBException;

import local.home.model.UsersEntity;
import local.home.model.UsersStorage;
import local.home.lib.AppContext;

public class UserDetailsServiceImp implements UserDetailsService 
{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		AppContext context = AppContext.getInstance();
		
		UsersStorage storage;
		UsersEntity userEntity = null;
		
		try {
			storage = new UsersStorage(context.getConnectionPool());
			
			userEntity = storage.getSession().getSelectQuery()
				.where("login", username)
				.where("login", username)
				.getOne();
		} catch (DBException ex) {
	    	System.out.println("Error: " + ex.getMessage());
	    }
		
	    UserBuilder builder = null;
	    if (userEntity != null) {
	    	builder = User.withUsername(username);
	    	builder.password(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
	    	builder.roles("ADMIN");
	    } else {
	    	throw new UsernameNotFoundException("User not found.");
	    }
	
	    return builder.build();
	}
}