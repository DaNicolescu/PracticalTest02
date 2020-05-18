package ro.pub.cs.systems.eim.practicaltest02;

public class BitcoinData {
    private String usd;
    private String eur;
    private String updated;

    public BitcoinData() {
    }

    public BitcoinData(String usd, String eur, String updated) {
        this.usd = usd;
        this.eur = eur;
        this.updated = updated;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
