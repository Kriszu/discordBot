package app.repository;

import app.model.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscordUserRepo extends JpaRepository<DiscordUser, Long> {

    Optional<DiscordUser> findByUserId(Long userId);

}
