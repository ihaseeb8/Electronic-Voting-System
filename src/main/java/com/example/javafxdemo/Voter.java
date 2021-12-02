package com.example.javafxdemo;

public class Voter {

    public String voterCnic;
    public String voterParty;

    private static Voter obj=new Voter();

    private Voter(){
        voterCnic = null;
        voterParty = null;
    }

    public static Voter getVoter(){
        return obj;
    }

    public void setVoter(String voterCnic, String voterParty) {
        this.voterCnic = voterCnic;
        this.voterParty = voterParty;
    }

}
