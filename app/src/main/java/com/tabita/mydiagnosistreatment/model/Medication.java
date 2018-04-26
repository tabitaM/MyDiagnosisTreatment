package com.tabita.mydiagnosistreatment.model;

public class Medication {
    private String name;
    private String dose;

    public Medication() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "name='" + name + '\'' +
                ", dose='" + dose + '\'' +
                '}';
    }
}
