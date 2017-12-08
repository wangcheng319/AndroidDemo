package vico.xin.mvpdemo.activity;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import vico.xin.mvpdemo.R;
import vico.xin.mvpdemo.app.App;
import vico.xin.mvpdemo.dto.Student;
import vico.xin.mvpdemo.dto.StudentDao;
import vico.xin.mvpdemo.service.ContactIntentService;
import vico.xin.mvpdemo.utils.DBHelper;

public class ViewDragHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drag_helper);

        DBHelper.init(this);
        //新增
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StudentDao studentDao = DBHelper.getInstance().getStudentDao();
                Student student = new Student();
                student.setAge("3");
                student.setName("张三");

                studentDao.insert(student);
            }
        });


        //查找
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < DBHelper.getInstance().getStudentDao().queryBuilder().build().list().size(); i++) {
                    Log.e("+++",DBHelper.getInstance().getStudentDao().queryBuilder().build().list().get(i).getName());
                }
            }
        });


        //删除
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDragHelperActivity.this,ContactIntentService.class);
                ViewDragHelperActivity.this.startService(intent);
            }
        });
    }
}
