package ourhistory.hj.com.ourhistory.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ourhistory.hj.com.ourhistory.R;
import ourhistory.hj.com.ourhistory.activity.EventActivity;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SearchFragment extends Fragment {
    Context context;
    EditText editMonth;
    EditText editDay;
    Button enter;
    View view;
    String month;
    String day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment_layout, container, false);
        editMonth = (EditText) view.findViewById(R.id.search_fragment_month);
        editDay = (EditText) view.findViewById(R.id.search_fragment_day);
        enter = (Button) view.findViewById(R.id.search_fragment_enter);
        enter.setOnClickListener(onClickListener);
        return view;
    }

    private void getDate() {
        month = editMonth.getText().toString();
        day = editDay.getText().toString();
        if (month != null && !month.equals("") && day != null && !day.equals("")) {
            int a = Integer.parseInt(month);
            int b = Integer.parseInt(day);
            Intent intent = new Intent(context, EventActivity.class);
            if (a == 1 || a == 3 || a == 5 || a == 7 || a == 8 || a == 10 || a == 12) {
                if (b > 0 && b < 32) {
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                }
            }else if (a==4||a==6||a==9||a==11){
                if (b>0&&b<31){
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                }
            }else if (a==2){
                if (b>0&&b<30){
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "请输入正确的日期", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "请输入日期", Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_fragment_enter:
                    getDate();
                    break;
            }
        }
    };
}
