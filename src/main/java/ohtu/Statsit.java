package ohtu;

public class Statsit {
    private int students;
    private int exercise_total;
    private int hour_total;

    public int getHour_total() {
        return hour_total;
    }

    public void setHour_total(int hour_total) {
        this.hour_total = hour_total;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public int getExercise_total() {
        return exercise_total;
    }

    public void setExercise_total(int exercise_total) {
        this.exercise_total = exercise_total;
    }
    
    @Override
    public String toString() {
        return students + ", " + exercise_total + ", " + hour_total;
    }
}
