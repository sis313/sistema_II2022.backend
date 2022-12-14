package ucb.app.mslogin.api;

import ucb.app.mslogin.bl.UserBl;
import ucb.app.mslogin.dao.UserRepository;
import ucb.app.mslogin.dto.UserEntity;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/api/user")
public class UserAPI {
    private UserBl userBl;
    private UserEntity userEntity;
    private UserRepository userRepository;

    Logger LOGGER = LoggerFactory.getLogger(UserAPI.class);

    public UserAPI(UserBl userBl, UserRepository userRepository) {

        this.userBl = userBl;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/publico/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity) {
        LOGGER.info("addUser from UserAPI");
        UserEntity user = userBl.saveUser(userEntity);
        if (user == null) {
            return new ResponseEntity<>("Error. The email entered does have an associated account.",
                    HttpStatus.PRECONDITION_FAILED);
        } else {
            return new ResponseEntity<>("User added successfully.", HttpStatus.OK);
        }
    }

    @PutMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserEntity> updateRolUser(@RequestBody UserEntity userEntity) {
        LOGGER.info("updateRolUser from UserAPI");
        UserEntity user = userBl.updateUser(userEntity);

        if (user == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<UserEntity>> getUserById(@PathVariable Integer id) {
        LOGGER.info("Invocando al servicio REST para obtener un usuario");
        Optional<UserEntity> user = userBl.findUserByID(id);
        LOGGER.info("DATABASE-SUCCESS: Consulta exitosa para obtener un usuario {}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> getUsers() {
        LOGGER.info("Invocando al servicio REST para obtener todos los usuarios");
        List<UserEntity> userList = userRepository.findAll();
        LOGGER.info("DATABASE-SUCCESS: Consulta exitosa para obtener los usuarios {}", userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping(path = "/username")
    public ResponseEntity<UserEntity> getUser(@RequestParam String username) {
        UserEntity user = userRepository.findUserByUser(username);
        return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
    }

    // CODE CHANGE - START
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("userId") Integer userId) {
        UserEntity response = userBl.deleteByIdDto(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/type={typeUser}/status={status}")
    public ResponseEntity<List<UserEntity>> findUserByTypeAndStatus(@PathVariable("typeUser") Integer tipo,
            @PathVariable("status") Integer status) {
        List<UserEntity> response = userBl.findUserByTypeAndStatus(tipo, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // CODE CHANGE - STOP
}
