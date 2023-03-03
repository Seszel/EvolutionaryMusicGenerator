package evolution.solution;


public class Note {
    private String pitchNote; // c,d,e,f,g,a,h,cis... and pause p
    private int pitchOctave; // 1,2,3,4... and null
    private int duration; // 1/8, 1/4, 1 ...


    public Note(String pitchNote, int pitchOctave, int duration) {
        this.pitchNote = pitchNote;
        this.pitchOctave = pitchOctave;
        this.duration = duration;
    }


    public String getPitchNote() {
        return pitchNote;
    }

    public void setPitchNote(String pitchNote) {
        this.pitchNote = pitchNote;
    }

    public int getPitchOctave() {
        return pitchOctave;
    }

    public void setPitchOctave(int pitchOctave) {
        this.pitchOctave = pitchOctave;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
