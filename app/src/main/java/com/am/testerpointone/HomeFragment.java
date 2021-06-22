package com.am.testerpointone;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    Button btnparticipatenow;

    LinearLayout llcompr;
    PrefManager prefManager;
    int paper;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        btnparticipatenow=root.findViewById(R.id.appCompatButton2);

        llcompr=root.findViewById(R.id.llcompr);

        prefManager= new PrefManager(getContext());
        if (prefManager.isregistered()){
            llcompr.setVisibility(View.GONE);
        } else {

        }

        llcompr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefManager= new PrefManager(getContext());
                if (!prefManager.isregistered()) {
                    llcompr.setVisibility(View.GONE);

                    BottomNavigationView mBottomNavigationView = getActivity().findViewById(R.id.nav_view);
                    mBottomNavigationView.setSelectedItemId(R.id.navigation_exams);

                } else {
                    Intent intent = new Intent(getActivity(), Registration.class);
                    startActivity(intent);
                }

            }
        });

        btnparticipatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Light_Dialog);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.custom_dialog);
//                dialog.findViewById(R.id.linearLayout3).setBackgroundResource(R.drawable.edbgllgr);
//
//                dialog.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.findViewById(R.id.linearLayout3).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        paper=1;
//                        view.setBackgroundResource(R.drawable.edbgllgr);
//                        dialog.findViewById(R.id.linearLayout4).setBackgroundResource(R.drawable.edbgll);
//                    }
//                });
//                dialog.findViewById(R.id.linearLayout4).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        paper=2;
//                        view.setBackgroundResource(R.drawable.edbgllgr);
//                        dialog.findViewById(R.id.linearLayout3).setBackgroundResource(R.drawable.edbgll);
//                    }
//                });

//                dialog.findViewById(R.id.appCompatButton).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
                        if (prefManager.isregistered()) {
                            llcompr.setVisibility(View.GONE);

                            BottomNavigationView mBottomNavigationView = getActivity().findViewById(R.id.nav_view);
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_exams);

                        } else {
                            Intent intent = new Intent(getActivity(), Registration.class);
                            startActivity(intent);
                        }



                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}