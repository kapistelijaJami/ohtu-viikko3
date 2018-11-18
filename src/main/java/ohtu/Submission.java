package ohtu;

public class Submission {
    private int week;
    private int hours;
    private String course;
    private int[] exercises;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int[] getExercises() {
        return exercises;
    }
    
    public int getExercisesCount() {
        return exercises.length;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    @Override
    public String toString() {
        String tehtavat = "";
        for (int i = 0; i < getExercisesCount(); i++) {
            tehtavat += exercises[i];
            
            if (i != getExercisesCount() - 1) {
                tehtavat += ", ";
            }
            
        }
        
        return course + ", viikko " + week + " tehtyjä tehtäviä yhteensä " + getExercisesCount() + " tehdyt tehtävät: " + tehtavat;
    }
    
}