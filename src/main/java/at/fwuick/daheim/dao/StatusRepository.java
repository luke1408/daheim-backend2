package at.fwuick.daheim.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.Status;

@Repository
public class StatusRepository {

	@Autowired
	StatusDao statusDao;
	
	public Status getStatusSafe(long id) throws DaheimException {
		try{
			return statusDao.getStatus(id);
		}catch(DataAccessException e){
			throw new DaheimException(Errors.INVALID_STATUS);
		}
	}

}
