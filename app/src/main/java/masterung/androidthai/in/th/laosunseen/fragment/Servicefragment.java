package masterung.androidthai.in.th.laosunseen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import masterung.androidthai.in.th.laosunseen.R;
import masterung.androidthai.in.th.laosunseen.utility.MyAlert;
import masterung.androidthai.in.th.laosunseen.utility.ServiceAdapter;
import masterung.androidthai.in.th.laosunseen.utility.UserModel;

public class Servicefragment extends Fragment {

    private String nameString, currentPostString, uidString;
    private String tag = "10AvgV1";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findMyme();

//        Create Toolbar
        //  createToolbar();


//    Add Fragment
// PostController
        PostContorller();

//        Create RecyclerView123

        createRecyclerView();


    }//Main Method

    private void createRecyclerView() {
        final RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewUser);
        final int[] contints = new int[]{0};

        final ArrayList<String> phoStringArrayList = new ArrayList<>();
        final ArrayList<String> nmeStringArrayList = new ArrayList<>();
        final ArrayList<String> poStringArrayList = new ArrayList<>();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = (int) dataSnapshot.getChildrenCount();
                ArrayList<UserModel> modelArrayList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    UserModel userModel = dataSnapshot1.getValue(UserModel.class);
                    modelArrayList.add(userModel);


                    UserModel userModel1 = modelArrayList.get(contints[0]);
                    contints[0] += 1;

                    phoStringArrayList.add(userModel1.getPathUrlString());
                    nmeStringArrayList.add(userModel1.getNameString());
                    poStringArrayList.add(userModel1.getMyPostString());


                }//for

                ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(),
                        phoStringArrayList, nmeStringArrayList, poStringArrayList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(serviceAdapter);


            }//datachang

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {




            }
        });
    }

    private void findMyme() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uidString = firebaseAuth.getCurrentUser().getUid();
        Log.d(tag, "Uid ==>" + uidString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("User").child(uidString);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map map = (Map) dataSnapshot.getValue();
                nameString = String.valueOf(map.get("nameString"));
                currentPostString = String.valueOf(map.get("myPostString"));
                Log.d(tag, "Name ==>" + nameString);
                Log.d(tag, "CurrentPost ==>" + currentPostString);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void PostContorller() {

        Button button = getView().findViewById(R.id.btnpost);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = getView().findViewById(R.id.editPost);
                String posString = editText.getText().toString().trim();
                if (posString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Post False", "Please Type on Post");

                } else {
                }
                editCurrentPost(posString);

            }
        });
    }

    private void editCurrentPost(String posString) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("User").child(uidString);

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("myPostString", changeMyData(posString));
        databaseReference.updateChildren(stringObjectMap);

    }

    private String changeMyData(String posString) {

        String resultString = null;
        resultString = currentPostString.substring(1, currentPostString.length() - 1);
        String[] strings = resultString.split(",");
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < strings.length; i += 1) {
            stringArrayList.add(strings[i]);
        }

        stringArrayList.add(posString);

        return stringArrayList.toString();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_service, menu);
//    }

    // @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == R.id.itemSignout) {
//            signOut();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private  void  signOut(){
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signOut();
//        getActivity().finish();
//
//
//    }

//    private void createToolbar() {
//        Toolbar toolbar = getView().findViewById(R.id.toolbarservice);
//        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
//        setHasOptionsMenu(true);
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }//
}
