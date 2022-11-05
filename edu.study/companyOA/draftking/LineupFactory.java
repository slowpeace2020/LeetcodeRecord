package companyOA.draftking;

import java.util.List;
import java.util.Map;

public class LineupFactory {
    /// <summary>
    /// Determines whether the given lineup is valid
    /// </summary>
    /// <param name="contest">The contest for which the given lineup has been submitted</param>
    /// <param name="lineup">The list of players on a fantasy team</param>
    /// <returns>Whether the lineup is valid according to the given rules</returns>
    public boolean validLineup(Contest contest, List<TeamPlayer> lineup) {

        return false;
    }

    class Contest {
        private Map<FantasyPosition, Integer> rosterPositionCount;
        private int maximumSalaryCap;
        private int id;

        public Contest(Map<FantasyPosition, Integer> rosterPositionCount,
                       int maximumSalaryCap, int id) {
            this.rosterPositionCount = rosterPositionCount;
            this.maximumSalaryCap = maximumSalaryCap;
            this.id = id;
        }

        public Map<FantasyPosition, Integer> getRosterPositionCount() {
            return rosterPositionCount;
        }

        public void setRosterPositionCount(Map<FantasyPosition, Integer> rosterPositionCount) {
            this.rosterPositionCount = rosterPositionCount;
        }

        public int getMaximumSalaryCap() {
            return maximumSalaryCap;
        }

        public void setMaximumSalaryCap(int maximumSalaryCap) {
            this.maximumSalaryCap = maximumSalaryCap;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    class TeamPlayer {
        private FantasyPosition fantasyPosition;
        private Player player;

        public FantasyPosition getFantasyPosition() {
            return fantasyPosition;
        }

        public void setFantasyPosition(FantasyPosition fantasyPosition) {
            this.fantasyPosition = fantasyPosition;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }

    class Player {
        private int id;
        private TeamPosition position;
        private int teamId;
        private int nextGameId;
        private int salary;
        private int avgPointsPerGame;

        public Player(int id, TeamPosition position, int teamId, int nextGameId, int salary, int avgPointsPerGame) {
            this.id = id;
            this.position = position;
            this.teamId = teamId;
            this.nextGameId = nextGameId;
            this.salary = salary;
            this.avgPointsPerGame = avgPointsPerGame;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TeamPosition getPosition() {
            return position;
        }

        public void setPosition(TeamPosition position) {
            this.position = position;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public int getNextGameId() {
            return nextGameId;
        }

        public void setNextGameId(int nextGameId) {
            this.nextGameId = nextGameId;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getAvgPointsPerGame() {
            return avgPointsPerGame;
        }

        public void setAvgPointsPerGame(int avgPointsPerGame) {
            this.avgPointsPerGame = avgPointsPerGame;
        }
    }

    class FantasyPosition {
        private List<TeamPosition> allowedPositions;
        private String name;
        private int id;

        public FantasyPosition(List<TeamPosition> allowedPositions, String name, int id) {
            this.allowedPositions = allowedPositions;
            this.name = name;
            this.id = id;
        }

        public List<TeamPosition> getAllowedPositions() {
            return allowedPositions;
        }

        public void setAllowedPositions(List<TeamPosition> allowedPositions) {
            this.allowedPositions = allowedPositions;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    class TeamPosition {
        private String name;
        private int id;

        public TeamPosition(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
