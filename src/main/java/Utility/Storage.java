package Utility;

import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    Storage(String filePath) {
        this.filePath = filePath;
    }

    public static int getPosition(String s, char target) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == target) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Task> load() throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        ArrayList<Task> newList = new ArrayList<>();
        while (s.hasNext()) {
            String line = s.nextLine();
            char taskType = line.charAt(1);
            char isDone = line.charAt(4);
            switch (taskType) {
                case 'T':
                    newList.add(new Todo(line.substring(7)));
                    break;
                case 'E':
                    int posE = getPosition(line, '/');
                    newList.add(new Event(line.substring(7, posE), line.substring(posE + 4)));
                case 'D':
                    int posD = getPosition(line, '/');
                    /*
                    System.out.println("posD " +  posD);
                    System.out.println(line);
                    System.out.println(line.substring(7, posD));
                    System.out.println(line.substring(posD + 4));
                    */
                    newList.add(new Deadline(line.substring(7, posD), line.substring(posD + 4)));
                default:
                    System.out.println("Can't read line");
            }
        }
        // NO IDEA WHY IS THERE ANOTHER LAST ELEMENT CAUSING EXCEPTION BUT JUST DELETE IT WTV
        newList.remove(newList.size()-1);
        return newList;
    }

    public void writeToFile(ArrayList<Task> ls) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        String textToAdd = "";
        // convert ArrayList contents to string
        for (Task t : ls) {
            if (t instanceof Event || t instanceof Deadline) {
                textToAdd = textToAdd + t.toWrite() + "\n";
            } else {
                textToAdd = textToAdd + t.toString() + "\n";
            }

        }
        fw.write(textToAdd);
        fw.close();
    }












}
