package com.happy.todo.module_main.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happy.todo.module_main.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    public TestFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment_test, container, false);

        return view;
    }

}
