package com.enzo.fxapp.Controllers.Client;

public class Collaborateur {

    private Integer id;
    private String nom;
    private String prenom;
    private String telephoneFixe;
    private String telephonePortable;
    private String email;
    private String service;
    private String site;

    public Collaborateur(Integer id, String nom, String prenom, String telephoneFixe, String telephonePortable, String email, String service, String site) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephoneFixe = telephoneFixe;
        this.telephonePortable = telephonePortable;
        this.email = email;
        this.service = service;
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
