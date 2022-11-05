const badLineup = [
    {
        playerId: 132,
        position: "QB",
    },
];

const players = [
    {
        playerId: 123,
        teamId: 222,
        name: "Joe",
        salary: 5000,
    },
];

const contest = {
    id: 123,
    salaryCap: 90000,
    positionsRequired: [
        { name: "Q", count: 1 },
        { name: "RB", count: 2 },
        { name: "WR", count: 2 },
    ],
    games: [
        {
            gameId: 343,
            homeTeamId: 222,
            awayTeamId: 444,
        },
    ],
};

function validate(lineup) {
    let budget = contest.salaryCap;

    // Easy access to player meta data
    const playersById = {};

    // Mapping of position and count
    const positionsMapped = {};

    // How many players from the given team
    const playersFromTeam = {};

    // Number of required players
    let totalPlayers = 0;

    const usedPlayers = new Set();

    // Map players for easy access
    for (playerFull of players) {
        playersById[playerFull.playerId] = playerFull;
    }

    contest.positionsRequired.map(({ name, count }) => {
        positionsMapped[name] = count;
        totalPlayers += count;
    });

    const teamsInvolved = new Set();

    contest.games.map(({ homeTeamId, awayTeamId }) => {
        teamsInvolved.add(homeTeamId);
        teamsInvolved.add(awayTeamId);
    });

    // The lineup cannot contain more than the required amount of players
    if (totalPlayers !== lineup.length) {
        return false;
    }

    for (const player of lineup) {
        budget -= playersById[player.playerId].salary;

        // Any single player can only be used once
        if (usedPlayers.has(player.playerId)) {
            return false;
        }

        usedPlayers.add(player.playerId);

        // The lineup must encompass at least two games
        if (teamsInvolved.has(playersById[player.playerId].teamId)) {
            return flase;
        }

        // Unknown position or we used all slots
        // All roster positions listed in the contest must be filled by the lineup
        if (
            player.position in positionsMapped === false ||
            positionsMapped[player.position] < 0
        ) {
            return false;
        }

        const currentTeam = playersFromTeam[playersById[player.playerId]].teamId;

        playersFromTeam[currentTeam] = playersFromTeam[currentTeam] || 0;
        playersFromTeam[currentTeam] += 1;

        // There can not be more than 3 players on a single team per game
        if (playersFromTeam[currentTeam] > 3) {
            return false;
        }

        // The sum of player salary cannot exceed the contests max salary cap
        if (budget < 0) {
            return false;
        }
    }

    return true;
}

console.assert(validate(badLineup), false);