package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;

public class Calculator implements ActionListener {

    JFrame window;
    int frameX = 400;
    int frameY = 400;
    JButton button[];
    JTextField textField;
    JTextField textStack;
    Stack<Double> values;
    Stack<String> operators;

    int buttonX = 60;
    int gapX;
    int buttonY = 60;
    int gapY;
    String s;

    Calculator() {
        gapX = (frameX - 5 * buttonX) / 6;
        gapY = (frameY - 45 - 4 * buttonY) / 5;

        s = new String("789()456*/123+-C0.=^");

        window = new JFrame("Calculator");
        window.setSize(frameX, frameY);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        
        Font f = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 30);
        
        window.add(Box.createVerticalStrut(5));
        
        textStack = new JTextField();
        textStack.setFont(f);
        textStack.setMaximumSize(new Dimension(frameX - 10, 35));
        window.add(textStack);
        
        window.add(Box.createVerticalStrut(5));

        textField = new JTextField("");

        textField.setFont(f);
        textField.setMaximumSize(new Dimension(frameX - 10, 35));
        window.add(textField);

        window.add(Box.createVerticalStrut(10));

        JPanel panel = new JPanel(new GridLayout(4, 5, gapY, gapX));
        button = new JButton[20];
        for (int i = 0; i < 20; i++) {
            button[i] = new JButton(s.substring(i, i + 1));
            button[i].addActionListener(this);
            button[i].setFont(f);
            panel.add(button[i]);
        }
        panel.setMaximumSize(new Dimension(frameX-10,frameY-122));
        window.add(panel);

        values = new Stack<>();
        operators = new Stack<>();

        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
        String choice = ((JButton) e.getSource()).getText();
        switch (choice) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
            case ".":
                textField.setText(textField.getText() + choice);
                textStack.setText(textStack.getText() + choice);
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                textStack.setText(textStack.getText() + " " + choice + " ");
                Push(choice);
                textField.setText("");
                break;
            case "(":
            case ")":
                textStack.setText(textStack.getText() + " " + choice + " ");
                Push(choice);
                textField.setText("");
                break;
            case "=":
                if(!textField.getText().equals(""))
                    values.push(Double.valueOf(textField.getText()));
                ShowAnswer();
                break;
            default:
                Clear();
        }
        }
        catch(Exception ex)
        {
            Clear();
            textField.setText("Błędne wyrażenie!");
        }
    }

    private void Push(String choice) {
        if(choice.equals("("))
        {
            if(!textField.getText().equals(""))
            {
                values.push(Double.valueOf(textField.getText()));
                operators.push("*");
            }
            operators.push(choice);
        }
        else if (!choice.equals(")")) {
            while (!operators.isEmpty() && Priority(choice) < Priority(operators.peek())) {
                Calculate();
            }
            operators.push(choice);
            if(!textField.getText().equals(""))
                values.push(Double.valueOf(textField.getText()));
        } else {
            if(!textField.getText().equals(""))
                values.push(Double.valueOf(textField.getText()));
            while (!operators.isEmpty() && !operators.peek().equals("(")) {
                Calculate();
            }
            operators.pop();
        }
    }

    private void Clear() {
        operators.clear();
        values.clear();
        textField.setText("");
        textStack.setText("");
        
    }

    private int Priority(String choice) {
        switch (choice) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 0;
        }
    }

    private void Calculate() {
        Double x = values.pop();
        Double y = values.pop();
        switch (operators.pop()) {
            case "+":
                values.push(y + x);
                break;
            case "-":
                values.push(y - x);
                break;
            case "*":
                values.push(y * x);
                break;
            case "/":
                values.push(y / x);
                break;
            case "^":
                values.push(Math.pow(y, x));
                break;
        }
    }

    private void ShowAnswer() {
        while (!operators.isEmpty()) {
            Calculate();
        }

        String ans = values.peek().toString();
        int pos = ans.indexOf(".");
        if (pos != -1 && ans.length() > 10) {
            textField.setText(ans.substring(0, ans.length() - 2));
            //textStack.setText(ans.substring(0, ans.length() - 2));
        } else {
            textField.setText(ans);
           // textStack.setText(ans);
        }
        textStack.setText("( " + textStack.getText() + " )");
    }

}
