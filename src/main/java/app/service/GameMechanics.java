package app.service;

import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class GameMechanics {


    public void mergeImages(String bingoBoard, String xImg) throws IOException {
        BufferedImage overlay = ImageIO.read(new File(xImg));
        BufferedImage image = ImageIO.read(new File(bingoBoard));

        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);
        g.dispose();
        boolean result = ImageIO.write(combined, "PNG", new File(bingoBoard));
        System.out.println(result);
    }
    public void copy(String s1, String s2) throws IOException {
        File src = new File(s1);
        File dst = new File(s2);
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public String userBingo(String[][] bingoWords, int[][] score){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bingoWords.length; i++) {
            for (int j = 0; j <bingoWords[0].length ; j++) {
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String beautyString(int[][] s) {
        String result = "";
        for (int i = 0; i < s[0].length ; i++) {
            for (int j = 0; j < s.length; j++) {
                result += s[i][j] + ", ";
            }
            result += "\n";
        }
        return result;
    }

    int[][] createScoreBoard(int x, int y){
        int[][] scoreboard = new int[x][y];
        Random rand = new Random();
        for (int i = 0; i < x ; i++) {
            for (int j = 0; j < y; j++) {
                scoreboard[i][j] = 0;
            }
        }
        scoreboard[x/2][y/2] = 1;
        return  scoreboard;

    }

    String[][] createOfficialBingoBoard(){
        String[][] bingoBoard = {{"oceńcie sytucję w jaiej się znalazłem", "taki jeden", "serwer umiera", "błagam", "co myślisz o aborcji"},
                {"pics or mitoman", "hej, masz pożyczyć 300zł?", "fajnie, nikt nie pytał", "Warszawa to gówno", "dogekek"},
                {"nie geomaguj", "ryb wrzuca cringe poza swój kanał", "BINGO", "karmelkowa pokaż cycki", "razor to bawidamek"},
                {"kurwy jebane", "wyjebcie meszuge", "ktoś wychodzi z serwera i później wraca", "spermiarze", "dajcie mi seks"},
                {"prrt zesrał się", "kij w dupie", "ja jestem Colek", "eclipse chuju", "obiad za mniej niż 26zł"}};
        return bingoBoard;
    }


    public String getBingoForUser(User user){
        return "C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\bingo"+user.getId()+".png";
    }

    public int checkWin(int[][] score) {

        int wins = 0;

        for (int row = 0; row < score.length; row++) {
            int rowSum = 0;
            for (int col = 0; col < score[row].length; col++) {
                rowSum += score[row][col];
            }
            if (rowSum == score.length) {
                wins++;
            }
        }
        for (int col = 0; col < score[0].length; col++) {
            int colSum = 0;
            for (int row = 0; row < score.length; row++) {
                colSum += score[row][col];
            }
            if (colSum == score.length) {
                wins++;
            }

        }
        int total = 0;

        for (int row = 0; row < score.length; row++) {
            total += score[row][row];
        }
        if(total == 3)
            wins++;
        int r_sum = 0;
        for(int j = 0; j<score.length ; j++){
            r_sum+=score[j][score.length-1-j];
        }
        if(r_sum == 3)
            wins++;

        return wins;
    }

    public String getXimg(int i, int j) {
        return "C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\x"+ i + j +".png";
    }

    public boolean resetImages() {
        File folder = new File("C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\");
        String bingoTable = "C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\bingo.png";
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().startsWith("bingo") && listOfFiles[i].getName().length() > 10) {
                    try {
                        copy(bingoTable, listOfFiles[i].getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
