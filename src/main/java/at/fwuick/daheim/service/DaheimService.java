package at.fwuick.daheim.service;

import java.util.UUID;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.fwuick.daheim.dao.UserDao;
import at.fwuick.daheim.model.CreateUserRequest;
import at.fwuick.daheim.model.CreateUserResponse;
import at.fwuick.daheim.model.ErrorResponse;
import at.fwuick.daheim.model.JoinHomeRequest;
import at.fwuick.daheim.model.Response;
import at.fwuick.daheim.model.User;
import lombok.val;

@RestController
public class DaheimService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	HomeDao homeDao;
	
	@Autowired
	Validator validator;
	
	private ErrorResponse validate(Object o){
		val errors = validator.validate(o);
		if(!errors.isEmpty()){
			val error = errors.iterator().next();
			return new ErrorResponse(error);
		}
		return null;
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/create-user")
	public Response createUser(@RequestBody CreateUserRequest request){
		//VALIDATE REQUEST - POWER BLOCK ///////////////
		ErrorResponse error;
		if((error = validate(request)) != null)
			return error;
		////////////////////////////////////////////////
		
		User user = new User(request.getName());
		user.setUuid(UUID.randomUUID().toString());
		userDao.insert(user);
		return new CreateUserResponse(user.getUuid());
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/join-home")
	public Response joinHome(@RequestBody JoinHomeRequest request){
		//VALIDATE REQUEST - POWER BLOCK ///////////////
		ErrorResponse error;
		if((error = validate(request)) != null)
			return error;
		////////////////////////////////////////////////
		
		return new ErrorResponse("nope");
	}
	
	
}
