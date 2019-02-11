package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model;

import java.util.Date;

import java.util.Iterator;
public abstract class TaskList implements Iterable<Task> {

    private Task[] taskList;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);
    abstract public Iterator<Task> iterator();

    
    
    
    public TaskList incoming(Date from, Date to)  {
        TaskList taskList;
        if (this instanceof LinkedTaskList) {
            taskList = new LinkedTaskList();
        } else {
            taskList = new ArrayTaskList();
        }
            for (int i = 0; i < size(); i++) {
         
                if (getTask(i).nextTimeAfter(from) != null && getTask(i).nextTimeAfter(from).before(to) ) {
                               
           
                taskList.add(getTask(i));
           
                
                }
            }
            return taskList;

    }

    
    }





