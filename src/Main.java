import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class Main {

    private static String filePath = "/home/emre1s/ipl/matches.csv";

    File csvFile1 = new File("matches.csv");
    String row = "";

    public static List<?> extractData(boolean flag ,String... filePath) {

        BufferedReader bufferedReader = null;

        try {

            String line = "";
            File file = new File(filePath[0]);
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.readLine();

            // FOR MATCH FILE
            if (flag) {

                List<Match> matchList = new ArrayList<Match>();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length > 0) {
                        Match match = new Match();
                        match.setMatch_id(Integer.parseInt(fields[0]));
                        match.setSeason(fields[1]);
                        match.setWinner(fields[10]);

                        matchList.add(match);
                    }
                }

                return matchList;
            }

            else {

                List<BowlData> bowlDataList = new ArrayList<>();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length > 0) {
                        BowlData bowlData = new BowlData();
                        bowlData.setMatch_id(Integer.parseInt(fields[0]));
                        bowlData.setBowlingTeam(fields[3]);
                        bowlData.setBowler(fields[8]);
                        bowlData.setExtra_runs(Integer.parseInt(fields[16]));
                        bowlData.setTotal_runs(Integer.parseInt(fields[17]));

                        bowlDataList.add(bowlData);
                    }
                }

                return bowlDataList;
            }
        }

        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        finally {

            try {
                bufferedReader.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


//        List<Match> matchList = new ArrayList<Match>();
//
//        Match match = new Match(1,"2017","RCB",2);
//        matchList.add(match);
//
//        match = new Match(2, "2017", "MI", 3);
//        matchList.add(match);
//
//        match = new Match(3, "2017", "CSK", 1);
//        matchList.add(match);
//
//        System.out.println(matchList.get(0)+ " " + matchList.get(1))

    public static void main(String[] args) {

        String matchFile = "/home/emre1s/ipl/matches.csv";
        String deliveryFile = "/home/emre1s/ipl/deliveries.csv";

        List<Match> matchList = (List<Match>) extractData(true,matchFile);
        List<BowlData> bowlDataList = (List<BowlData>) extractData(false, deliveryFile);
        List<BowlData> bowlDataList_Q3 = null;
        List<BowlData> bowlDataList_Q4 = null;

        Map<String, Integer> queryOne = new HashMap<>();
        Map<String, Integer> teamWins_queryTwo = new HashMap<>();

        Map<String, Map<String, Integer>> queryTwo = new HashMap<>();
        Map<String, Integer> queryThree = new HashMap<>();

        Map<String, ArrayList<Integer>> queryFour = new HashMap<>();
        ArrayList<Integer> bowlerStats = new ArrayList<>();
        Map<Double, String> econRate = new HashMap<>();

        List<Integer> matchID_queryThree = new ArrayList<>();
        List<Integer> matchId_queryFour = new ArrayList<>();

        String tempKey = "";

        if (!matchList.isEmpty()) {

            for (Match match : matchList) {

               // System.out.println(match);

                String season = match.getSeason();
                String winner = match.getWinner();

                if (season.equals("2016"))                              //FOR QUERY THREE
                    matchID_queryThree.add(match.getMatch_id());

                else if (season.equals("2015"))                         // FOR QUERY FOURs
                    matchId_queryFour.add(match.getMatch_id());


                if (tempKey.isEmpty())
                    tempKey = season;

                //QUERY ONE STARTS
                if (queryOne.containsKey(season)) {
                    int value = queryOne.get(season) + 1;
                    queryOne.put(season, value);
                } else
                    queryOne.put(season, 1);
                //QUERY ONE ENDS


                //QUERY TWO STARTS
                if (winner.isEmpty())
                    continue;

                if (!tempKey.equals(season)) {

                    tempKey = season;
                    teamWins_queryTwo = new HashMap<>();
                }


                if (teamWins_queryTwo.containsKey(winner)) {
                    int value = teamWins_queryTwo.get(winner) + 1;
                    teamWins_queryTwo.put(winner, value);
                } else
                    teamWins_queryTwo.put(winner, 1);

                queryTwo.put(tempKey, teamWins_queryTwo);

            }

            //QUERY TWO ENDS

            //matchID_queryThree = matchID_queryThree.stream().distinct().collect(Collectors.toList());
            //matchId_queryFour = matchId_queryFour.stream().distinct().collect(Collectors.toList());

            final List<Integer> a = matchID_queryThree;


            Object[] keys_queryOne = queryOne.keySet().toArray();
            Arrays.sort(keys_queryOne, Collections.reverseOrder());

            System.out.println("\n ----MATCHES PLAYED EVERY YEAR----");

            for (Object key : keys_queryOne) {
                System.out.println(MessageFormat.format("YEAR {0} : MATCHES {1}", key, queryOne.get(key)));
            }

            System.out.println("\n <------- YEAR WISE TEAM WINS --------->");

            for (Object key : keys_queryOne) {

                System.out.println(MessageFormat.format("------------ YEAR: {0} ----------", key));
                System.out.println("\t");

                HashMap<String, Integer> tempMap = (HashMap<String, Integer>) queryTwo.get(key);

                for (Object teamKey: tempMap.keySet()) {
                    System.out.println(MessageFormat.format("TEAM: {0} : WINS: {1}", teamKey, tempMap.get(teamKey)));
                }

                System.out.println();
            }

            int queryThree_initial = matchID_queryThree.get(0);
            int queryThree_final = matchID_queryThree.get(matchID_queryThree.size()-1);

            int queryFour_initial = matchId_queryFour.get(0);
            int queryFour_final = matchId_queryFour.get(matchId_queryFour.size()-1);

            bowlDataList_Q3 = bowlDataList.stream().filter(x -> x.getMatch_id() >= queryThree_initial && x.getMatch_id() <= queryThree_final).collect(Collectors.toList());
            bowlDataList_Q4 = bowlDataList.stream().filter(x -> x.getMatch_id() >= queryFour_initial && x.getMatch_id() <= queryFour_final).collect(Collectors.toList());



        }

        else {
            System.out.println("List is empty");
        }

        //String queryThree_teamName = "";

        if (!bowlDataList_Q3.isEmpty()) {

            for (BowlData bowl : bowlDataList_Q3) {

                String bowlingTeam = bowl.getBowlingTeam();

                if (queryThree.containsKey(bowlingTeam)) {
                    int value = queryThree.get(bowlingTeam) + bowl.getExtra_runs();
                    queryThree.put(bowlingTeam, value);
                }
                else
                    queryThree.put(bowlingTeam, bowl.getExtra_runs());

            }
        }

        else {
            System.out.println("List is empty (Query 3)");
        }

        System.out.println("\n --------- YEAR 2016 EXTRA RUNS ---------");
        for (String key: queryThree.keySet()) {
            System.out.println(MessageFormat.format("TEAM: {0} : EXTRA RUNS: {1}", key, queryThree.get(key)));
        }


        if(!bowlDataList_Q4.isEmpty()) {
            for (BowlData b : bowlDataList_Q4) {
//                System.out.println(b);

                String bowlerName = b.getBowler();
                if (queryFour.containsKey(bowlerName)) {
                    ArrayList<Integer> ballStat = queryFour.get(bowlerName);
                    int ball = ballStat.get(0) + 1;
                    int runs = ballStat.get(1) + b.getTotal_runs();

                    ballStat.add(0,ball);
                    ballStat.add(1, runs);

                    queryFour.put(bowlerName, ballStat);
                }

                else {
                    ArrayList<Integer> ballStat = new ArrayList<>();
                    ballStat.add(1);
                    ballStat.add(b.getTotal_runs());
                    queryFour.put(bowlerName, ballStat);
                }

            }
        }

        else {
            System.out.println("List is empty (Query 4)");
        }

        System.out.println("\n------ YEAR 2015 TOP 10 ECONOMIES----------");
        for (String key: queryFour.keySet()) {
            ArrayList<Integer> tempList = queryFour.get(key);
            double overs = tempList.get(0)/6;
            int runs = tempList.get(1);
            double econ = runs / overs;
            econRate.put(econ, key);
            //System.out.println(MessageFormat.format("BOWLER: {0} BALLS: {1} RUNS: {2}", key, tempList.get(0), tempList.get(1)));
        }

        Object[] economies =  econRate.keySet().toArray();
        Arrays.sort(economies);
        for (int i =0; i<10; i++) {
            Double economy = (Double) economies[i];
            System.out.println(MessageFormat.format("BOWLER: {0} : ECONOMY RATE: {1}",econRate.get(economy), economy));
        }
    }
}


