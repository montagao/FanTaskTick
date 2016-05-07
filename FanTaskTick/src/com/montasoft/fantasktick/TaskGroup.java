package com.montasoft.fantasktick;

import java.util.ArrayList;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskGroup {
	private TextView taskGroupName;
	private Context context;

	final ArrayList<Task> taskList;

	
	public TaskGroup(TextView tv, String name ){
		this.taskGroupName = tv;
		taskGroupName.setText(name);

		taskList = new ArrayList<Task>();

	}
	
	public void addTask( Task t){
		if (t == null) 
			return;

		taskList.add(t);
	}
	
	public void clearAllComplete(){
		for(int i = 0; i < taskList.size() ; i++){
			if(taskList.get(i).isChecked()){
				taskList.get(i).clearTask();
				taskList.remove(i);
				i--;
			}
		}
	}
	

}
