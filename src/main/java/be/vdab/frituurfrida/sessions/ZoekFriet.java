package be.vdab.frituurfrida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

@Component
@SessionScope
public class ZoekFriet implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean[] openDeuren = new boolean[7];
    private int deurMetFrietId;
    private boolean gewonnen;

    public ZoekFriet() {
        nieuwSpel();
    }

    public boolean[] getOpenDeuren() {
        return openDeuren;
    }

    public int getDeurMetFrietId() {
        return gewonnen ? deurMetFrietId : -1;
    }

    public void nieuwSpel() {
        deurMetFrietId = new Random().nextInt(7);
        Arrays.fill(openDeuren, false);
        gewonnen = false;
    }

    public void openDeur(int deurId) {
        openDeuren[deurId] = true;
        if (deurId == deurMetFrietId) {
            gewonnen = true;
        }
    }

}
