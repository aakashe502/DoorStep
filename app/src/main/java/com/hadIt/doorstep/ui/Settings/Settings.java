package com.hadIt.doorstep.ui.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hadIt.doorstep.R;

public class Settings extends Fragment {

    public TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_settings,container,false);

        return root;
    }
}
