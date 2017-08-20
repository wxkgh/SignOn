package org.life.task.Model;

public class Task {
    private long id;
    private long userid;
    String title;
    int reword;
    int number;
    String label;
    String species;

    public long getId() {
        return this.id;
    }

    public long getUserid() {
        return this.userid;
    }

    public String getTitle() {
        return this.title;
    }

    public int getReword() {
        return this.reword;
    }

    public int getNumber() {
        return this.number;
    }

    public String getLabel() {
        return this.label;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReword(int reword) {
        this.reword = reword;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
