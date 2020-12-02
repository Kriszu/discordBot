package app.repository;

import app.model.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordUserRepo extends JpaRepository<DiscordUser, Long> {
}
