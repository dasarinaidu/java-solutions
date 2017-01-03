import java.util.*;


public class ScoreKeeper {

  private Map<String, TreeMap<String, Integer>> playerScores;

  public ScoreKeeper() {
    playerScores = new TreeMap<>();
  }

  private void addScore(String name, String date, int score) {
    TreeMap<String, Integer> scores;
    scores = (playerScores.containsKey(name) ?
        playerScores.get(name) : new TreeMap<>());
    
    scores.put(date, score);
    playerScores.put(name, scores);
  }

  private int gameCount(String name) {
    return (playerScores.containsKey(name) ?
        playerScores.get(name).size() : 0);
  }

  private Double averageScore(String name) {
    if (!playerScores.containsKey(name)) {
      return null;
    } else {
      int scoreTotal = 0;
      for (int score : playerScores.get(name).values()) scoreTotal += score;
      return scoreTotal / (double) gameCount(name);
    }
  }

  private String lastDate(String name) {
    return (playerScores.containsKey(name) ?
        playerScores.get(name).lastKey() : null);
  }

  private Integer lastScore(String name) {
    return (playerScores.containsKey(name) ?
        playerScores.get(name).get(lastDate(name)) : null);
  }

  public static void main(String args[]) {
    ScoreKeeper scoreKeeper = new ScoreKeeper();
    scoreKeeper.addScore("john", "2016-01-01", 80);
    scoreKeeper.addScore("john", "2016-02-01", 88);
    scoreKeeper.addScore("john", "2016-03-01", 75);
    scoreKeeper.addScore("michael", "2016-02-01", 90);

    for (String name : scoreKeeper.playerScores.keySet()) {
      System.out.printf("Player: %s, Number of games: %d, Average score: %.1f, Last score: %d, Last game: %s",
          name, scoreKeeper.gameCount(name), scoreKeeper.averageScore(name),
          scoreKeeper.lastScore(name), scoreKeeper.lastDate(name));
      System.out.println();
    }
  }
}