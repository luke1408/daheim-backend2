package at.fwuick.daheim.service;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.dao.HomeDao;
import at.fwuick.daheim.dao.StatusDao;
import at.fwuick.daheim.dao.StatusRepository;
import at.fwuick.daheim.dao.UserDao;
import at.fwuick.daheim.dao.UserHomeRepository;
import at.fwuick.daheim.model.CheckHomeObject;
import at.fwuick.daheim.model.CheckHomeResponse;
import at.fwuick.daheim.model.CreateHomeRequest;
import at.fwuick.daheim.model.CreateUserRequest;
import at.fwuick.daheim.model.CreateUserResponse;
import at.fwuick.daheim.model.ErrorResponse;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.JoinHomeRequest;
import at.fwuick.daheim.model.Response;
import at.fwuick.daheim.model.SetStatusRequest;
import at.fwuick.daheim.model.Status;
import at.fwuick.daheim.model.User;

@RestController
public class DaheimService {
	@Autowired
	UserDao userDao;

	@Autowired
	HomeDao homeDao;

	@Autowired
	UserHomeRepository repo;
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired
	StatusRepository statusRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/create-user")
	public Response createUser(@RequestBody @Valid CreateUserRequest request) {
		User user = new User(request.getName());
		user.setUuid(UUID.randomUUID().toString());
		userDao.insert(user);
		return new CreateUserResponse(user.getUuid());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/join-home")
	public Response joinHome(@RequestBody @Valid JoinHomeRequest request) throws DaheimException {
		User user = getNoHomeUser(request.getUuid());
		Home home = homeDao.findByBssidSafe(request.getBssid());
		user.setHome(home.getId());
		userDao.updateHome(user);
		return new Response();

	}

	private void validateNoHome(User user) throws DaheimException {
		if (user.getHome() != 0) {
			throw new DaheimException(Errors.USER_HAS_HOME_ALREADY);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/create-home")
	public Response joinHome(@RequestBody @Valid CreateHomeRequest request) throws DaheimException {
		User user = getNoHomeUser(request.getUuid());
		if (homeDao.bssidExists(request.getBssid()))
			throw new DaheimException(Errors.HOME_ALREADY_EXISTS);
		Home home = new Home();
		home.setName(request.getName());
		home.setBssid(request.getBssid());
		homeDao.insert(home);
		user.setHome(home.getId());
		userDao.updateHome(user);
		return new Response();
	}

	private User getNoHomeUser(String uuid) throws DaheimException {
		User user = userDao.findByUuidSafe(uuid);
		validateNoHome(user);
		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/check-home")
	public Response checkHome(@RequestBody @Valid JoinHomeRequest request) throws DaheimException {
		getNoHomeUser(request.getUuid());
		Home h = homeDao.findByBssid(request.getBssid());

		CheckHomeResponse r = new CheckHomeResponse();
		if (h != null) {
			CheckHomeObject cho = new CheckHomeObject();
			cho.setName(h.getName());
			cho.setUser(repo.countUserByHome(h));
			r.setHome(cho);
		}
		return r;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/set-status")
	public Response setStatus(@RequestBody @Valid SetStatusRequest request) throws DaheimException {
		User user = userDao.findByUuidSafe(request.getUuid());
		Status status = statusRepository.getStatusSafe(request.getStatus());
		statusDao.setStatus(user, status);
		return new Response();
	}
	
	
	@ExceptionHandler(Exception.class)
	public ErrorResponse katch(Exception e) {
		return new ErrorResponse(e.getMessage());
	}

}
