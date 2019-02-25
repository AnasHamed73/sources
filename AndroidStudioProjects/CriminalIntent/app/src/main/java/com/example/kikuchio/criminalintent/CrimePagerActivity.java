package com.example.kikuchio.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();
        setContentView(mViewPager);
        initCrimes();
        setPagerAdapter();
        displaySelectedCrime();
    }

    private void setPagerAdapter() {
        mViewPager.setAdapter(new PagerAdapterImpl());
    }

    private void displaySelectedCrime() {
        UUID currentCrimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        mViewPager.setCurrentItem(mCrimes.indexOf(
                CrimeLab.get(this).getCrime(currentCrimeId)));
    }

    private void initCrimes() {
        mCrimes = CrimeLab.get(this).getCrimes();
    }

    private void initViewPager() {
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        mViewPager.addOnPageChangeListener(new PagerOnPageChangeListenerImpl());
    }

    private class PagerOnPageChangeListenerImpl implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int i) {
            Crime crime = mCrimes.get(i);
            String crimeTitle = crime.getTitle();
            if(crimeTitle != null)
                setTitle(crimeTitle );
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private class PagerAdapterImpl extends FragmentStatePagerAdapter {

        PagerAdapterImpl() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int i) {
            return CrimeFragment.newInstance(mCrimes.get(i).getId());
        }

        @Override
        public int getCount() {
            return mCrimes.size();
        }
    }
}
