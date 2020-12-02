package app.service;

import org.springframework.stereotype.Service;

@Service
public class GameMechanics {

    int[][] createScoreBoard(int x, int y){
        int[][] scoreboard = new int[x][y];

        for (int i = 0; i < x ; i++) {
            for (int j = 0; j < y; j++) {
                scoreboard[i][j] = 0;
            }
        }
        return  scoreboard;

    }

    String[][] createOfficialBingoBoard(){
        String[][] bingoBoard = new String[3][3];
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                bingoBoard[i][j] = "0";
            }
        }
        return bingoBoard;
    }
}
