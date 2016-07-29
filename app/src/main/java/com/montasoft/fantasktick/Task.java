package com.montasoft.fantasktick;

import java.util.Calendar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;



public class Task{
	
	private String taskName;
	private Context context;
	private CheckBox checkBox;
	private LinearLayout layout;
	private Spinner spinner;
	private Date taskDate;
	
	
	public class Date{
		private ImageButton addDateButton;
		private TextView dateLabel; // date format "Fri, April 27" something or other.
		private LinearLayout dateGroup;
		private String dateString;

		public String toString(){
			return dateString;
		}
		
		public Date(){
			/*
			 * Constructor creates default textview labels and addDatebutton, 
			 * Makes sure they are aligned to the right of dateGroup layoutcontainer.
			 */


			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.dateGroup = new LinearLayout(context);
			dateGroup.setOrientation(LinearLayout.HORIZONTAL);

			this.addDateButton = (ImageButton)inflater.inflate(R.layout.calendar,null);
			addDateButton.setImageResource(R.drawable.calendar);
			addDateButton.setPadding(0,0,0,5);

			this.dateLabel = new TextView(context);

			dateLabel.setText("No date");
			dateLabel.setPadding(0,20,10,0);

			LinearLayout.LayoutParams dateParams  = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
			dateParams.weight = 0.1f;
			dateParams.gravity= Gravity.END; 
			
			addDateButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// Calls TimePicker dialog
					DialogFragment newFragment = new DatePickerFragment();
					newFragment.show(((FragmentActivity)context).getFragmentManager(), "timePicker");

					
				}
			});

			LinearLayout.LayoutParams rightGravity = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			dateGroup.addView(dateLabel, rightGravity);
			dateGroup.addView(addDateButton , rightGravity);
			rightGravity.gravity = Gravity.END; 

			this.dateGroup.setGravity(Gravity.END);
			layout.addView(this.dateGroup, dateParams);
		}
		
		void setDateLabel(String s){
			dateLabel.setText(s);
		}

		void setDateString(String s) {dateString = s;}
		
		
		
	}
	
	public Task(String taskName, Context context, LinearLayout layout){
		this.context = context;

		this.layout = new LinearLayout(context);
		LayoutParams lparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT ); 
		layout.addView(this.layout,lparams); 

		//RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
		//		LayoutParams.WRAP_CONTENT);
		//relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
		
		this.taskName = taskName;

		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.priority_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		
		this.checkBox = new CheckBox(context);
		this.checkBox.setId(1);
		this.checkBox.setText(taskName);
		LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.9f);
		
		this.spinner = new Spinner(context);
		this.spinner.setAdapter(adapter);
		this.spinner.setId(3);
		
		//relativeParams.addRule(RelativeLayout.RIGHT_OF, this.checkBox.getId());
		
		//dateParams.addRule(RelativeLayout.RIGHT_OF, this.spinner.getId());
		
		this.layout.addView(this.checkBox, params);
		this.layout.addView(this.spinner );
		this.taskDate = new Date();

		
		
		//this.checkBox.layout(0, 0, 0, 0);
	}
	
	public boolean isChecked(){
		return checkBox.isChecked();
		
	}

	public void setChecked(boolean checked){
		checkBox.setChecked(checked);

	}

	public void setPriority(String p){
		p = p.toUpperCase();
		switch (p ){
			case "A":
				this.spinner.setSelection(0);
				break;
			case "B":
				this.spinner.setSelection(1);
				break;
			case "C":
				this.spinner.setSelection(2);
				break;
			case "D":
				this.spinner.setSelection(3);
				break;
			default:
				break;
		}
	}

	public void setDate(String d){
		if ( d.equals("null")) return;
		//sets date of form DD-MM-YY
     	String[] dSplit = d.split("-");
		try {
			setDate(Integer.parseInt(dSplit[2]), Integer.parseInt(dSplit[1]), Integer.parseInt(dSplit[0]));
		} catch (Exception e ){
			this.taskDate = null;
		}

	}
	
	public void setDate(int y, int m, int d){
		String month = new String();
		switch(m){
		case 0:
			month = ("Jan");
			break;
		case 1:
			month = ("Feb");
			break;
		case 2:
			month = ("Mar");
			break;
		case 3:
			month = ("Apr");
			break;
		case 4:
			month = ("May");
			break;
		case 5:
			month = ("Jun");
			break;
		case 6:
			month = ("Jul");
			break;
		case 7:
			month = ("Aug");
			break;
		case 8:
			month = ("Sep");
			break;
		case 9:
			month = ("Oct");
			break;
		case 10:
			month = ("Nov");
			break;
		case 11:
			month = ("Dec");
			break;
		}

		taskDate.setDateString(d+"-"+m+"-"+y);
		
		taskDate.setDateLabel(String.format("%s %d", month, d));
		
		
	}
	
	public void clearTask(){
		((ViewGroup)layout.getParent()).removeView(layout);
		return;
	}


	@Override
	public String toString(){
		// Returns parseable taskData (comma separated);
		return taskName +"," + isChecked() + "," + spinner.getSelectedItem().toString() + "," + taskDate.toString();
	}
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// use the curernt time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			/// Create a new instance of TimePickerDCialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			Task.this.setDate(year,monthOfYear, dayOfMonth);
		}
	
	}
}
