package cn.edu.tjut.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnC;
    private Button btnDEL;
    private Button btnDiv;
    private Button btnMul;
    private Button btnAdd;
    private Button btnSub;
    private Button btnEq;
    private Button btnDot;
    private TextView textSm;
    private TextView textLg;

    //操作数数组
    private float[] operArr = new float[10];
    //操作符数组
    private char[] symbols = new char[10];
    //标志最后一位是小数还是整数，true代表整数
    private boolean flag = true;
    //标志当前操作数的个数
    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElem();
        setBtnHandler();
    }

    private void setBtnHandler() {
        btn0.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn1.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn2.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn3.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn4.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn5.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn6.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn7.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn8.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btn9.setOnClickListener((View v) -> {
            numBtnHandler(v);
        });
        btnAdd.setOnClickListener((View v) -> {
            symbolBtnHandler(v);
        });
        btnSub.setOnClickListener((View v) -> {
            symbolBtnHandler(v);
        });
        btnMul.setOnClickListener((View v) -> {
            symbolBtnHandler(v);
        });
        btnDiv.setOnClickListener((View v) -> {
            symbolBtnHandler(v);
        });
        btnDot.setOnClickListener((View v) -> {
            symbolBtnHandler(v);
        });
        btnEq.setOnClickListener((View v) -> {
            float result = 0;
            String lgTxt = (String) textLg.getText();
            //如果最后一个字符不为数字，则打印错误并退出
            if(lgTxt.length() > 0){
                char lastC = lgTxt.charAt(lgTxt.length() - 1);
                if(!Character.isDigit(lastC)){
                    //复位计算器
                    resetCalculator();
                    textSm.setText("Error!");
                    return;
                }
            }else{
                return ;
            }
            if (cont >= 1) {
                result = operArr[1];
            } else if (cont == 0) {
                return;
            }
            for (int i = 1; i < cont; i++) {
                switch (symbols[i - 1]) {
                    case '+':
                        result = result + operArr[i + 1];
                        break;
                    case '-':
                        result = result - operArr[i + 1];
                        break;
                    case '*':
                        result = result * operArr[i + 1];
                        break;
                    case '/':
                        result = result / operArr[i + 1];
                        break;
                }
            }
            //将结果当做操作数存为第一个操作数
            cont = 1;
            operArr[1] = result;
            symbols[0] = '\0';
            textSm.setText(lgTxt);
            if (isFloat(operArr[cont])) {
                textLg.setText(result + "");
            } else {
                textLg.setText((int) result + "");
            }
        });
        //清空
        btnDEL.setOnClickListener((View) -> {
            resetCalculator();
        });
        //删除一个字符
        btnC.setOnClickListener((View) -> {
            String lgTxt = (String) textLg.getText();
            if (lgTxt.length() == 0) return;
            char c = lgTxt.charAt(lgTxt.length() - 1);
            if (c == '.') {
                flag = true;
            } else if (Character.isDigit(c)) {
                String numStr = null;
                if (isFloat(operArr[cont])) {
                    numStr = operArr[cont] + "";
                } else {
                    numStr = Integer.toString((int) operArr[cont]);
                }
                operArr[cont] = Float.parseFloat(numStr.substring(0, numStr.length() - 1));
            } else if (lgTxt.length() == 1) {
                cont = 0;
            }
            textLg.setText(lgTxt.substring(0, lgTxt.length() - 1));
        });
    }

    /**
     * 复位计算器
     */
    private void resetCalculator() {
        textLg.setText("");
        textSm.setText("");
        cont = 0;
    }

    /**
     * 记号按钮处理函数，包括:. + / * -
     *
     * @param v
     */
    private void symbolBtnHandler(View v) {
        Button btn = (Button) v;
        char symbol = (char) btn.getText().charAt(0);
        String lgTxt = (String) textLg.getText();
        //如果没有任何字符，则输入符号无效
        if (lgTxt.length() == 0) return;
        //获得文本最后一个字符
        char lastC = lgTxt.charAt(lgTxt.length() - 1);
        //最后一个字符为数字，并且按下小数点
        if (Character.isDigit(lastC) && symbol == '.') {
            if (isFloat(operArr[cont])) return;
            flag = false;
        }//最后一个字符为数字，并且按下操作操作符
        else if (Character.isDigit(lastC) && symbol != '.') {
            symbols[cont - 1] = symbol;
            flag = true;
        }//最后一个非数字，并且按下小数点
        else if (!Character.isDigit(lastC) && symbol == '.') {
            if (isFloat(operArr[cont])) return;
            flag = false;
            //替换掉原来的符号
            lgTxt = lgTxt.substring(0, lgTxt.length() - 1);
            //去除符号数组中的符号
        }//最后一个非数字，并且按下操作符
        else if (!Character.isDigit(lastC) && symbol != '.') {
            symbols[cont - 1] = symbol;
            flag = true;
            lgTxt = lgTxt.substring(0, lgTxt.length() - 1);
        }
        textLg.setText(lgTxt + Character.toString(symbol));
    }

    /**
     * 判断一个数是否为浮点数
     *
     * @param num
     * @return
     */
    private boolean isFloat(float num) {
        double eps = 1e-15;  // 精度范围
        //转换为整数方便判断
        num = Math.abs(num);
        //大于精度范围则为浮点数
        if (num - (double) ((int) num) > eps) return true;
        return false;
    }

    /**
     * 数字按钮处理函数
     *
     * @param v
     */
    private void numBtnHandler(View v) {
        Button btn = (Button) v;
        int num = (int) Integer.parseInt((String) btn.getText());
        //获取大文本框中的字符串
        String lgTxt = (String) textLg.getText();
        float operNum = 0;
        if (lgTxt.length() > 0) {
            char lastC = lgTxt.charAt(lgTxt.length() - 1);
            //如果最后一位不是数字也不是小数点，则肯定是运算符，因此要新增加一个操作数
            if (!Character.isDigit(lastC) && lastC != '.') {
                cont++;
            }//如果最后一个操作数为0，并且按下的按键为0，或者大于15个字符时直接返回。
            else if (0 == operArr[cont] && 0 == num || lgTxt.length() >= 15) {
                return;
            } else {
                operNum = operArr[cont];
            }
        } else {
            cont++;
        }
        //计算操作数，并存入操作数组中
        if (flag) operArr[cont] = operNum * 10 + num;
        else operArr[cont] = (float) (operNum + num * 0.1);
        //显示字符
        if (lgTxt.length() == 1 && 0 == operNum) textLg.setText(lgTxt);
        else textLg.setText(lgTxt + btn.getText());
        Log.i("num", operArr[cont] + "");
    }

    private void initElem() {
        btn0 = (Button) findViewById(R.id.btn_0);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        btnC = (Button) findViewById(R.id.btn_c);
        btnDEL = (Button) findViewById(R.id.btn_del);
        btnDiv = (Button) findViewById(R.id.btn_divide);
        btnMul = (Button) findViewById(R.id.btn_multiply);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnSub = (Button) findViewById(R.id.btn_sub);
        btnEq = (Button) findViewById(R.id.btn_eq);
        btnDot = (Button) findViewById(R.id.btn_dot);
        textSm = (TextView) findViewById(R.id.text_sm);
        textLg = (TextView) findViewById(R.id.text_lg);
    }
}
