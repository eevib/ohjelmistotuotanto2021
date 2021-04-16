package ohtu;

import static java.lang.Math.abs;

public class TennisGame {

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName == "player1") {
            scorePlayer1 += 1;
        } else {
            scorePlayer2 += 1;
        }
    }

    public String getResult(int scorePlayer) {
        if (scorePlayer == 1) {
            return "Fifteen";
        } else if (scorePlayer == 2) {
            return "Thirty";
        } else if (scorePlayer == 3) {
            return "Forty";
        }
        return "Love";
    }

    private int pointDifference() {
        return Math.abs(scorePlayer1 - scorePlayer2);
    }

    private int whoIsLeading() {
        if (scorePlayer1 > scorePlayer2) {
            return 1;
        }
        return 2;
    }

    private int playerWon() {
        if (pointDifference() > 1 && scorePlayer1 > 3 && whoIsLeading() == 1) {
            return 1;
        } else if (pointDifference() > 1 && scorePlayer2 > 3) {
            return 2;
        } else {
            return 0;
        }
    }

    private boolean gameEven() {
        if (scorePlayer1 == scorePlayer2) {
            return true;
        }
        return false;
    }

    public String getScore() {
        String score = "";

        if (gameEven()) {
            if (scorePlayer1 == 4) {
                return "Deuce";
            } else {
                return getResult(scorePlayer1) + "-All";
            }

        } else if (scorePlayer1 > 3 || scorePlayer2 > 3) {
            if (playerWon() == 1) {
                return "Win for player1";
            } else if (playerWon() == 2) {
                return "Win for player2";
            } else if (whoIsLeading() == 1) {
                return "Advantage player1";
            } else {
                return "Advantage player2";
            }
        }
        score = getResult(scorePlayer1) + "-" + getResult(scorePlayer2);

        return score;
    }
}
