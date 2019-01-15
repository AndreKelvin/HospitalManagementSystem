/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechpatient;

/**
 *
 * @author Andre Kelvin
 */
public class Patient {
    
    private String patient,bloodGroup,diagnosis;

    public Patient(String patient, String bloodGroup, String diagnosis) {
        this.patient = patient;
        this.bloodGroup = bloodGroup;
        this.diagnosis = diagnosis;
    }

    public String getPatient() {
        return patient;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    
}
