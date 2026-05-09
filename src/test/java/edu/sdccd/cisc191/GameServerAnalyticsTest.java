package edu.sdccd.cisc191;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class GameServerAnalyticsTest {

    @Test
    void findTopNUsernamesByRatingReturnsHighestRatedFirst() {
        List<PlayerAccount> players = List.of(
                new PlayerAccount("Ana", 1200, "NA"),
                new PlayerAccount("Leo", 1700, "EU"),
                new PlayerAccount("Mia", 1500, "NA"),
                new PlayerAccount("Zed", 900, "APAC")
        );

        List<String> result = GameServerAnalytics.findTopNUsernamesByRating(players, 3);
        assertEquals(List.of("Leo", "Mia", "Ana"), result);
    }

    @Test
    void averageRatingByRegionBuildsRegionMap() {
        List<PlayerAccount> players = List.of(
                new PlayerAccount("Ana", 1200, "NA"),
                new PlayerAccount("Mia", 1400, "NA"),
                new PlayerAccount("Leo", 1600, "EU")
        );

        Map<String, Double> result = GameServerAnalytics.averageRatingByRegion(players);
        assertEquals(1300.0, result.get("NA"));
        assertEquals(1600.0, result.get("EU"));
    }

    @Test
    void findDuplicateUsernamesReturnsOnlyRepeatedNames() {
        List<PlayerAccount> players = List.of(
                new PlayerAccount("Ana", 1200, "NA"),
                new PlayerAccount("Leo", 1300, "EU"),
                new PlayerAccount("Ana", 1500, "APAC"),
                new PlayerAccount("Mia", 1100, "NA"),
                new PlayerAccount("Leo", 1700, "EU")
        );

        Set<String> duplicates = GameServerAnalytics.findDuplicateUsernames(players);
        assertEquals(Set.of("Ana", "Leo"), duplicates);
    }

    @Test
    void groupUsernamesByTierGroupsPlayersCorrectly() {
        List<PlayerAccount> players = List.of(
                new PlayerAccount("Ana", 900, "NA"),
                new PlayerAccount("Leo", 1200, "EU"),
                new PlayerAccount("Mia", 1399, "NA"),
                new PlayerAccount("Zed", 1800, "APAC")
        );

        Map<String, List<String>> grouped = GameServerAnalytics.groupUsernamesByTier(players);
        assertEquals(List.of("Ana"), grouped.get("Bronze"));
        assertEquals(List.of("Leo", "Mia"), grouped.get("Silver"));
        assertEquals(List.of("Zed"), grouped.get("Gold"));
    }

    @Test
    void buildRecentMatchSummariesByPlayerAddsEachMatchToBothPlayers() {
        PlayerAccount ana = new PlayerAccount("Ana", 1200, "NA");
        PlayerAccount leo = new PlayerAccount("Leo", 1300, "EU");
        PlayerAccount mia = new PlayerAccount("Mia", 1400, "NA");

        List<MatchRecord> matches = List.of(
                new MatchRecord("Volcano", ana, leo, "Leo"),
                new MatchRecord("Temple", mia, ana, "Ana")
        );

        Map<String, List<String>> result = GameServerAnalytics.buildRecentMatchSummariesByPlayer(matches);

        assertEquals(List.of("Ana vs Leo @ Volcano", "Mia vs Ana @ Temple"), result.get("Ana"));
        assertEquals(List.of("Ana vs Leo @ Volcano"), result.get("Leo"));
        assertEquals(List.of("Mia vs Ana @ Temple"), result.get("Mia"));
    }
//
    @Test
    void pickHigherRatedUsesComparatorGenerically() {
        PlayerAccount ana = new PlayerAccount("Ana", 1200, "NA");
        PlayerAccount leo = new PlayerAccount("Leo", 1300, "EU");

        PlayerAccount result = GameServerAnalytics.pickHigherRated(
                ana,
                leo,
                Comparator.comparingInt(PlayerAccount::rating)
        );

        assertEquals(leo, result);
    }

    @Test
    void pickHigherRatedReturnsFirstWhenEqual() {
        String first = "Alpha";
        String second = "Bravo";

        String result = GameServerAnalytics.pickHigherRated(first, second, (a, b) -> 0);
        assertEquals(first, result);
    }
}
