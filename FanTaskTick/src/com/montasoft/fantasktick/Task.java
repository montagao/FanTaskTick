package com.montasoft.fantasktick;

import android.content.Context;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;



public class Task{
	
	private String taskName;
	private Context context;
	private EditText dateView;
	private CheckBox checkBox;
	private RelativeLayout layout;
	private Spinner spinner;
	
	
	public Task(String taskName, Context context, LinearLayout layout,ArrayAdapter adapter ){
		this.context = context;

		this.layout = new RelativeLayout(context);
		layout.addView(this.layout);

		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT);
		relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
		
		this.taskName = taskName;
		
		this.checkBox = new CheckBox(context);
		this.checkBox.setId(1);;
		this.checkBox.setText(taskName);
		
		this.dateView = new EditText(context);
		this.dateView.setId(2);
		this.dateView.setTextSize(14);
		this.dateView.setText("Date (TODO)");


		
		this.spinner = new Spinner(context);
		this.spinner.setAdapter(adapter);
		this.spinner.setId(3);
		
		relativeParams.addRule(RelativeLayout.RIGHT_OF, this.checkBox.getId());
		
		RelativeLayout.LayoutParams dateParams  = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT);
		//dateParams.addRule(RelativeLayout.RIGHT_OF, this.spinner.getId());
		dateParams.leftMargin = 400;
		
		this.layout.addView(this.checkBox);
		this.layout.addView(this.spinner, relativeParams);
		this.layout.addView(this.dateView, dateParams);
		
		
		this.checkBox.layout(0, 0, 0, 0);
	}
	
	public boolean isChecked(){
		return checkBox.isChecked();
		
	}
	
	public void clearTask(){
		((ViewGroup)layout.getParent()).removeView(layout);
		return;
	}

}
