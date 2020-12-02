package app.model;

import javax.persistence.*;

@Entity
@Table(name = "bingoBoard")
public class BingoBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bingoGame_id")
    private Long id;

    @Column(name = "bingoBoard")
    private String[][] bingoBoard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[][] getBingoBoard() {
        return bingoBoard;
    }

    public void setBingoBoard(String[][] bingoBoard) {
        this.bingoBoard = bingoBoard;
    }

    public BingoBoard(String[][] bingoBoard) {
        this.bingoBoard = bingoBoard;
    }

    public BingoBoard() {
    }
}
