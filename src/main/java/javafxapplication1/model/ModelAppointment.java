package javafxapplication1.model;

/**
 * Modelo para representar una cita en la tabla
 */
public class ModelAppointment {
    private int    appointmentId;
    private int    patientId;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String requestTreatment;
    private String dentistName;

    public ModelAppointment(int appointmentId,
                            int patientId,
                            String patientName,
                            String appointmentDate,
                            String appointmentTime,
                            String requestTreatment,
                            String dentistName) {
        this.appointmentId     = appointmentId;
        this.patientId         = patientId;
        this.patientName       = patientName;
        this.appointmentDate   = appointmentDate;
        this.appointmentTime   = appointmentTime;
        this.requestTreatment  = requestTreatment;
        this.dentistName       = dentistName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getRequestTreatment() {
        return requestTreatment;
    }

    public void setRequestTreatment(String requestTreatment) {
        this.requestTreatment = requestTreatment;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }
}
