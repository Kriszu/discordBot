package app.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "discordUser")
public class DiscordUser implements Comparable<DiscordUser> {

    @Id
    @Column(name = "user_dbId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bingoGame_id", referencedColumnName = "bingoGame_id")
    private BingoGame bingoGame;

    private Long userId;

    private String name;

    private int rankPoints;

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

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


    @Override
    public int compareTo(@NotNull DiscordUser o) {
        if(rankPoints > o.getRankPoints()){
            return 1;
        } else if(rankPoints == o.getRankPoints()){
            return 0;
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
