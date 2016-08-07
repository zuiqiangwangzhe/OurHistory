package ourhistory.hj.com.ourhistory.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import ourhistory.hj.com.ourhistory.R;
import ourhistory.hj.com.ourhistory.fragment.CollectFragment;
import ourhistory.hj.com.ourhistory.fragment.SearchFragment;

public class MainActivity extends Activity {
    RadioGroup radioGroup;
    LinearLayout linearLayout;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        linearLayout= (LinearLayout) findViewById(R.id.main_linear);
        radioGroup= (RadioGroup) findViewById(R.id.main_rg);
        manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        SearchFragment searchFragment=new SearchFragment();
        transaction.add(R.id.main_linear,searchFragment);
        transaction.commit();
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction transaction=manager.beginTransaction();
            switch (checkedId){
                case R.id.main_history:
                    transaction.replace(R.id.main_linear,new SearchFragment());
                    break;
                case R.id.main_collect:
                    transaction.replace(R.id.main_linear,new CollectFragment());
                    break;
            }
            transaction.commit();
        }
    };

}
