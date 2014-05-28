package ipcm.calc.cropmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/*
The activity for the grain yield calculator
 */

public class GrainYield extends CalculatorActivity {

    private EditText plantsInput;
    private EditText numberRowsInput;
    private EditText kernelsInput;
    private EditText massInput;
    private TextView result;
    ScrollView scroller;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grainyield);

        // Get all necessary layout elements
        plantsInput = (EditText)findViewById(R.id.plants_per_acre_input);
        numberRowsInput = (EditText)findViewById(R.id.number_rows_input);
        kernelsInput = (EditText)findViewById(R.id.kernels_per_row_input);
        massInput = (EditText)findViewById(R.id.kernel_mass_input);
        result = (TextView)findViewById(R.id.grainyield_result);
        scroller = (ScrollView)findViewById(R.id.scroller);

        // Set up the text listeners and input types for the EditTexts
        plantsInput.addTextChangedListener( new CurrencyWholeFormatWatcher( plantsInput, this) );
        numberRowsInput.addTextChangedListener( new CurrencyWholeFormatWatcher( numberRowsInput, this) );
        kernelsInput.addTextChangedListener( new CurrencyWholeFormatWatcher( kernelsInput, this) );
        massInput.addTextChangedListener( new CurrencyWholeFormatWatcher( massInput, this) );

        // Ensures that we just get the number keyboard
        plantsInput.setRawInputType(Configuration.KEYBOARD_12KEY);
        numberRowsInput.setRawInputType(Configuration.KEYBOARD_12KEY);
        kernelsInput.setRawInputType(Configuration.KEYBOARD_12KEY);
        massInput.setRawInputType(Configuration.KEYBOARD_12KEY);

        scroller.setBackgroundColor(getResources().getColor(R.color.grey_ddd));

    }

    protected void onResume(){
        super.onResume();

        // Needed to fix a bug that was happening with the color of the background
        scroller.setBackgroundColor(getResources().getColor(R.color.grey_ddd));

    }

    public void calculate(){

        // Can't do the calculation if any of the inputs are empty
        if(plantsInput.getText().equals("") || numberRowsInput.getText().equals("")
                || kernelsInput.getText().equals("") || massInput.getText().equals("")){
            result.setText("--");
            return;
        }

        int plants;
        int rows;
        int kernels;
        int mass;

        try{
            plants = Integer.parseInt(plantsInput.getText().toString());
            rows = Integer.parseInt(numberRowsInput.getText().toString());
            kernels = Integer.parseInt(kernelsInput.getText().toString());
            mass = Integer.parseInt(massInput.getText().toString());

            Double r = plants * rows * kernels * mass * (1000.0/(56.0*454000.0));

            result.setText(((Integer)r.intValue()).toString());
            return;
        }catch(Exception e){
            // A simple catch-all for any error. Probably not the cleanest solution
            result.setText("--");
            return;
        }

    }

    /*
    Creates the email report and launches the default email activity
     */
    public void emailReport(View v){

        // We don't allow the user to send an email without having made a calculation
        if(result.getText().equals("--")){
            Toast toast = Toast.makeText(this, "Perform a calculation", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String body = "Plants per 1000th acre: " + plantsInput.getText().toString() + "\n";
        body += "Rows per ear: " + numberRowsInput.getText().toString() + "\n";
        body += "Kernels per row: " + kernelsInput.getText().toString() + "\n";
        body += "Kernel mass: " + massInput.getText().toString() + "\n\n";
        body += "Grain yield result: " + result.getText().toString() + " bu/A\n\n";
        body += "This email generated by Crop Management Calculators, an Android app by the University of Wisconsin-Madison's NPM program\n";
        body += "http://ipcm.wisc.edu/apps/";

        Intent intent = new Intent(Intent.ACTION_SEND);								// Sets the intent to be an email intent
        intent.setType("plain/text");												// I don't know what this does but it's necessary
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });					// The email address to send to. We don't know who the user will want to send it to.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Grain yield report");    // The subject line

        intent.putExtra(Intent.EXTRA_TEXT, body);			// The body text
        startActivity(Intent.createChooser(intent, ""));		// Starts the email activity, passing the given data with it

    }

    // Launched when the help button is clicked
    public void help(View v){
        Intent i = new Intent(this, GrainYieldHelp.class);
        startActivity(i);
    }

}