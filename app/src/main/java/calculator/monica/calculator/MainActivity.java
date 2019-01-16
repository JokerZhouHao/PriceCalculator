package calculator.monica.calculator;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import calculator.monica.calculator.entity.Info;
import calculator.monica.calculator.view.MainView;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        getSupportActionBar().hide();
        this.setTitle("");
        MainView mv = null;
        if(!new File(getInfoPath()).exists())   mv = new MainView(this, null);
        else mv = new MainView(this, Info.load(getInfoPath()));
        setContentView(mv);

//        Toast.makeText(this,"启动成功", Toast.LENGTH_SHORT).show();
    }

    public static MainActivity getInstance(){
        return instance;
    }

    public static String getInfoPath(){
        if(instance == null)    return null;
        else return instance.getFilesDir().getAbsolutePath() + File.separator + "info.obj";
    }

    @Override
    public void finish() {
        moveTaskToBack(true);
    }

}
