package net.justinwhite.score_model;

public abstract class Player {
    private String name;
    private int score;

    public Player(String _name) {
        name = _name;
        score = 0;
    }

    public String toString() {
        return String.format(
                "Name '%s'; Score %s",
                getName(),
                getScore()
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getInitials() {
        // assume name is "First" or "First Last", return "F_" or "FL"
        String names[] = name.split(" ");
        if (names.length > 1) {
            return names[0].substring(0, 1) + names[1].substring(0, 1);
        } else {
            return name.substring(0, 1) + "_";
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int _score) {
        score = _score;
    }
}
