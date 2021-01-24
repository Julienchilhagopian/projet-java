package Controller;

public class Ranking implements Comparable<Ranking> {
    private String nickname;
    private int score;

    public String getPseudo() {
        return nickname;
    }

    public int getScores() {
        return score;
    }

    @Override
    public String toString() {
        return "Ranking [nickname=" + nickname + ", score=" + score + "]";
    }

    public Ranking(String lines, String separator) {
        String[] linesTab = lines.split(separator);
        this.nickname = linesTab[0];
        this.score = Integer.parseInt(linesTab[1]);
    }

    /**
     * Compare player scores and order them in descending order from best to worst.
     *
     * @param rank corresponding to a player and a score
     * @return an integer : -1, 0 or 1 depending on the comparison between the
     * player's score and the scores of others.
     */
    @Override
    public int compareTo(Ranking rank) {
        int score1 = rank.getScores();
        int score2 = this.getScores();
        if (score1 > score2)
            return -1;
        else if (score1 == score2)
            return 0;
        else
            return 1;
    }

}
