package com.example.kikuchio.unitconverter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.Objects;

public abstract class UnitClassFragment<T extends Enum<?>> extends Fragment {

    private UnitConverter<T> mConverter;
    private EditText mFromEditText;
    private Spinner mFromUnitSpinner;
    private EditText mToEditText;
    private Button mSwapUnitsButton;
    private Button mClearButton;
    private Spinner mToUnitSpinner;

    protected abstract List<T> availableUnits();

    protected abstract boolean acceptsNegatives();

    protected abstract String unitClass();

    protected abstract UnitConverter<T> converter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mConverter = converter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unit_class_item, container, false);
        linkWidgetsToViews(view);
        initFromToSpinners();
        initFromEditText();
        initButtons();
        hideKeyboard(view);
        return view;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm =(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initButtons() {
        mSwapUnitsButton.setOnClickListener(v -> {
            swapUnits();
            convert();
        });
        mClearButton.setOnClickListener(v -> {
            mFromEditText.setText("");
            mFromEditText.requestFocus();
        });
    }

    private void swapUnits() {
        int from = mFromUnitSpinner.getSelectedItemPosition();
        int to = mToUnitSpinner.getSelectedItemPosition();
        mFromUnitSpinner.setSelection(to);
        mToUnitSpinner.setSelection(from);
    }

    private void initFromEditText() {
        mFromEditText.addTextChangedListener(fromTextWatcher());
        if (!acceptsNegatives())
            mFromEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        resetFromEditText();
        mFromEditText.setShowSoftInputOnFocus(true);
    }

    private TextWatcher fromTextWatcher() {
        return new TextWatcherImpl();
    }

    private void resetFromEditText() {
        mFromEditText.setText("1");
    }

    @SuppressWarnings("unchecked")
    private void convert() {
        float magnitude = getInputFromEditText(mFromEditText);
        T fromUnit = (T) mFromUnitSpinner.getSelectedItem();
        T toUnit = (T) mToUnitSpinner.getSelectedItem();
        float converted = mConverter.convert(fromUnit, magnitude, toUnit);
        mToEditText.setText(String.valueOf(converted));
    }

    private float getInputFromEditText(EditText editText) {
        if (editText.getText() == null || editText.getText().toString().isEmpty()
                || isSymbolsOnly(editText.getText().toString())) return 0;
        return Float.parseFloat(editText.getText().toString());
    }

    private boolean isSymbolsOnly(String text) {
        return text.matches("(^[.]$)|(^[-+]([.]*)$)");
    }

    private void initFromToSpinners() {
        ArrayAdapter<T> adapter = adapterOf(availableUnits());
        mFromUnitSpinner.setAdapter(adapter);
        mFromUnitSpinner.setOnItemSelectedListener(fromSpinnerOnItemSelectedListener());
        mFromUnitSpinner.setSelection(0);

        mToUnitSpinner.setAdapter(adapter);
        mToUnitSpinner.setOnItemSelectedListener(toSpinnerOnItemSelectedListener());
        mToUnitSpinner.setSelection(1);

    }

    private AdapterView.OnItemSelectedListener toSpinnerOnItemSelectedListener() {
        return new SpinnerOnItemSelectedListener();
    }

    private AdapterView.OnItemSelectedListener fromSpinnerOnItemSelectedListener() {
        return new SpinnerOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetFromEditText();
                super.onItemSelected(parent, view, position, id);
            }
        };
    }

    private ArrayAdapter<T> adapterOf(List<T> items) {
        return new UnitAdapter(items);
    }

    private void linkWidgetsToViews(View view) {
        mFromEditText = view.findViewById(R.id.unit_from_editText);
        mFromUnitSpinner = view.findViewById(R.id.unit_from_spinner);
        mToEditText = view.findViewById(R.id.unit_to_editText);
        mToUnitSpinner = view.findViewById(R.id.unit_to_spinner);
        mSwapUnitsButton = view.findViewById(R.id.swap_units_button);
        mClearButton = view.findViewById(R.id.clear_button);
    }

    private class UnitAdapter extends ArrayAdapter<T> {

        UnitAdapter(List<T> items) {
            super(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, items);
        }
    }

    private class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            convert();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            convert();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
