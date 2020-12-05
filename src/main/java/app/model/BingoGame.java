package app.model;

import app.service.GameMechanics;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "bingoGame")
public class BingoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bingoGame_id")
    private Long id;

    @OneToOne(mappedBy = "bingoGame")
    private DiscordUser discordUser;

    @Lob
    @Column(name = "board")
    private String[][] board;

    public BingoGame() {
    }

    @Column(name = "scoreBoard")
    private int[][] scoreBoard;

    public BingoGame(DiscordUser discordUser, String[][] board, int[][] scoreBoard) {
        this.discordUser = discordUser;
        this.board = board;
        this.scoreBoard = scoreBoard;
    }

    public int[][] getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(int[][] scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @Override
    public String toString() {
        GameMechanics gameMechanics = new GameMechanics();
        return gameMechanics.beautyString(scoreBoard);
    }
}
