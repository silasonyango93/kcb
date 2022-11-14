package bank.controller.user_management;

import javax.servlet.http.HttpServletRequest;

import bank.dto.user_management.*;
import bank.entity.user_management.AuthenticationStatus;
import bank.entity.user_management.Roles;
import bank.repository.user_management.RolesRepository;
import bank.service.user_management.retrofit.RetrofitClientInstance;
import bank.service.user_management.retrofit.user_management.UserRetrofitModel;
import bank.service.user_management.retrofit.user_management.UserRetrofitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import bank.entity.user_management.User;
import bank.service.user_management.UserService;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static bank.configuration.EndPoints.NODE_SERVICE_BASE_URL;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}", response = AuthenticationObject.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 422, message = "A user does not exist by this email"),
            @ApiResponse(code = 200, message = "Succesful authentication"),
            @ApiResponse(code = 401, message = "Invalid username/password supplied")})
    public ResponseEntity<AuthenticationObject> login(@ApiParam("Login User") @RequestBody UserLoginDTO userLoginDTO) {
        AuthenticationObject authenticationObject = userService.signin(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        if (authenticationObject.isAuthenticationSuccessful()) {
            return new ResponseEntity<AuthenticationObject>(authenticationObject, HttpStatus.valueOf(200));
        } else if (authenticationObject.getAuthenticationStatus() == AuthenticationStatus.WRONG_CREDENTIALS){
            return new ResponseEntity<AuthenticationObject>(authenticationObject, HttpStatus.valueOf(401));
        } else if(authenticationObject.getAuthenticationStatus() == AuthenticationStatus.USER_DOES_NOT_EXIST) {
            return new ResponseEntity<AuthenticationObject>(authenticationObject, HttpStatus.valueOf(422));
        } else {
            return new ResponseEntity<AuthenticationObject>(authenticationObject, HttpStatus.valueOf(400));
        }
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}", response = SignupStatusDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 422, message = "Email is already in use")})
    public ResponseEntity<SignupStatusDto> signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
        SignupStatusDto signupStatusDto = userService.signup(new User(
                user.getCustomerName(),
                user.getUserEmail(),
                user.getPassword()
        ));

        if (signupStatusDto.getIsSignupSuccessful()) {
            return new ResponseEntity<SignupStatusDto>(signupStatusDto, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<SignupStatusDto>(signupStatusDto, HttpStatus.valueOf(422));
        }
    }

    @DeleteMapping(value = "/{username}")
    @ApiOperation(value = "${UserController.delete}", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Username") @PathVariable String username) {
        userService.delete(username);
        return username;
    }


    @GetMapping(value = "/all-users")
    @ApiOperation(value = "${UserController.all-users}", response = UserResponseDTO.class, responseContainer = "List" ,authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied")})
    public List<UserResponseDTO> getAllUsers() {
        UserRetrofitService userRetrofitService = RetrofitClientInstance.getRetrofitInstance(NODE_SERVICE_BASE_URL).create(UserRetrofitService.class);
        Call<List<UserRetrofitModel>> callSync = userRetrofitService.fetchAllUsers();
        try {
            Response<List<UserRetrofitModel>> response = callSync.execute();
            List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
            for (UserRetrofitModel userRetrofitModel : response.body()) {
                userResponseDTOList.add(new UserResponseDTO(
                        userRetrofitModel.getUserId(),
                        userRetrofitModel.getCountyName(),
                        userRetrofitModel.getUserFirstName(),
                        userRetrofitModel.getUserMiddleName(),
                        userRetrofitModel.getUserSurname(),
                        userRetrofitModel.getUserEmail(),
                        userRetrofitModel.getOrganizationName()
                ));
            }

            return userResponseDTOList;

        } catch (Exception ex) {}

        return null;
    }


    @GetMapping(value = "/all-roles")
    @ApiOperation(value = "${UserController.all-roles}", response = Roles.class, responseContainer = "List" ,authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied")})
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @GetMapping("/refresh")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

}
