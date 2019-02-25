package com.example.kikuchio.remotecontrol;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RemoteControlFragment extends Fragment {

    private TextView mSelectedTextView;
    private TextView mWorkingTextView;
    private TableLayout mTableLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remote_control, container, false);
        linkWidgetsToViews(view);
        initButtons();
        return view;
    }

    private void initButtons() {
        initTopNumbers();
        initBottomRow();
    }

    private void initBottomRow() {
        TableRow bottomRow = (TableRow)mTableLayout.getChildAt(mTableLayout.getChildCount() - 1);
        initDeleteButton((Button)bottomRow.getChildAt(0));
        initZeroButton((Button)bottomRow.getChildAt(1));
        initEnterButton((Button)bottomRow.getChildAt(2));
    }

    private void initEnterButton(Button enterButton) {
        enterButton.setText("Enter");
        enterButton.setOnClickListener(enterButtonOnClickListener());
    }

    private void initZeroButton(Button zeroButton) {
        zeroButton.setText("0");
        zeroButton.setOnClickListener(numberButtonOnClickListener());
    }

    private void initDeleteButton(Button delButton) {
        delButton.setText("Delete");

        delButton.setOnClickListener(v -> mWorkingTextView.setText("0"));
    }

    private void initTopNumbers() {
        int number = 1;
        for(int i = 2; i < mTableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) mTableLayout.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("" + number++);
                button.setOnClickListener(numberButtonOnClickListener());
            }
        }
    }

    private View.OnClickListener enterButtonOnClickListener() {
        return v -> {
            CharSequence working = mWorkingTextView.getText();
            if(working.length() > 0)
                mSelectedTextView.setText(working);
            mWorkingTextView.setText("0");
        };
    }

    private View.OnClickListener numberButtonOnClickListener() {
        return v -> {
            TextView textView = (TextView) v;
            String working = mWorkingTextView.getText().toString();
            String text = textView.getText().toString();
            mWorkingTextView.setText(working.equals("0") ? text : working + text);
        };
    }

    private void linkWidgetsToViews(View view) {
        mSelectedTextView = view.findViewById(R.id.fragment_remote_control_selectedTextView);
        mWorkingTextView = view.findViewById(R.id.fragment_remote_control_workingTextView);
        mTableLayout = view.findViewById(R.id.fragment_remote_control_tableLayout);
    }
}
