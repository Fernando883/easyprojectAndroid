package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.TaskPageAdapter;
import inftel.easyprojectandroid.model.Tarea;

/**
 * Created by csalas on 13/4/16.
 */
public class TaskViewPagerFragment extends Fragment {
    private View view;
    private ViewPager viewPager;
    private TaskPageAdapter pageAdapter;
    private TabLayout tabLayout;
    private List<Tarea> listTask;

    public TaskViewPagerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_task_viewpager, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.container);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pageAdapter = new TaskPageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        pageAdapter.setTaskList(listTask);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(tabSelectedListener());

    }

    private TabLayout.OnTabSelectedListener tabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabId) {
                Log.e("onTabSelected", tabId.getText().toString());

                viewPager.setCurrentItem(tabId.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    public void setListTask(List<Tarea> listTask) {
        this.listTask = listTask;
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

}
