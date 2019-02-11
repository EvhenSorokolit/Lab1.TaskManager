package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model;

import java.util.Iterator;

public class LinkedTaskList extends TaskList {

    private Node firstNode;
    private Node lastNode;
    private int size;

    private class Node {

        private Task task;
        private Node previousNode;
        private Node nextNode;

        Node(Task newTask) {
            this.task = newTask;
        }

        public void setPreviousNode(Node previousNode) {
            this.previousNode = previousNode;
        }

        public Node getPreviousNode() {
            return previousNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public Task getTask() {
            return task;
        }
    }


    public void add(Task task) {
        if (task == null) throw new IllegalArgumentException("task == null");

        Node node = new Node(task);

        if (firstNode == null) {
            this.firstNode = node;
            this.lastNode = node;
        } else {
            this.lastNode.setNextNode(node);
            node.setPreviousNode(this.lastNode);
            this.lastNode = node;
        }

        size++;
    }


    public int size() {
        return size;
    }


    public Task getTask(int index) {
        if (index < 0) throw new IllegalArgumentException("index < 0");

        int tempIndex = -1;

        Node currentNode = firstNode;

        while (currentNode != null) {

            tempIndex++;

            if (tempIndex == index) break;

            currentNode = currentNode.getNextNode();
        }

        return (currentNode == null) ? null : currentNode.getTask();
    }


    public boolean remove(Task task) {
        if (task == null) throw new IllegalArgumentException("task = null");

        if (size() == 0) return false;

        boolean taskExist = false;
        Node currentNode = firstNode;

        while (currentNode != null) {

            if (task.equals(currentNode.getTask())) {
                taskExist = true;
                break;
            }

            currentNode = currentNode.getNextNode();
        }

        if (taskExist) {

            if (size() == 1) {

                firstNode = null;
                lastNode = null;
                size--;
                return true;

            } else {

                if (currentNode.equals(firstNode)) {

                    firstNode = currentNode.getNextNode();
                    firstNode.setPreviousNode(null);

                } else if (currentNode.equals(lastNode)) {

                    lastNode = currentNode.getPreviousNode();
                    lastNode.setNextNode(null);

                } else {

                    Node tmpPrev = currentNode.getPreviousNode();
                    Node tmpNext = currentNode.getNextNode();
                    tmpPrev.setNextNode(tmpNext);
                    tmpNext.setPreviousNode(tmpPrev);
                }

                size--;
                return true;
            }
        } else {

            return false;

        }

    }

    public Iterator<Task> iterator() {
        return new ListIterator();

    }

    private class ListIterator implements Iterator<Task> {
        private int next = 0;
        ;

        public boolean hasNext() {
            return next < size();
        }

        public Task next() {

            return getTask(next++);
        }


        public void remove() {

            if (next == 0) {
                throw new IllegalStateException();
            } else {
                LinkedTaskList.this.remove(getTask(--next));
            }
        }

    }

    public int hashCode() {
        int hashList = 0;
        for (Task task : this)
            hashList ^= task.hashCode();
        return hashList;
    }


    public boolean equals(Object obj) {
        if ((obj == null) || !obj.getClass().equals(getClass())) {
            return false;
        }
        LinkedTaskList tempList = (LinkedTaskList) obj;
        if (tempList.size() == size()) {
            Iterator firstIterator = tempList.iterator();
            Iterator sekIterator = iterator();
            while (firstIterator.hasNext()) {
                if (sekIterator.next().equals(firstIterator.next()))
                    return true;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList copy = new LinkedTaskList();
        for (Task task : this)
            copy.add(task.clone());
        return copy;
    }
    
    public String toString() {

        String tempString = "";

        int i = 0;
        for (Task task : this) {
            if (i != 0) tempString += ", ";
            tempString += "{" + task.toString() + "}";
            i++;
        }

        tempString = "LinkedTaskList{" + tempString + "}";

        return tempString;

    }


}