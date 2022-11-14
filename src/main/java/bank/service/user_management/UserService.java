package bank.service.user_management;

import javax.servlet.http.HttpServletRequest;

import bank.dto.user_management.AuthenticationObject;
import bank.dto.user_management.RoleAssignmentDto;
import bank.dto.user_management.SignupStatusDto;
import bank.dto.user_management.SimplifiedUserRolesDto;
import bank.entity.user_management.AuthenticationStatus;
import bank.entity.user_management.Roles;
import bank.entity.user_management.UserRoles;
import bank.repository.user_management.RolesRepository;
import bank.repository.user_management.UserRolesRepository;
import bank.service.user_management.retrofit.RetrofitClientInstance;
import bank.service.user_management.retrofit.user_management.UserRetrofitService;
import bank.service.user_management.retrofit.user_management.UserRolesRetrofitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bank.exception.CustomException;
import bank.entity.user_management.User;
import bank.repository.user_management.UserRepository;
import bank.security.JwtTokenProvider;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static bank.configuration.EndPoints.NODE_SERVICE_BASE_URL;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRolesRepository userRolesRepository;

  @Autowired
  private RolesRepository rolesRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  public AuthenticationObject signin(String email, String attemtedPassword) {

    User user = userRepository.findByUserEmail(email);
    if (user == null) {
      return new AuthenticationObject(false, null,null,null,AuthenticationStatus.USER_DOES_NOT_EXIST);
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (passwordEncoder.matches(attemtedPassword, user.getEncryptedPassword())) {
      return new AuthenticationObject(true
              , jwtTokenProvider.createToken(email)
              ,user.getCustomerName()
              ,user.getUserEmail(),
              AuthenticationStatus.SUCCESSFUL_AUTHENTICATION);
    } else {
      return new AuthenticationObject(false, null,null,null,AuthenticationStatus.WRONG_CREDENTIALS);
    }
  }

  public SignupStatusDto signup(User user) {
    if (!userRepository.existsByUserEmail(user.getUserEmail())) {
      user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));
      userRepository.save(user);
      return new SignupStatusDto(true,"User registration successful");
    } else {
      return new SignupStatusDto(false,"A user already exists by this email");
    }
  }

  public void assignAUserRoles(int userId) {
    List<Roles> roles = rolesRepository.findAll();
    for (Roles role : roles) {
      userRolesRepository.save(new UserRoles(
              userId,
              role.getRoleId(),
              0
      ));
    }
  }

  public void designateSelectedRoles(int userId, List<RoleAssignmentDto> rolesToBeAssigned) {
    for (RoleAssignmentDto roleToBeAssigned : rolesToBeAssigned) {
      UserRoles userRole = fetchAUserSpecificRoleByRoleId(userId, roleToBeAssigned.getRoleId());
      userRole.setConfirmationStatus(1);
      userRolesRepository.save(userRole);
    }
  }

  public UserRoles fetchAUserSpecificRoleByRoleId(int userId, int roleId) {
    UserRetrofitService userRetrofitService = RetrofitClientInstance.getRetrofitInstance(NODE_SERVICE_BASE_URL).create(UserRetrofitService.class);
    Call<UserRolesRetrofitModel> callSync = userRetrofitService.fetchAUserSpecificRolesByUserIdAndRoleId(userId,roleId);
    try {
      Response<UserRolesRetrofitModel> response = callSync.execute();
      return userRolesRepository.findByUserRoleId(response.body().getUserRoleId());

    } catch (Exception ex) {}

    return null;
  }

  public void delete(String userEmail) {
    userRepository.deleteByUserEmail(userEmail);
  }

  public User search(String userEmail) {
    User user = userRepository.findByUserEmail(userEmail);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  public User whoami(HttpServletRequest req) {
    return userRepository.findByUserEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String userEmail) {
    return jwtTokenProvider.createToken(userEmail);
  }


  public List<SimplifiedUserRolesDto> getAUserSimplifiedRoles(int userId) {
    UserRetrofitService userRetrofitService = RetrofitClientInstance.getRetrofitInstance(NODE_SERVICE_BASE_URL).create(UserRetrofitService.class);
    Call<List<UserRolesRetrofitModel>> callSync = userRetrofitService.fetchAUserRolesByUserId(userId);
    try {
      Response<List<UserRolesRetrofitModel>> response = callSync.execute();
      List<SimplifiedUserRolesDto> simplifiedUserRolesDtoList = new ArrayList<>();

      for (UserRolesRetrofitModel userRolesRetrofitModel : response.body()) {
        simplifiedUserRolesDtoList.add(new SimplifiedUserRolesDto(userRolesRetrofitModel.getRoleDescription(),userRolesRetrofitModel.getConfirmationStatus() == 1));
      }

      return simplifiedUserRolesDtoList;

    } catch (Exception ex) {}

    return null;
  }

}
