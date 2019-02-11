package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayTaskList extends TaskList {
    private Task[] taskList;
    private int index = 0;


    public int size() {
        return index;
    }

    public void add(Task task) {

        if (taskList == null) {
            taskList = new Task[10];
            taskList[index] = task;
            index++;
        } else {
            Task[] temp = new Task[taskList.length + 1];
            System.arraycopy(taskList, 0, temp, 0, taskList.length);
            temp[index] = task;
            index++;
            taskList = temp;
        }
    }

    public boolean remove(Task task) {
        int i = 0;
        while (i < size()) {
            if (taskList[i] == task) {
                taskList[i] = null;
                System.arraycopy(taskList, i + 1, taskList, i, ((taskList.length - 1) - i));
                index--;
                i++;
                return true;
            }
            i++;
        }
        return false;
    }

    public Task getTask(int index) {

        return taskList[index];
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayIterator();
    }


    private class ArrayIterator implements Iterator<Task> {
        private int next = 0;


        public boolean hasNext() {


            return next < size();

        }

        public Task next() {
            if (!hasNext()) throw new NoSuchElementException();


            return getTask(next++);

        }


        public void remove() {

            if (next == 0) {
                throw new IllegalStateException();
            } else {
                ArrayTaskList.this.remove(getTask(--next));


            }
        }
    }

    public int hashCode() {
        int arrayHash = 0;
        for (Task task : this) {
            arrayHash ^= task.hashCode();
        }
        return arrayHash;
    }

    public boolean equals(Object obj) {
        if ((obj == null) || !obj.getClass().equals(getClass())) {
            return false;

        }
        ArrayTaskList arr = (ArrayTaskList) obj;
        if (arr == (obj)) {
            Iterator first = this.iterator();
            Iterator second = arr.iterator();
            while (first.hasNext()) {
                if (second.next().equals(first.next())) ;
                return true;
            }
            return true;
        } else
            return false;
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {

        ArrayTaskList copy = new ArrayTaskList();
        for (Task task : this) {
            copy.add(task.clone());
        }
        return copy;
    }

     public String toString() {

        String tempString = "";
        for (int i = 0; i < size(); i++) {
            if (i > 0) tempString += ", ";

            tempString += "{" + taskList[i].toString() + "}";
        }

        tempString = "ArrayTaskList{"
                + "taskList{" + tempString
                + "}, Index=" + index
                + "}";

        return tempString;

    }
}

