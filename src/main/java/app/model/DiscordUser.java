package app.model;

import javax.persistence.*;

@Entity
@Table(name = "discordUser")
public class DiscordUser {

    @Id
    @Column(name = "user_dbId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bingoGame_id", referencedColumnName = "bingoGame_id")
    private BingoGame bingoGame;

    private Long userId;

    public BingoGame getBingoGame() {
        return bingoGame;
    }

    public void setBingoGame(BingoGame bingoGame) {
        this.bingoGame = bingoGame;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DiscordUser() {
    }
}
