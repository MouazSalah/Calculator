package com.example.smartcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{

    private TextView resultTextView;
    private String statment = "" ;
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.result_textview);
        resultTextView.setText(statment);
    }

    private void updateScreen()
    {
         resultTextView.setText(statment);
    }

    public void onClickNumber(View v)
    {
        if(result != "")
        {
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        statment += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op)
    {
        switch (op)
        {
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;
        }
    }

    public void onClickOperator(View v)
    {
        if(statment == "") return;

        Button b = (Button)v;

        if(result != "")
        {
            String _display = result;
            clear();
            statment = _display;
        }

        if(currentOperator != "")
        {
            if(isOperator(statment.charAt(statment.length()-1)))
            {
                statment = statment.replace(statment.charAt(statment.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }
            else
            {
                getResult();
                statment = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }

        statment += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        statment = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op)
    {
        switch (op)
        {
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "÷": try
            {
                return Double.valueOf(a) / Double.valueOf(b);
            }
            catch (Exception e)
            {
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getResult()
    {
        if(currentOperator == "") return false;
        String[] operation = statment.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v)
    {
        if(statment == "") return;
        if(!getResult()) return;
        resultTextView.setText(statment + "\n" + String.valueOf(result));
    }

    public void onClickDelete(View v)
    {
        if (statment.equals(""))
        {
            statment = "";
            resultTextView.setText("");
        }
        else
        {
            statment = statment.substring(0, statment.length() - 1);
            resultTextView.setText(statment);
        }
    }
}



    /*
    Button button;
    TextView resultTextView;
    String statment = "";
    String currentOperator = "";
    double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.result_textview);
        resultTextView.setText(statment);
    }

    public void updateScreen()
    {
        resultTextView.setText(statment);
    }

    public void onClickNumber(View v)
    {
        button = (Button) v;

        if (result != 0)
        {
            result = 0;
            statment += button.getText();
            updateScreen();
        }
        else if (statment.contains("+") || statment.contains("+") || statment.contains("x") || statment.contains("x") || statment.contains("÷"))
        {
            statment += button.getText();
            updateScreen();
        }
        else
        {
            Button button = (Button) v;
            statment += button.getText();
            updateScreen();
        }
    }

    public void onClickOperator(View v)
    {
        if(statment == "") return;

        Button b = (Button)v;

        if(result != 0)
        {
            double s = result;
            clear();
            statment += s ;
        }

        if(currentOperator != "")
        {
            if(checkOperator(statment.charAt(statment.length()-1)))
            {
                statment = statment.replace(statment.charAt(statment.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }
            else
            {
                showResult();
                statment = "";
                statment += result;
                result = 0;
            }
            currentOperator = b.getText().toString();
        }

        statment += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }



    private void clear()
    {
        statment = "";
        currentOperator = "";
        result = 0;
    }

    public boolean checkOperator(char op)
    {
        switch (op)
        {
            case '+': return true;
            case '-': return true;
            case 'x': return true;
            case '÷': return true;
            default: return false;
        }

    }
    public void onClickEqual(View v)
    {
        if (result != 0)
        {
            statment = "" + result;
            result = 0;
            updateScreen();
        }

        if (statment == "")
        {
            result = 0;
            resultTextView.setText("0");
        }
        else
        {
            operate(statment);
        }
    }

    private void operate(String s)
    {
        if (s.length() < 2 && currentOperator.equals(""))
        {
            return;
        }
        else
        {
            String[] elements = statment.split(Pattern.quote(currentOperator));
            String a = elements[0];
            String b = elements[1];

            switch (currentOperator)
            {
                case "+":
                    result = Integer.parseInt(a) + Integer.parseInt(b);
                    showResult();
                    break;
                case "-":
                    result = Integer.parseInt(a) - Integer.parseInt(b);
                    showResult();
                    break;
                case "x":
                    result = Integer.parseInt(a) * Integer.parseInt(b);
                    showResult();
                    break;
                case "÷":
                    if(b.equals(0))
                    {
                        resultTextView.setText("Can not be divided by zero");
                        result = 0;
                        currentOperator = "";
                        statment = "";
                    }
                    else
                    {
                        result = Double.parseDouble(a) / Double.parseDouble(b);
                        showResult();
                    }

                    break;
            }
        }
    }

    private void showResult()
    {
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));

        double x = result - Math.floor(result);
        if (x == 0)
        {
            result = (int) result;
        }

        resultTextView.setText("" + result);


        statment = "";
        currentOperator = "";
    }

    public void onClickClear(View v)
    {
        statment = "";
        currentOperator = "";
        result = 0;
        resultTextView.setText("" + 0);
    }


    public void onClickDelete(View v)
    {
        if (statment.equals(""))
        {
            statment = "";
            resultTextView.setText("");
        }
        else
        {
            statment = statment.substring(0, statment.length() - 1);
            resultTextView.setText(statment);
        }
    }







}
 */