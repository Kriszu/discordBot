import net.dv8tion.jda.api.entities.User;

public class Bingo {

    static String[][] bingoMap = {{"1", "2", "3"}, {"4","5","6"}, {"7","8","9"}};

    public boolean bingoContainsString(String string){
        int width = bingoMap[0].length;
        int height = bingoMap.length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(bingoMap[i][j].equalsIgnoreCase(string)){
                    return true;
                }
            }
        }
        return false;
    }

    public String bingoToString(){
        String s = "";
        int width = bingoMap[0].length;
        for (int i = 0; i < width; i++) {
            s += bingoMap[0][i] +" ";
        }
        s += "\n";
        for (int i = 0; i < width; i++) {
            s += bingoMap[1][i] +" ";
        }
        s += "\n";for (int i = 0; i < width; i++) {
            s += bingoMap[2][i] +" ";
        }
        s += "\n";
        return s;
    }

    private static int levenshtein(String s, String t)
    {
        int i, j, m, n, cost;
        int d[][];

        m = s.length();
        n = t.length();

        d = new int[m+1][n+1];

        for (i=0; i<=m; i++)
            d[i][0] = i;
        for (j=1; j<=n; j++)
            d[0][j] = j;

        for (i=1; i<=m; i++)
        {
            for (j=1; j<=n; j++)
            {
                if (s.charAt(i-1) == t.charAt(j-1))
                    cost = 0;
                else
                    cost = 1;

                d[i][j] = Math.min(d[i-1][j] + 1,         /* remove */
                        Math.min(d[i][j-1] + 1,      /* insert */
                                d[i-1][j-1] + cost));   /* change */
            }
        }

        return d[m][n];
    }
}
