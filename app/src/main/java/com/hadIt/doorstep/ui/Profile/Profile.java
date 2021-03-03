package com.hadIt.doorstep.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hadIt.doorstep.R;

public class Profile extends Fragment {

    public TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile,container,false);

        return root;
    }
}
