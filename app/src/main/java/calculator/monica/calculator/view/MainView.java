package calculator.monica.calculator.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import calculator.monica.calculator.MainActivity;
import calculator.monica.calculator.R;
import calculator.monica.calculator.entity.Info;
import calculator.monica.calculator.utility.StringFormator;

public class MainView extends GridLayout {
    public Info info = null;

    public MainView(Context context, Info info){
        this(context, null, info);
    }

    public MainView(Context context, AttributeSet attrs, Info info) {
        super(context, attrs);
        this.info = info;
        LayoutInflater.from(context).inflate(R.layout.activity_main, this);
        init();
    }

    private void init(){
        // 设置所有UI
        setUI();

        // 添加计算
        Button calBtn = (Button)this.findViewById(R.id.cal);
        calBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检查路径是否为空
                if(MainActivity.getInfoPath() == null){
                    Toast.makeText(MainActivity.getInstance(), "失败：infoPath为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 计算
                StringBuffer itemBF = new StringBuffer();
                double price = Double.parseDouble(((EditText)MainView.this.findViewById(R.id.price)).getText().toString());
                double min = Double.parseDouble(((EditText)MainView.this.findViewById(R.id.min)).getText().toString());
                double max = Double.parseDouble(((EditText)MainView.this.findViewById(R.id.max)).getText().toString());
                int minIndex = (int)(min/0.5);
                int maxIndex = (int)(max/0.5);
                if(min != minIndex * 0.5){
                    itemBF.append(format(min, price));
                }
                for(int i=minIndex; i<=maxIndex; i++){
                    itemBF.append(format(0.5*i, price));
                }
                if(max != maxIndex * 0.5){
                    itemBF.append(format(max, price));
                }
                ((EditText)MainView.this.findViewById(R.id.items)).setText(itemBF.toString());

                // 保存
                if(info == null){
                    info = new Info(price, min, max, itemBF.toString());
                } else{
                    info.set(price, min, max, itemBF.toString());
                }
                String res = info.save(MainActivity.getInfoPath());
                if(res != null) Toast.makeText(MainActivity.getInstance(), "失败：" + res, Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.getInstance(), "成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button updateBtn = (Button)MainView.this.findViewById(R.id.update);
        updateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info == null){
                    Toast.makeText(MainActivity.getInstance(), "失败：info未初始化", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MainActivity.getInfoPath() == null){
                    Toast.makeText(MainActivity.getInstance(), "失败：infoPath为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                info.items = ((EditText)findViewById(R.id.items)).getText().toString();
                String res = info.save(MainActivity.getInfoPath());
                if(res != null) Toast.makeText(MainActivity.getInstance(), "失败：" + res, Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.getInstance(), "成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUI(){
        if(null == info)    return;
        ((EditText)this.findViewById(R.id.price)).setText(String.valueOf(info.price));
        ((EditText)this.findViewById(R.id.min)).setText(String.valueOf(info.min));
        ((EditText)this.findViewById(R.id.max)).setText(String.valueOf(info.max));
        ((EditText)this.findViewById(R.id.items)).setText(String.valueOf(info.items));
    }

    private String format(double percent, double price){
        return String.format("%-15s%-19s\n", StringFormator.fomatePercent5(percent) + "%", StringFormator.formatPrice(price * (100 + percent) / 100));
    }
}
