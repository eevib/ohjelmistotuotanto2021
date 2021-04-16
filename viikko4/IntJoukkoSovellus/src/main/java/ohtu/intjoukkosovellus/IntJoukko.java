package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] taulukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        taulukko = new int[KAPASITEETTI];
        for (int i = 0; i < taulukko.length; i++) {
            taulukko[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        taulukko = new int[kapasiteetti];
        for (int i = 0; i < taulukko.length; i++) {
            taulukko[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti liian pieni");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kasvatuskoko liian pieni");//heitin vaan jotain :D
        }
        taulukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0) {
            taulukko[0] = luku;
            alkioidenLkm++;
            return true;
        }
        if (!onTaulukossa(luku)) {
            taulukko[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (taulukkoTaysi()) {
                kopioiTaulukko(taulukko);
            }
            return true;
        }
        return false;
    }

    public boolean taulukkoTaysi() {
        if (alkioidenLkm == taulukko.length) {
            return true;
        }
        return false;
    }

    public boolean onTaulukossa(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int kohta = -1;
        int apu;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                kohta = i; //luku löytyy tuosta kohdasta
                taulukko[kohta] = 0;
                break;
            }
        }
        if (kohta != -1) {
            for (int j = kohta; j < alkioidenLkm - 1; j++) {
                apu = taulukko[j];
                taulukko[j] = taulukko[j + 1];
                taulukko[j + 1] = apu;
            }
            alkioidenLkm--;
            return true;
        }
        return false;
    }

    private void kopioiTaulukko(int[] vanha) {
        int[] uusi = new int[taulukko.length + kasvatuskoko];
        for (int i = 0; i < taulukko.length; i++) {
            uusi[i] = vanha[i];
        }
        taulukko = uusi;

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + taulukko[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += taulukko[i];
                tuotos += ", ";
            }
            tuotos += taulukko[alkioidenLkm - 1];
            tuotos += "}";
            return tuotos;
        }

    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = taulukko[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdiste = new IntJoukko();
        for (int i = 0; i < a.alkioidenLkm; i++) {
            yhdiste.lisaa(a.taulukko[i]);
        }
        for (int i = 0; i < b.alkioidenLkm; i++) {
            yhdiste.lisaa(b.taulukko[i]);
        }
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        for (int i = 0; i < b.alkioidenLkm; i++) { //jos taulukon b alkio löytyy taulukosta a, lisätään alkio taulukkoon leikkaus.
            if (b.onTaulukossa(a.taulukko[i])) {
                leikkaus.lisaa(a.taulukko[i]);
            }
        }
        return leikkaus;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko();
        for (int i = 0; i < a.alkioidenLkm; i++) {
            if (!b.onTaulukossa(a.taulukko[i])) {
                erotus.lisaa(a.taulukko[i]);
            }
        }
        return erotus;
    }

}
