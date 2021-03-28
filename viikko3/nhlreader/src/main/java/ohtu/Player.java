package ohtu;

public class Player {

    private String name;
    private String nationality;
    private int assists;
    private int goals;
    private int penalties;
    private String team;
    private int games;

    public String getNationality() {
        return nationality;
    }

    public Integer getPoints() {
        return goals + assists;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " team " + team + " " + goals + " + " + assists + " = " + (goals + assists);
    }

}
