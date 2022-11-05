package companyOA.draftking;

import java.util.ArrayList;
import java.util.List;

public class FantasyLineUp {
    class Lineup {
        List<Team> teams;
        Integer salarySum;
        Integer salaryRequest;
    }

    class LineupMember{
        Integer playerId;
        String position;

        public LineupMember(Integer playerId, String position) {
            this.playerId = playerId;
            this.position = position;
        }
    }

    class Player {
        Integer playerId;
        Integer teamId;
        String name;
        Integer salary;

        public Player(Integer playerId, Integer teamId, String name, Integer salary) {
            this.playerId = playerId;
            this.teamId = teamId;
            this.name = name;
            this.salary = salary;
        }
    }

    class Team{
        Integer teamId;
        List<Player> players;
        Integer salarySum;

        public Team(Integer teamId) {
            this.teamId = teamId;
            this.players = new ArrayList<>();
        }

        public void addPlayer(Player player){
            players.add(player);
            salarySum+=player.salary;
        }

    }


//    public boolean validate(List<Player> players,List<String> requestPositions,Integer limitSalary){
//
//    }


}
