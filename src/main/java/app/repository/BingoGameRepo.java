package app.repository;

import app.model.BingoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BingoGameRepo extends JpaRepository<BingoGame, Long> {
}
