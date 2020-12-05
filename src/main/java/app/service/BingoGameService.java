package app.service;

import app.DiscordConfig;
import app.model.BingoBoard;
import app.model.BingoGame;
import app.model.DiscordUser;
import app.repository.BingoBoardRepo;
import app.repository.DiscordUserRepo;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BingoGameService {

    @Autowired
    DiscordUserRepo discordUserRepo;
    @Autowired
    BingoBoardRepo bingoBoardRepo;
    @Autowired
    GameMechanics gameMechanics;

    DiscordConfig discordConfig = new DiscordConfig();

    public String createBingoGameForUser(MessageReceivedEvent event) throws IOException {
        String result = "";
        if(bingoBoardRepo.findById(1L).isPresent()){
            if(discordUserRepo.findByUserId(event.getAuthor().getIdLong()).isPresent()){
                result = discordConfig.getMessage("BingoAlreadyCreated", event);
            } else {
                String[][] officialBingoBoard = bingoBoardRepo.findById(1L).get().getBingoBoard();
                int[][] scoreBoard = gameMechanics.createScoreBoard(4, 4);
                DiscordUser discordUserToSave = new DiscordUser();
                BingoGame bingoGame = new BingoGame(discordUserToSave, officialBingoBoard, scoreBoard);
                discordUserToSave.setUserId(event.getAuthor().getIdLong());
                discordUserToSave.setName(event.getAuthor().getName());
                discordUserToSave.setBingoGame(bingoGame);
                discordUserRepo.save(discordUserToSave);
                if(discordUserRepo.findByUserId(event.getAuthor().getIdLong()).isPresent()) {
                    result = discordConfig.getMessage("BingoCreated", event);
                } else {
                    result = discordConfig.getMessage("UnexpectedFailure", event);
                }
                gameMechanics.copy(
                        "C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\bingo.png",
                        "C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\bingo"+event.getAuthor().getId()+".png"
                );

            }
        }
        return result;
    }

    public String getBingoGameForUser(MessageReceivedEvent event) {
        String result = "";
        Optional<DiscordUser> optionalDiscordUser = discordUserRepo.findByUserId(event.getAuthor().getIdLong());
        if(optionalDiscordUser.isEmpty()){
            result = discordConfig.getMessage("BingoDoesntExists", event);
        } else {
            result = discordConfig.getMessage("BingoBoard",event);
            event.getChannel().sendFile(new File("C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\bingo"+event.getAuthor().getId()+".png")).queue();
        }
        return result;
    }

    public void createOfficialBingoBoard() {
        if(bingoBoardRepo.findById(1L).isEmpty()){
            String[][] officialBingoBoard = gameMechanics.createOfficialBingoBoard();
            bingoBoardRepo.save(new BingoBoard(officialBingoBoard));
        } else {
            System.out.println("Bingo istnieje!");
        }
    }

    private DiscordUser getMentionedUserForMessage(MessageReceivedEvent event){
       Long id = event.getMessage().getMentionedUsers().get(0).getIdLong();
       return discordUserRepo.getOne(id);
    }

    private BingoGame updateScoreBoard(BingoGame bingoGame, int x, int y){
        BingoGame result;
        int[][] scoreBoard = bingoGame.getScoreBoard();
        scoreBoard[x][y] = 1;
        bingoGame.setScoreBoard(scoreBoard);
        result = bingoGame;
        return result;
    }

    public String okCommand (MessageReceivedEvent event) {
        DiscordConfig discordConfig = new DiscordConfig();
        String bingoWord =  event.getMessage().getContentRaw().replace("!ok @" + event.getMessage().getMentionedUsers().get(0).getName() + " ", "");
        String[][] bingoBoard = gameMechanics.createOfficialBingoBoard();
        DiscordUser discordUserToSave = getMentionedUserForMessage(event);
        if(discordUserToSave.getBingoGame().getScoreBoard() == null){
            return discordConfig.getMessage("BingoDoesntExists", event);
        }
        BingoGame bingoGame = discordUserToSave.getBingoGame();
        for (int i = 0; i < bingoBoard.length; i++) {
            for (int j = 0; j < bingoBoard[0].length; j++) {
                if (!bingoBoard[i][j].contains(bingoWord)) {
                    return discordConfig.getMessage("BingoWordDoesntExsists", event);
                } else if (bingoGame.getScoreBoard()[i][j] == 1){
                    return discordConfig.getMessage("AlreadyHasScore", event);
                } else {
                    discordUserToSave.setBingoGame(updateScoreBoard(bingoGame, i, j));
                    try {
                        gameMechanics.mergeImages(gameMechanics.getBingoForUser(event.getMessage().getMentionedUsers().get(0)), gameMechanics.getXimg(i,j));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return discordConfig.getMessage("ImageMergeFailure", event);
                    }
                    int winPoints = gameMechanics.checkWin(discordUserToSave.getBingoGame().getScoreBoard());
                    if(winPoints > 0){
                        discordUserToSave.setRankPoints(discordUserToSave.getRankPoints() + winPoints);
                        discordUserRepo.save(discordUserToSave);
                        resetScoreBoards();
                        if(!gameMechanics.resetImages()){
                            return discordConfig.getMessage("ImgResetFailure", event);
                        };
                        return discordConfig.getMessage("WinnerAndReset", event);
                    } else {
                        return discordConfig.getMessage("BingoAccepted", event);
                    }
                }
            }
        }
        return discordConfig.getMessage("UnhandledFailure", event);
    }

    private void resetScorBoard(DiscordUser discordUser){
        discordUser.getBingoGame().setScoreBoard(gameMechanics.createScoreBoard(4,4));
        discordUserRepo.save(discordUser);
    }
    private void resetScoreBoards() {
        List<DiscordUser> users = discordUserRepo.findAll();
        users.forEach(this::resetScorBoard);
    }

    public Pair<String, Boolean> bingoForUser(MessageReceivedEvent event) {
        Pair<String, Boolean> result;
        if(discordUserRepo.findByUserId(event.getAuthor().getIdLong()).isEmpty()) {
            result = Pair.of(discordConfig.getMessage("BingoDoesntExists", event), false);
        } else {
            result = Pair.of(discordConfig.getMessage("BingoNotificationForUser", event), true);
        }
        return result;
    }

    public String bingoForMode(MessageReceivedEvent event) {
        return discordConfig.getMessage("BingoNotificationForMode", event);
    }

    public String ranking() {
        List<DiscordUser> users = discordUserRepo.findAll();
        List<DiscordUser> sortedUsers = users.stream().sorted().collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sortedUsers.size(); i++) {
            result.append(i).append(". ").append(sortedUsers.get(i).getName()).append("\n");
        }
        return result.toString();
    }
}
