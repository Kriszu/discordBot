package app.model;

import javax.persistence.*;

@Entity
@Table(name = "bingoGame")
public class BingoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bingoGame_id")
    private Long id;

    @OneToOne(mappedBy = "bingoGame")
    private DiscordUser discordUser;

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
}
