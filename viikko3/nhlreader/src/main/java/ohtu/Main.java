package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";

        String bodyText = Request.Get(url).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
//        System.out.println(bodyText);

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);
        List<Player> finPlayers = new ArrayList<>();
        
        
        System.out.println("Players from FIN:");
        for (Player player : players) {
            if(player.getNationality().equals("FIN")) {
                finPlayers.add(player);
            }
        }
           finPlayers.stream()
                    .distinct()
                    .sorted((p1, p2) -> {
                    return p2.getPoints() - p1.getPoints();
    })
                    .forEach(p -> System.out.println(p));
           
           
     
    }

}
