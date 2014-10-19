package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.proyectos2.mileem.R;

public class MediaDetailsActivity extends Activity {

    private List<String> imagesURLs = new LinkedList<String>();
    private String videoURL;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_details);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        imagesURLs = intent.getExtras().getStringArrayList("imagesURLs");
        videoURL = intent.getExtras().getString("videoURL");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.mediaPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.media_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (videoURL == null) {
                return ImagePageFragment.create(imagesURLs.get(position));
            } else {
                if (position < imagesURLs.size()) {
                    return ImagePageFragment.create(imagesURLs.get(position));
                } else {
                    return VideoPageFragment.create(videoURL);
                }
            }
        }

        @Override
        public int getCount() {
            if (videoURL==null)
                return imagesURLs.size();
            else
                return imagesURLs.size() + 1;
        }
    }

}
