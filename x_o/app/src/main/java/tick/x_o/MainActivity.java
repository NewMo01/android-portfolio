package tick.x_o;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;
    int player = 1 ;
    int[] state = new int[9];
    int[][] winArea = {{0,1,2} , {3,4,5} , {6,7,8} ,{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    char winner='-';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.grid);

        onClickGrid(gridLayout);



    }

    private void onClickGrid(GridLayout gridLayout) {
        for(int i=0 ; i<gridLayout.getChildCount() ; i++){
            Button button = (Button)gridLayout.getChildAt(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view ) {
                    int buttonId =  Integer.parseInt( view.getResources().getResourceEntryName(view.getId()).substring(2,3) );
                    if(player == 1) {
                        button.setText("O");
                        button.setEnabled(false);
                        // adjust the array data
                        state[buttonId] = 1 ;
                        player=2;
                    }else if(player==2 ){
                        button.setText("X");
                        button.setEnabled(false);
                        // adjust array data
                        state[buttonId] = 2 ;
                        player=1;
                    }
                    if(checkWin()) show() ;
                }
            });
        }
    }

    private boolean checkWin(){
        boolean res = false ;
        for (int[] i : winArea) {
            if (state[i[0]] == state[i[1]] && state[i[1]] == state[i[2]] && state[i[0]] != 0) {
                res = true;
                if (state[i[0]] == 1) winner = 'O';
                else winner = 'X';

                return res;
            }
        }
        return res;
    }
    private void show(){
        if (winner=='O')Toast.makeText(this , " O win",Toast.LENGTH_SHORT).show();
        else if(winner=='X')Toast.makeText(this , "X win",Toast.LENGTH_SHORT).show();
    }

    public void resetOnClick(View view) {
        for (int i=0 ; i<gridLayout.getChildCount(); i++ ){
            Button b = (Button)gridLayout.getChildAt(i);
            b.setText("");
            b.setEnabled(true);
            state[i]=0 ;
        }
        winner = '-';
    }
}