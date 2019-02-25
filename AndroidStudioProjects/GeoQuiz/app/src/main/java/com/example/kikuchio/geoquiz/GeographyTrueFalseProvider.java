package com.example.kikuchio.geoquiz;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GeographyTrueFalseProvider implements TrueFalseProvider {

    private static final List<TrueFalse> trueFalseList = new ArrayList<>();

    static {
        trueFalseList.add(new TrueFalse(R.string.question_oceans, true));
        trueFalseList.add(new TrueFalse(R.string.question_mideast, false));
        trueFalseList.add(new TrueFalse(R.string.question_africa, false));
        trueFalseList.add(new TrueFalse(R.string.question_americas, true));
        trueFalseList.add(new TrueFalse(R.string.question_asia, true));
    }

    @Override
    public Iterable<TrueFalse> trueFalseIterable() {
        return trueFalseList;
    }
}
