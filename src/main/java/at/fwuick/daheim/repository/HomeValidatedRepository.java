package at.fwuick.daheim.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.dao.HomeDao;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.HomeIdentity;

@Component
public class HomeValidatedRepository {
	
	@Autowired
	HomeDao dao;
	
	public Home get(HomeIdentity id) throws DaheimException {
		try{
			return dao.get(id);
		}
		catch(DataAccessException e){
			throw new DaheimException("Home not found");
		}
	}
}
