package com.enzo.fxapp.Models;

public class Salarie {
    private String nom;
    private String prenom;
    private String telephoneFixe;
    private String telephonePortable;
    private String email;
    private Service service;
    private Site site;

    public Salarie(String nom, String prenom, String telephoneFixe, String telephonePortable, String email, Service service, Site site) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephoneFixe = telephoneFixe;
        this.telephonePortable = telephonePortable;
        this.email = email;
        this.service = service;
        this.site = site;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public void setTelephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public String getTelephonePortable() {
        return telephonePortable;
    }

    public void setTelephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
