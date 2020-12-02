package app.service;

import app.model.BingoBoard;
import app.model.BingoGame;
import app.model.DiscordUser;
import net.dv8tion.jda.api.entities.User;
import app.repository.BingoBoardRepo;
import app.repository.BingoGameRepo;
import app.repository.DiscordUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BingoGameService {

    @Autowired
    DiscordUserRepo discordUserRepo;
    @Autowired
    BingoBoardRepo bingoBoardRepo;
    @Autowired
    GameMechanics gameMechanics;

    public void createBingoGameForUser(Long id) {
        if(bingoBoardRepo.findById(1L).isPresent()){
            String[][] officialBingoBoard = bingoBoardRepo.findById(1L).get().getBingoBoard();
            int[][] scoreBoard = gameMechanics.createScoreBoard(3,3);
            DiscordUser discordUserToSave = new DiscordUser();
            BingoGame bingoGame = new BingoGame(discordUserToSave, officialBingoBoard, scoreBoard);
            discordUserToSave.setUserId(id);
            discordUserToSave.setBingoGame(bingoGame);
            discordUserRepo.save(discordUserToSave);
        }
    }

    public BingoGame getBingoGameForUser(User user) {
        return null;
    }

    public List<User> getWinners() {
        return null;
    }

    public void createOfficialBingoBoard() {
        if(bingoBoardRepo.findById(1L).isEmpty()){
            String[][] officialBingoBoard = gameMechanics.createOfficialBingoBoard();
            bingoBoardRepo.save(new BingoBoard(officialBingoBoard));
        } else {
            System.out.println("Bingo istnieje!");
        }
    }

    public String showBingo() {
        return bingoBoardRepo.findById(1L).isPresent() ? bingoBoardRepo.findById(1L).get().toString() : "Kriszu jest chujowym programistą sorki :/";
    }

}
