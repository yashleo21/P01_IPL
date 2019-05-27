import java.text.MessageFormat;

public class BowlData {

    private int match_id;
    private String bowler;

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    private String bowlingTeam;
    private int extra_runs;
    private int total_runs;

    public BowlData(int match_id, String bowler, String bowlingTeam, int extra_runs, int total_runs) {
        this.match_id = match_id;
        this.bowler = bowler;
        this.extra_runs = extra_runs;
        this.total_runs = total_runs;
        this.bowlingTeam = bowlingTeam;
    }

    public BowlData() {

    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public int getExtra_runs() {
        return extra_runs;
    }

    public void setExtra_runs(int extra_runs) {
        this.extra_runs = extra_runs;
    }

    public int getTotal_runs() {
        return total_runs;
    }

    public void setTotal_runs(int total_runs) {
        this.total_runs = total_runs;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Match ID: {0} Bowling Team: {1} Bowler: {2} Extra runs: {3}", getMatch_id(), getBowlingTeam() ,getBowler(), getExtra_runs());
    }
}
