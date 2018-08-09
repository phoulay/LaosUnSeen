package masterung.androidthai.in.th.laosunseen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import masterung.androidthai.in.th.laosunseen.fragment.Servicefragment;

public class ServiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
// Add Fragment


        addFragment(savedInstanceState);

//        Exit Controller

        exitController();


    }//Main Method

    private void exitController() {
        TextView textView = findViewById(R.id.txtExit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();

            }
        });
    }


    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenServiceFragment, new Servicefragment())
                    .commit();
        }
    }
}
