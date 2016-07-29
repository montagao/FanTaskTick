package com.montasoft.fantasktick;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;

/**
 * Created by mgao on 24/07/16.
 */
public class Storage {


    static String FILENAME = "data";

    public static TaskGroup getTaskGroupFromData(Context context, EditText tgv, LinearLayout tL ){

        TaskGroup tG = null;
        try {
            // Returns taskGroup from given;
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            // Parse data file for task group
            tG = new TaskGroup(tgv,reader.readLine());

            String taskData = null;

            while ( (taskData = reader.readLine()) != null){
                // parses task lines, in the form of taskName,isChecked, priority, date
                String[] tDataSplit = taskData.split(",");
                Task task = new Task(tDataSplit[0],context, tL);
                task.setChecked(Boolean.parseBoolean(tDataSplit[1]));
                task.setPriority(tDataSplit[2]);
                task.setDate(tDataSplit[3]);

                tG.addTask(task);
            }
            return tG;

        }catch (IOException e) {
            Log.d("IOException occurred", "Failed to read from :" + FILENAME);
        }

        return null;

    }


    public static void storeTaskGroupToFile(TaskGroup tG, Context c){
        String FILENAME = "data";
        // Store TaskGroups in format:
        // First line is taskGroup name
        // subsequent lines are comma separated lines with format taskName,isChecked,priority,date
        // date value is formatted as DD-MM-YY
        try {
            FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(fos);
            writer.print(tG.toString());
            writer.flush();
            writer.close();
            fos.close();

        } catch (IOException e) {
            Log.d("IOException", "Failed to write to :" + FILENAME);
        }

    }

    public static boolean taskDataExists(){
        // bad code, i know....
        return (new File(FILENAME)).exists();

    }
}
