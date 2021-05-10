package com.hadIt.doorstep.fragment_ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hadIt.doorstep.R;

public class DashboardFragment extends Fragment {


    public TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard,container,false);
        Toast.makeText(getActivity(),"wow",Toast.LENGTH_SHORT).show();

        getActivity().setTitle("Dashboard");

        return root;
    }
}