package at.fwuick.daheim.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import at.fwuick.daheim.dao.UserRepository;
import at.fwuick.daheim.model.CheckHomeObject;
import at.fwuick.daheim.model.CheckHomeResponse;
import at.fwuick.daheim.model.CreateHomeRequest;
import at.fwuick.daheim.model.CreateUserRequest;
import at.fwuick.daheim.model.CreateUserResponse;
import at.fwuick.daheim.model.ErrorResponse;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.JoinHomeRequest;
import at.fwuick.daheim.model.ListStatusResponse;
import at.fwuick.daheim.model.RedactedUser;
import at.fwuick.daheim.model.Response;
import at.fwuick.daheim.model.SetStatusRequest;
import at.fwuick.daheim.model.ShowHomeResponse;
import at.fwuick.daheim.model.Status;
import at.fwuick.daheim.model.User;
import at.fwuick.daheim.model.UserContextRequest;

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
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/create-user")
	public Response createUser(@RequestBody @Valid CreateUserRequest request) {
		User user = new User(request.getName());
		user.setUuid(UUID.randomUUID().toString());
		userDao.insert(user);
		return new CreateUserResponse(user.getUuid());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/join-home")
	public Response joinHome(@RequestBody @Valid JoinHomeRequest request) throws DaheimException {
		User user = userRepository.getNoHomeUser(request.getUuid());
		Home home = homeDao.findByBssidSafe(request.getBssid());
		user.setHome(home.getId());
		userDao.updateHome(user);
		return new Response();

	}


	@RequestMapping(method = RequestMethod.POST, path = "/create-home")
	public Response joinHome(@RequestBody @Valid CreateHomeRequest request) throws DaheimException {
		User user = userRepository.getNoHomeUser(request.getUuid());
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

	@RequestMapping(method = RequestMethod.POST, path = "/check-home")
	public Response checkHome(@RequestBody @Valid JoinHomeRequest request) throws DaheimException {
		userRepository.getNoHomeUser(request.getUuid());
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
	
	@RequestMapping(method = RequestMethod.POST, path = "/list-status")
	public Response listStatus() throws DaheimException {
		List<Status> status = statusDao.getAllStatus();
		return new ListStatusResponse(status);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/show-home")
	public Response showHome(@RequestBody @Valid UserContextRequest request) throws DaheimException {
		User user = userRepository.getHomeUser(request.getUuid());
		Home home = homeDao.get(user.getHome());
		
		ShowHomeResponse response = new ShowHomeResponse();
		response.setHome_name(home.getName());
		List<RedactedUser> users = repo.getUsersOfHome(home).stream()
				.filter(u -> !u.getUuid().equals(request.getUuid()))
				.map(u -> {
					return new RedactedUser(u);
				})
				.collect(Collectors.toList());
		response.setUsers(users);
		return response;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ErrorResponse katch(Exception e) {
		return new ErrorResponse(e.getMessage());
	}

}
