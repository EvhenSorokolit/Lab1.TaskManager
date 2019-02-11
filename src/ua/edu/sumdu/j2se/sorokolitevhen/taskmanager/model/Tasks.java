
package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Set;
public class Tasks {


public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
        ArrayTaskList result = new ArrayTaskList();

        for (Task t :tasks) {
            if (t.isActive() && t.nextTimeAfter(start) != null
                    && t.nextTimeAfter(start).compareTo(end) <= 0) {
                result.add(t);
            }
        }
        return result;

}



    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        TreeMap<Date, Set<Task>> calendar = new TreeMap<>();
        Iterable<Task> inc = incoming(tasks, start, end);
        for (Task task : inc) {
            Date tmp = task.nextTimeAfter(start);
                   
            while(tmp != null && tmp.compareTo(end) <= 0) {
            
                if (calendar.containsKey(tmp)) {
                   calendar.get(tmp).add(task);
                } else {
                    Set<Task> setOfTasks = new HashSet<>();
                    setOfTasks.add(task);
                    calendar.put(tmp, setOfTasks);
                    
                }
                tmp = task.nextTimeAfter(tmp);
          
          }
           
        }
        return calendar;
    }

    
    
   

    }