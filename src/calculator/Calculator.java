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


public class Calculator implements ActionListener{
    JFrame window;
    int frameX = 400;
    int frameY = 400;
    JButton button[];
    JTextField textField;
    Stack<Double> values;
    Stack<Character> operators;
    int buttonX = 80;
    int gapX;
    int buttonY = 60;
    int gapY;
    
    Calculator(){
        gapX = (frameX - 4*buttonX)/5;
        gapY = (frameY - 45 - 4*buttonY)/5;
        
        String s = new String("789/456*123-C0+=");
        
        window = new JFrame("Calculator");
        window.setSize(frameX, frameY);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.setLayout(null);
        
 //       window.add(Box.createVerticalStrut(60));
        textField = new JTextField("");
        Font f = new Font("Arial",Font.LAYOUT_LEFT_TO_RIGHT, 30);
        textField.setFont(f);
        textField.setBounds(10, 10, 378, 35);
        window.add(textField);

        button = new JButton[16];
        for(int i = 0; i< 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                button[i*4 + j] = new JButton(s.substring(i*4 + j, i*4+j+1));
                button[i*4 + j].setBounds(j * (buttonX + gapX) + gapX,i*(buttonY + gapY) + 50, buttonX, buttonY);
                window.add(button[i*4 + j]);
            }
        }
        button[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText(textField.getText() + "7");
            }
        });

        window.setVisible(true);
    }
    public static void main(String[] args) {
           new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
