package ucb.app.mslogin.dao.entityMapper;

import ucb.app.mslogin.api.AuthController;
import ucb.app.mslogin.dao.UserPrincipal;
import ucb.app.mslogin.dto.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserMapper {
    private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public static UserPrincipal userToPrincipal(UserEntity user) {
        UserPrincipal userPrincipal = new UserPrincipal();
        boolean status = false;
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
        userPrincipal.setUsername(user.getNickname());
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setAuthorities(authorities);
        if (user.getStatus() == 1)
            status = true;
        if (user.getStatus() == 0)
            status = false;
        // status = (Objects.equals(user.getStatus(), "Active")||
        // Objects.equals(user.getStatus(), "Pending")) ;
        userPrincipal.setEnabled(status);
        LOGGER.info(userPrincipal.getUsername());
        LOGGER.info(userPrincipal.getPassword());
        LOGGER.info(userPrincipal.getAuthorities().toString());
        return userPrincipal;
    }
}
