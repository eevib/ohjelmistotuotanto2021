package statistics.matcher;

import java.lang.reflect.Method;
import statistics.Player;

public class Not implements Matcher {

    private Matcher matcher;

    public Not(Matcher m) {
        this.matcher = m;

        //  fieldName = "get" + Character.toUpperCase(category.charAt(0)) + category.substring(1, category.length());
    }

    @Override
    public boolean matches(Player p) {
        return !this.matcher.matches(p);

    }
}
