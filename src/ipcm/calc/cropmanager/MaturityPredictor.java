package ipcm.calc.cropmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* The activity for the maturity predictor */

public class MaturityPredictor extends Activity {

    private TextView kernelMilkResult;
    private TextView blackLayerResult;
    private TextView frostDate;
    private TextView tasselDate;
    private Button frostDateButton;
    private Button tasselDateButton;
    private LinearLayout kernelMilkLayout;
    private LinearLayout blackLayerLayout;

    Date milkRangeBottom;
    Date milkRangeTop;
    Date blackRangeBottom;
    Date blackRangeTop;

    Date frost = null;
    Date tassel = null;

    private DatePickerDialog.OnDateSetListener frostDateSetListener;
    private DatePickerDialog.OnDateSetListener tasselDateSetListener;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_maturitypredictor);

        kernelMilkResult = (TextView)findViewById(R.id.kernel_milk_text);
        blackLayerResult = (TextView)findViewById(R.id.black_layer_text);
        frostDate = (TextView)findViewById(R.id.frost_date);
        tasselDate = (TextView)findViewById(R.id.tassel_date);
        frostDateButton = (Button)findViewById(R.id.frost_date_button);
        tasselDateButton = (Button)findViewById(R.id.tassel_date_button);
        kernelMilkLayout = (LinearLayout)findViewById(R.id.kernel_milk_result);
        blackLayerLayout = (LinearLayout)findViewById(R.id.black_layer_result);

        kernelMilkLayout.setBackgroundColor(getResources().getColor(R.color.grey_ccc));
        blackLayerLayout.setBackgroundColor(getResources().getColor(R.color.grey_ddd));

        frostDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {

                Dates helper = new Dates();

                String month = helper.ItoSMonth(i2 + 1);
                String day = helper.ItoSDayOfMonth(i3);
                String year = helper.ItoSYear(i);

                frostDate.setText(month + " " + day + ", " + year);

                frost = new Date(i, i2, i3);

                calculate();

            }

        };

        tasselDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {

                Dates helper = new Dates();

                String month = helper.ItoSMonth(i2 + 1);
                String day = helper.ItoSDayOfMonth(i3);
                String year = helper.ItoSYear(i);

                tasselDate.setText(month + " " + day + ", " + year);

                tassel = new Date(i, i2, i3);

                calculate();

            }

        };

    }

    @Override
    protected void onResume(){
        super.onResume();

        kernelMilkLayout.setBackgroundColor(getResources().getColor(R.color.grey_ccc));
        blackLayerLayout.setBackgroundColor(getResources().getColor(R.color.grey_ddd));

    }

    // Called when the frost button is clicked
    public void frostOnClick(View v){

        if(frost == null){
            Calendar c = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(this, frostDateSetListener, c.get(Calendar.YEAR), 9, 15);
            dpd.show();
        }else{
            DatePickerDialog dpd = new DatePickerDialog(this, frostDateSetListener, frost.getYear(), frost.getMonth(), frost.getDate());
            dpd.show();
        }

    }

    // Called when the tassel button is clicked
    public void tasselOnClick(View v){

        if(tassel == null){
            Calendar c = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(this, tasselDateSetListener, c.get(Calendar.YEAR), 6, 15);
            dpd.show();
        }else{
            DatePickerDialog dpd = new DatePickerDialog(this, tasselDateSetListener, tassel.getYear(), tassel.getMonth(), tassel.getDate());
            dpd.show();
        }

    }

    public void calculate(){

        if(tassel != null){

            milkRangeBottom = addDays(tassel, 42);
            milkRangeTop = addDays(milkRangeBottom, 5);
            blackRangeBottom = addDays(milkRangeTop, 8);
            blackRangeTop = addDays(blackRangeBottom, 5);

            kernelMilkResult.setText(dateToString(milkRangeBottom) + " - " + dateToString(milkRangeTop));
            blackLayerResult.setText(dateToString(blackRangeBottom) + " - " + dateToString(blackRangeTop));

            if(frost != null){

                // If frost occurs before milk range
                if(frost.before(milkRangeBottom)){
                    kernelMilkLayout.setBackgroundColor(getResources().getColor(R.color.red_bad));
                }else{
                    kernelMilkLayout.setBackgroundColor(getResources().getColor(R.color.green_good));
                }

                // If frost occurs before black layer range
                if(frost.before(blackRangeBottom)){
                    blackLayerLayout.setBackgroundColor(getResources().getColor(R.color.red_bad_light));
                }else{
                    blackLayerLayout.setBackgroundColor(getResources().getColor(R.color.green_good_light));
                }

            }

        }

    }

    public Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public String dateToString(Date date){
        Dates d = new Dates();

        String month = d.ItoSMonth(date.getMonth() + 1);
        String day = d.ItoSDayOfMonth(date.getDate());
        String year = d.ItoSYear(date.getYear());

        return month + " " + day + ", " + year;
    }

    public void emailReport(View v){

        String body = "";

        if(tassel == null){
            Toast toast = Toast.makeText(this, "Tassel date required", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        body += "Tassel date: " + dateToString(tassel) + "\n";

        if(frost != null){
            body += "Expected frost date: " + dateToString(frost) + "\n";
        }

        body += "\n";

        body += "50% kernel milk date range: " + kernelMilkResult.getText() + "\n";

        body += "Black layer date range: " + blackLayerResult.getText() + "\n\n";

        if(frost != null){

            if(frost.before(milkRangeBottom)){
                body += "Warning: frost expected to hit before kernel milk date range\n";
            }else{
                body += "Kernel milk date range expected to hit before frost\n";
            }

            if(frost.before(blackRangeBottom)){
                body += "Warning: frost expected to hit before black layer date range\n\n";
            }else{
                body += "Black layer date range expected to hit before frost\n\n";
            }

        }

        body += "This email generated by Crop Management Calculators, an Android app by the University of Wisconsin-Madison's NPM program\n";
        body += "http://ipcm.wisc.edu/apps/";

        Intent intent = new Intent(Intent.ACTION_SEND);								// Sets the intent to be an email intent
        intent.setType("plain/text");												// I don't know what this does but it's necessary
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });					// The email address to send to. We don't know who the user will want to send it to.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Corn maturity predictor report");    // The subject line

        intent.putExtra(Intent.EXTRA_TEXT, body);			// The body text
        startActivity(Intent.createChooser(intent, ""));		// Starts the email activity, passing the given data with it

    }

    public void help(View v){
        Intent i = new Intent(this, MaturityHelp.class);
        startActivity(i);
    }

}
