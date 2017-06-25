package at.fwuick.daheim.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import at.fwuick.daheim.dao.ResidenceRepository;
import at.fwuick.daheim.model.CheckHomeObject;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.HomeUserStatus;
import at.fwuick.daheim.model.RedactedUser;
import at.fwuick.daheim.model.Status;
import at.fwuick.daheim.model.User;
import at.fwuick.daheim.model.UserHomeReq;
import at.fwuick.daheim.model.UserIdentity;
import at.fwuick.daheim.model.requests.CreateHomeRequest;
import at.fwuick.daheim.model.requests.CreateUserRequest;
import at.fwuick.daheim.model.requests.HomeReqResponseRequest;
import at.fwuick.daheim.model.requests.HomeContextRequest;
import at.fwuick.daheim.model.requests.SetStatusRequest;
import at.fwuick.daheim.model.requests.UserContextRequest;
import at.fwuick.daheim.model.response.CheckHomeResponse;
import at.fwuick.daheim.model.response.CreateUserResponse;
import at.fwuick.daheim.model.response.ErrorResponse;
import at.fwuick.daheim.model.response.GetHomeRequestsResponse;
import at.fwuick.daheim.model.response.GetUserHomeStatusResponse;
import at.fwuick.daheim.model.response.ListStatusResponse;
import at.fwuick.daheim.model.response.Response;
import at.fwuick.daheim.model.response.ShowHomeResponse;
import at.fwuick.daheim.repository.HomeValidatedRepository;
import at.fwuick.daheim.repository.UserValidatedRepository;

@RestController
public class DaheimService {

  private static final Response SUCCESS = new Response();
  @Autowired
  UserDao userDao;

  @Autowired
  HomeDao homeDao;
  
  @Autowired
  HomeValidatedRepository homeRepository;

  @Autowired
  ResidenceRepository repo;

  @Autowired
  StatusDao statusDao;

  @Autowired
  StatusRepository statusRepository;

  @Autowired
  UserValidatedRepository userRepository;

  @Autowired
  ResidenceRepository userHomeRepository;
  
  @Autowired
  Logger log;

  @RequestMapping(method = RequestMethod.POST, path = "/create-user")
  public Response createUser(@RequestBody @Valid CreateUserRequest request) {
    User user = new User(request.getName());
    user.setUuid(UUID.randomUUID().toString());
    userDao.insert(user);
    return new CreateUserResponse(user.getUuid());
  }

  @RequestMapping(method = RequestMethod.POST, path = "/join-home")
  public Response joinHome(@RequestBody @Valid HomeContextRequest request) throws DaheimException {
    User user = userRepository.get(request.getUserIdentity());
    Home home = homeRepository.get(request.getHomeIdentity());
    userHomeRepository.validateRequestNotExists(user);
    userHomeRepository.createRequest(user, home);
    return SUCCESS;
  }

  @RequestMapping(method = RequestMethod.POST, path = "/create-home")
  public Response joinHome(@RequestBody @Valid CreateHomeRequest request) throws DaheimException {
    User user = userRepository.get(request.getUserIdentity());
    if (homeDao.bssidExists(request.getBssid())) {
      throw new DaheimException(Errors.HOME_ALREADY_EXISTS);
    }
    Home home = new Home();
    home.setName(request.getName());
    home.setBssid(request.getBssid());
    homeDao.insert(home);
    user.setHome(home.getId());
    userDao.updateHome(user);
    return SUCCESS;
  }

  @RequestMapping(method = RequestMethod.POST, path = "/check-home")
  public Response checkHome(@RequestBody @Valid HomeContextRequest request) throws DaheimException {
    userRepository.get(request.getUserIdentity());
    Home h = homeRepository.get(request.getHomeIdentity());

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
    User user = userDao.get(request.getUserIdentity());
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
    User user = userRepository.getHomeUser(request.getUserIdentity());
    Home home = homeDao.get(user.getHome());

    ShowHomeResponse response = new ShowHomeResponse();
    response.setHome_name(home.getName());
    List<RedactedUser> users = repo.getUsersOfHome(home).stream().filter(u -> !u.getUuid().equals(request.getUuid())).map(u -> {
      return new RedactedUser(u);
    }).collect(Collectors.toList());
    response.setUsers(users);
    return response;
  }

  @RequestMapping(method = RequestMethod.POST, path = "/get-home-status")
  public Response getUserHomeStatus(@RequestBody @Valid UserContextRequest request) throws DaheimException {
    User user = userDao.get(request.getUserIdentity());

    HomeUserStatus status;
    if (userHomeRepository.hasHome(user)) {
      status = HomeUserStatus.HAS_HOME;
    } else if (userHomeRepository.hasRequest(user)) {
      status = HomeUserStatus.REQUESTED;
    } else {
      status = HomeUserStatus.HOMELESS;
    }

    return new GetUserHomeStatusResponse(status);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/get-home-requests")
  public Response getHomeRequests(@RequestBody @Valid UserContextRequest request) throws DaheimException {
    User user = userRepository.getHomeUser(request.getUserIdentity());
    Home home = userHomeRepository.getHomeOfUser(user);
    List<UserHomeReq> reqs = userHomeRepository.getHomeRequests(home);
    List<User> users = reqs.stream().map(r -> r.getUser()).map(UserIdentity::id).map(userDao::get).collect(Collectors.toList());
    return new GetHomeRequestsResponse(users);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/answer-request")
  public Response answerRequest(@RequestBody @Valid HomeReqResponseRequest request) throws DaheimException {
    User user = userRepository.getHomeUser(request.getUserIdentity());
    Home home = homeDao.get(user.getHome());
    User userToAdd = userRepository.getNoHomeUser(UserIdentity.id(request.getUser()));
    userHomeRepository.validateRequestExists(userToAdd, home);
    if (request.getAccept()) {
      userHomeRepository.addUserToHome(home, userToAdd);
    } else {
      userHomeRepository.removeRequest(home, userToAdd);
    }
    return SUCCESS;
  }

  @ExceptionHandler(DaheimException.class)
  public ErrorResponse katch(DaheimException e) {
    return new ErrorResponse(e.getMessage());
  }
  
  @ExceptionHandler(Exception.class)
  public ErrorResponse katch(Exception e) {
	e.printStackTrace();
    return new ErrorResponse(e.getMessage());
  }

}
