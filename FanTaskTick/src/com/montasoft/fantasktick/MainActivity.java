package com.montasoft.fantasktick;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.os.Build;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new MainViewFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*
		if (id == R.id.action_settings) {
			return true;
		} */
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Fragment for Main Tasks area
	 */
	public static class MainViewFragment extends Fragment {

		public MainViewFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView taskGroupView = (TextView) rootView.findViewById(R.id.task_group);
			final LinearLayout taskLayout = (LinearLayout) rootView.findViewById(R.id.task_layout);
			final LinearLayout rootLayout = (LinearLayout) rootView.findViewById(R.id.root_layout);
			
			
			
			WindowManager wm = (WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point screenSize = new Point();
			display.getSize(screenSize);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenSize.x, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			rootLayout.setLayoutParams(params);

			
			final TaskGroup taskGroup = new TaskGroup(taskGroupView, "Testlist 1");
			

			// Task newTask = new Task("This is a task", rootView.getContext(), layout);
			ImageButton addTaskButton = (ImageButton) rootView.findViewById(R.id.taskadd_button);
			Button clearTaskButton = (Button) rootView.findViewById(R.id.clear_tasks);
			
			
			//create an array adapter using string array and default sipinner layout.
			
			final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.priority_array, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


			addTaskButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v ){
					TextView edit = (TextView) rootView.findViewById(R.id.addtask_edit);
					String taskName = edit.getText().toString();
					Task newTask = new Task(taskName, rootView.getContext(), taskLayout, adapter);
					taskGroup.addTask(newTask);
					edit.setText("");
				}
			});
			
			clearTaskButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					taskGroup.clearAllComplete();
					
				}
			});
			
			return rootView;
		}
	}

	
}
