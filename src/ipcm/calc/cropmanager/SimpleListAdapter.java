package ipcm.calc.cropmanager;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleListAdapter extends ArrayAdapter<Crop>{

	// Instance variables
	int resource;
	private ArrayList<Crop> crops;

	public SimpleListAdapter(Context _context, int _resource, ArrayList<Crop> _crops)
	{
		super(_context, _resource, _crops);
		resource = _resource;
		crops = _crops;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;		
		if(view == null)
		{
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    view = vi.inflate(resource, null);
		}
		Crop crop = crops.get(position);		
		if(crop != null){
			TextView name = (TextView)view.findViewById(R.id.name);
			if(name!=null)
				name.setText(crop.getName());
		}		
		return view;
	}

}