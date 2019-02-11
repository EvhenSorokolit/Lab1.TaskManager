package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model;

import java.util.Date;

public class Task implements Cloneable {


    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;


    public Task(String title, Date time) {
        setTitle(title);
        setTime(time);
        setActive(false);
        repeated = false;

    }

    public Task(String title, Date start, Date end, int interval) {
        setTime(start, end, interval);
        setActive(false);
        setTitle(title);
        repeated = true;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTime() {
        if (isRepeated())
            return start;
        else
            return time;
    }


   //normal set time
    public void setTime(Date time) {

        this.time = time;
        this.start = null;
        this.end = null;

        if (isRepeated()) {
            repeated = false;
        }


    }

    //normal set time
    public void setTime(Date start, Date end, int interval) {


        this.start = start;
        this.end = end;
        this.interval = interval;

        this.time = null;
    }



    public Date getStartTime() {
        if (isRepeated())
            return start;
        else
            return time;
    }

    public Date getEndTime() {
        if (!isRepeated())
            return this.time;
            else
        return this.end;
    }

    public int getRepeatInterval() {

        if (!isRepeated()) {
            return 0;
        } else {
            return interval;
        }
    }

    public boolean isRepeated() {
        return repeated;
    }

    public Date nextTimeAfter(Date current) {

        if (!isActive()) {
            return null;
        } else if (!isRepeated()) {
            if (current.before(time))
                return time;
            else
                return null;
        } else {
            if (current.before(start))
                return start;
            Date tmp = new Date(start.getTime());
            while (!tmp.after(current)) {
                tmp.setTime(tmp.getTime() + interval * 1000);
            }
            if (!tmp.after(end))
                return tmp;
            else
                return null;
        }
    }


    @Override
    protected Task clone() throws CloneNotSupportedException {

        Task copy = (Task) super.clone();
        copy.title = title;
        copy.active = active;
        copy.time = time;
        copy.start = start;
        copy.end = end;
        copy.interval = interval;
        return copy;
    }


    @Override
    public String toString() {
 if(isRepeated()){
 
 if(isActive()){
        return "Task{"
                + '\"' + title + '\"'
                + " active " 
               // + "  at [" + time + "]"
                + " from [" + start+ "]"
                + " to [" + end + "]"
                + " every " + intrvalToWords(interval)
                + '}';
                }else 
                return "Task{"
                + '\"' + title + '\"'
                + " inactive " 
               // + "  at [" + time + "]"
                + " from [" + start+ "]"
                + " to [" + end + "]"
                + " every " + intrvalToWords(interval)
                + '}';
                
                
    } else return " Task {\"" + getTitle() + "\" at [" + getTime() + "]}";
    }
     public String intrvalToWords(int interval){
        String out=null;
        int min;
        int hour;
        int days;
        min= Math.abs((interval/60));
        hour =Math.abs((interval/60)/60);
        
        days = Math.abs(((interval/60)/60)/24);
        if (min<59|| hour==0 ){
        return out=" "+min+" minutes ";
        }else if(hour<23 || days==0){
        return out=" "+hour+" hours";
        } else
        
        out=" "+days+" days "+hour+" hours "+min+" minutes ";

return  out;

    }

}
