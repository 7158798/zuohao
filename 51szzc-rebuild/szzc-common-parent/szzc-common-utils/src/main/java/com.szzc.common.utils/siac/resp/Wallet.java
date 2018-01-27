package com.szzc.common.utils.siac.resp;

/**
 * Created by fenggq on 17-6-16.
 */
public class Wallet {

    /**
     * encrypted : true
     * unlocked : true
     * rescanning : false
     * confirmedsiacoinbalance : 0
     * unconfirmedoutgoingsiacoins : 0
     * unconfirmedincomingsiacoins : 0
     * siafundbalance : 0
     * siacoinclaimbalance : 0
     */

    private boolean encrypted;
    private boolean unlocked;
    private boolean rescanning;
    private String confirmedsiacoinbalance;
    private String unconfirmedoutgoingsiacoins;
    private String unconfirmedincomingsiacoins;
    private String siafundbalance;
    private String siacoinclaimbalance;

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isRescanning() {
        return rescanning;
    }

    public void setRescanning(boolean rescanning) {
        this.rescanning = rescanning;
    }

    public String getConfirmedsiacoinbalance() {
        return confirmedsiacoinbalance;
    }

    public void setConfirmedsiacoinbalance(String confirmedsiacoinbalance) {
        this.confirmedsiacoinbalance = confirmedsiacoinbalance;
    }

    public String getUnconfirmedoutgoingsiacoins() {
        return unconfirmedoutgoingsiacoins;
    }

    public void setUnconfirmedoutgoingsiacoins(String unconfirmedoutgoingsiacoins) {
        this.unconfirmedoutgoingsiacoins = unconfirmedoutgoingsiacoins;
    }

    public String getUnconfirmedincomingsiacoins() {
        return unconfirmedincomingsiacoins;
    }

    public void setUnconfirmedincomingsiacoins(String unconfirmedincomingsiacoins) {
        this.unconfirmedincomingsiacoins = unconfirmedincomingsiacoins;
    }

    public String getSiafundbalance() {
        return siafundbalance;
    }

    public void setSiafundbalance(String siafundbalance) {
        this.siafundbalance = siafundbalance;
    }

    public String getSiacoinclaimbalance() {
        return siacoinclaimbalance;
    }

    public void setSiacoinclaimbalance(String siacoinclaimbalance) {
        this.siacoinclaimbalance = siacoinclaimbalance;
    }
}
