package ipcm.calc.cropmanager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/*
Not used but was used in an early version of the app
 */

public class CropPicker extends ListActivity {
	
	private ListView list;
	private ArrayList<Crop> crops;
	
	// The action performed when an item in the list is clicked on
	protected void onListItemClick(ListView l, View v, int position, long id){
		String name = crops.get(position).getName();
		Intent intent = new Intent(this, ToolPicker.class);
		intent.putExtra("cropname", name);
		startActivity(intent);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_croppicker);
        
        list = getListView();
        
        String[] array = getResources().getStringArray(R.array.crops);
        crops = new ArrayList<Crop>();
        
        for(int i = 0; i < array.length; i++)
        	crops.add(new Crop(array[i]));
        
        SimpleListAdapter adapter = new SimpleListAdapter(this, R.layout.listitem, crops);
        
        list.setAdapter(adapter);        
        list.setCacheColorHint(Color.TRANSPARENT); // Avoid a weird animation when a list item is clicked
        
    }
}