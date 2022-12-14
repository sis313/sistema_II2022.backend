package ucb.app.mslogin.api;

import ucb.app.mslogin.bl.RestorePasswordBl;
import ucb.app.mslogin.dto.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
public class RestorePasswordAPI {
    private RestorePasswordBl restorePasswordBl;
    private UserEntity userEntity;
    Logger LOGGER = LoggerFactory.getLogger(RestorePasswordAPI.class);

    public RestorePasswordAPI(RestorePasswordBl restorePasswordBl) {
        this.restorePasswordBl = restorePasswordBl;
    }

    @PostMapping(path = "/recover", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> sendEmail(@RequestBody UserEntity userEntity) {
        LOGGER.info("sendEmail from RestorePasswordAPI , {}", userEntity.getEmail());
        int res = restorePasswordBl.sendMailToRestorePass(userEntity.getEmail());

        if (res > 0) {
            return new ResponseEntity<>("A message to reset your password has been sent to your email.", HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no user with the specified email.", HttpStatus.UNPROCESSABLE_ENTITY);
        // Send to recuperacion
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public ResponseEntity<Void> redirectFromMail(@RequestParam("user") String user) {
        LOGGER.info("resendToResetPass from RestorePasswordAPI");
        UserEntity res = restorePasswordBl.confirmUser(user);

        if (res == null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .location(URI.create("https://shell-spa-beta.vercel.app/dev4#/error-recuperacion")).build();
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI
                        .create("https://shell-spa-beta.vercel.app/dev4#/envio-recuperacion?user=" + res.getNickname()))
                .build();
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<String> resetPass(@RequestBody UserEntity userEntity) {
        LOGGER.info("resetPass from RestorePasswordAPI");
        int res = restorePasswordBl.saveNewPassword(userEntity);

        if (res < 0) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Your password has been updated.", HttpStatus.OK);
    }
}
