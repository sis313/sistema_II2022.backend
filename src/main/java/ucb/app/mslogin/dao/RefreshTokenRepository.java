package ucb.app.mslogin.dao;

import ucb.app.mslogin.dto.RefreshTokenEntity;
import ucb.app.mslogin.dto.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    int deleteByUserEntity(UserEntity userEntity);
}
