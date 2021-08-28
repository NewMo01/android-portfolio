package c.cricket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1 , tv2 ;
    Button b ;
    EditText et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv2 = findViewById(R.id.tv2);
        b = findViewById(R.id.b);
        et = findViewById(R.id.et);

        tv2.setVisibility(View.GONE);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                float n = Float.parseFloat(et.getText().toString().trim());
                float result = (n/3)+4 ;
                String result_tex = getString(R.string.the_temp)+"  " + result+ "  "+ getString(R.string.degree) ;
                tv2.setText(result_tex); tv2.setVisibility(View.VISIBLE);
            }
        });

    }
}