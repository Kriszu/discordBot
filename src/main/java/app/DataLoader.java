package app;

import app.service.BingoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    BingoGameService bingoGameService;
    @Override
    public void run(String... args) throws Exception {
        boolean test = true;

        bingoGameService.createOfficialBingoBoard();

        if(test) {
            File folder = new File("C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().startsWith("bingo") && listOfFiles[i].getName().length() > 10){
                        boolean result = listOfFiles[i].delete();
                        if(!result){
                            System.out.println(listOfFiles[i].getName() + " nie został usunięty");
                        } else {
                            System.out.println(listOfFiles[i].getName() + " został usuniety");
                        }
                    }
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
        }

    }
}
