package ipcm.calc.cropmanager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ToolPicker extends ListActivity {
	
	private ListView list;
	private ArrayList<Crop> tools;

    ImageView forageButton;
    ImageView maturityButton;
    ImageView grainYieldButton;
	
	// The action performed when an item in the list is clicked on
	protected void onListItemClick(ListView l, View v, int position, long id){
		String name = tools.get(position).getName();
        Intent intent = null;

        if(name.equals("Corn silage moisture")){
		    intent = new Intent(this, ForageMoisture.class);
        }
        if(name.equals("Grain yield estimator")){
            intent = new Intent(this, GrainYield.class);
        }
        if(name.equals("Maturity predictor")){
            intent = new Intent(this, MaturityPredictor.class);
        }

        if(intent != null){
            intent.putExtra("toolname", name);
            startActivity(intent);
        }
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toolpicker);
        
        list = getListView();
        
        String[] array = getResources().getStringArray(R.array.corntools);
        tools = new ArrayList<Crop>();
        
        for(int i = 0; i < array.length; i++)
        	tools.add(new Crop(array[i]));
        
        SimpleListAdapter adapter = new SimpleListAdapter(this, R.layout.listitem, tools);
        
        list.setAdapter(adapter);        
        list.setCacheColorHint(Color.TRANSPARENT); // Avoid a weird animation when a list item is clicked
        
    }

    public void info(View v){
        Intent i = new Intent(this, MainInfo.class);
        startActivity(i);
    }

    public void forageOnClick(View v){
        Intent i = new Intent(this, ForageMoisture.class);
        startActivity(i);
    }

    public void maturityOnClick(View v){
        Intent i = new Intent(this, MaturityPredictor.class);
        startActivity(i);
    }

    public void grainYieldOnClick(View v){
        Intent i = new Intent(this, GrainYield.class);
        startActivity(i);
    }

}