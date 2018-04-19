package com.tabita.mydiagnosistreatment.model;

import java.util.List;

class Treatment {

    private String notes;
    private String period;
    private List<Medication> medication;

    public Treatment() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "notes='" + notes + '\'' +
                ", period='" + period + '\'' +
                ", medication=" + medication +
                '}';
    }
}
