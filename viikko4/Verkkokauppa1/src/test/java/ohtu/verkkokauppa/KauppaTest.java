package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa kauppa;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        kauppa = new Kauppa(varasto, pankki, viite);
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 3));
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "banaani", 1));
        when(varasto.saldo(2)).thenReturn(1);
        when(varasto.saldo(3)).thenReturn(0);
        kauppa.aloitaAsiointi();

    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        kauppa.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        kauppa.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostostaTehdessaOikeaaTiliaVeloitetaan() {
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

    @Test
    public void kahdenTuotteenOstosVeloitetaanOikealtaTiliilta() {
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 8);
    }

    @Test
    public void kahdenSamanTuotteenOstoVeloitetaanOikein() {
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("anna", "12321");
        verify(pankki).tilisiirto("anna", 42, "12321", "33333-44455", 10);
    }

    @Test
    public void tuotettaEiLisataJosEiVarastoSaldoa() {
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(3);
        kauppa.tilimaksu("anna", "12321");
        verify(pankki).tilisiirto("anna", 42, "12321", "33333-44455", 5);
    }

    @Test
    public void aloitaAsiointi() {
        kauppa.lisaaKoriin(1);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("anna", "1234521");
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(5));
    }

    @Test
    public void kauppaPyytaaUuttaViitenumeroaJokaiselleMaksutapahtumalle() {
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("Liisa", "54321");
        verify(viite, times(1)).uusi();

        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("Anna", "12345");
        verify(viite, times(2)).uusi();
    }
    @Test
    public void ostoskoristaPoistettuTuoteEiVaikutaHintaan() {
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("pekka", "98765");
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(5));
    }

}
