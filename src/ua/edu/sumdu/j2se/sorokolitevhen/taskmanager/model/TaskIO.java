package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model;

import java.io.*;
import java.util.*;
import java.text.*;



public class TaskIO implements Serializable{

    private static SimpleDateFormat convert = new SimpleDateFormat( "yyyy-MM-dd;HH:mm:ss:SSS" );



    public static void write(TaskList tasks, OutputStream out) throws IOException {
        if (tasks.equals(null)){
            throw  new NullPointerException("pustaya zadacha");
        }
        DataOutputStream in = new DataOutputStream(out);
        try {
            in.writeInt(tasks.size());
            for (Task task : tasks) {
                in.writeUTF(task.getTitle());
                in.writeBoolean(task.isActive());
                in.writeInt(task.getRepeatInterval());
                if(task.isRepeated()) {
                    in.writeLong(task.getStartTime().getTime());
                    in.writeLong(task.getEndTime().getTime());
                }else{
                    in.writeLong(task.getTime().getTime());
                }
            }
        } finally {
            in.flush();
        }
    }


    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream out = new DataInputStream(in);
        int size = out.readInt();
        try {
            for (int i = 0; i < size; i++) {
                String newTitle = out.readUTF();
                boolean act = out.readBoolean();
                int nRep = out.readInt();
                if(nRep>0) {

                    Date nStart = new Date(out.readLong());
                    Date nEnd = new Date(out.readLong());
                    Task nTask = new Task(newTitle, nStart, nEnd, nRep);
                    nTask.setActive(act);
                    tasks.add(nTask);
                }else{
                    Date nTime = new Date(out.readLong());
                    Task nTask = new Task(newTitle,nTime);
                    nTask.setActive(act);
                    tasks.add(nTask);
                }



            }
        } finally {
            out.close();
        }
    }


    public static void writeBinary(TaskList tasks, File file) throws IOException {
        FileOutputStream outfile = new FileOutputStream(file);
        try {
            write(tasks, outfile);
        } finally {
            outfile.close();
        }
    }


    public static void readBinary(TaskList tasks, File filename) throws IOException {
        FileInputStream inFile = new FileInputStream(filename);
        read(tasks, inFile);
    }


    public static void write(TaskList tasks, Writer out) throws IOException {
        BufferedWriter outwr = new BufferedWriter(out);

        try {
            for (Task task : tasks) {
                if(task.isRepeated()){
                    outwr.write(" / ");
                    outwr.write(String.valueOf(true));
                    outwr.write(" / ");
                    outwr.write(task.getTitle());
                    outwr.write(" / ");
                    outwr.write(convert.format(task.getStartTime()));
                    outwr.write(" / ");
                    outwr.write(convert.format(task.getEndTime()));
                    outwr.write(" / ");
                    outwr.write(String.valueOf(task.getRepeatInterval()));
                    outwr.write(" / ");
                    outwr.write(String.valueOf(task.isActive()));
                    outwr.write(" / ");
                    outwr.append("\n");

                }
                else {
                    outwr.write(" / ");
                    outwr.write(String.valueOf(false));
                    outwr.write(" / ");
                    outwr.write(task.getTitle());
                    outwr.write(" / ");
                    outwr.write(convert.format(task.getStartTime()));
                    outwr.write(" / ");
                    outwr.write(String.valueOf(task.isActive()));
                    outwr.write(" / ");
                    outwr.append("\n");
                }
            }
        } finally {
            outwr.flush();
            outwr.close();
        }
    }


    public static void writeText(TaskList tasks, File filename) throws IOException {

        FileWriter inFile = new FileWriter(filename);

        try {
            write(tasks, inFile);
        } finally {
            inFile.close();
        }
    }



    public static void read(TaskList tasks, Reader in) throws IOException, ParseException {
        Scanner inps = new Scanner(in).useDelimiter("\\s* / \\s*");

        try {
            while (inps.hasNext()) {
                String rep = inps.next();
                System.out.println(rep);
                Boolean repited = Boolean.valueOf(rep);
                if(repited == true){
                    String title = inps.next();
                    System.out.println(title);
                    Date start = convert.parse(inps.next());
                    System.out.println(start);
                    Date end = convert.parse(inps.next());
                    System.out.println(end);
                    int interval = Integer.parseInt(inps.next());
                    Task tmp = new Task(title, start, end, interval);
                    tmp.setActive(Boolean.parseBoolean(inps.next()));
                    tasks.add(tmp);

                }
                else {
                    String title = inps.next();
                    System.out.println(title);
                    Date time = convert.parse(inps.next());
                    System.out.println(title);
                    Task tmp = new Task(title,time);
                    tmp.setActive(Boolean.parseBoolean(inps.next()));
                    tasks.add(tmp);

                }
            }
        } finally {
            inps.close();
        }
    }


    public static void readText(TaskList tasks, File filename) throws IOException, ParseException {
        FileReader inFile = new FileReader(filename);
        try {
            read(tasks, inFile);
        } finally {
            inFile.close();
        }
    }

}