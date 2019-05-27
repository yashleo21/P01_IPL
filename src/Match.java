import java.text.MessageFormat;

public class Match {

    private int match_id;
    private String season;
    private String winner;


    public Match(int match_id, String year, String winner) {
        this.match_id = match_id;
        this.season = year;
        this.winner = winner;
    }

    public Match() {

    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String year) {
        this.season = year;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }



    @Override
    public String toString() {
        return MessageFormat.format("Match ID: {0}\t YEAR: {1}\t Winner: {2}", getMatch_id(), getSeason(), getWinner());
    }
}
