import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // A+B, A-B, A*B, A/B
    String A = "0";
    String operator = null;
    String B = null;

    Calculator() { //Label goes into the Panel, which then goes into the Frame. 
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack); //Label color
        displayLabel.setForeground(Color.white); //Text Color 
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true); //Label is solid and cannot be seen THROUGH

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for(int i = 0; i< buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];

            button.setContentAreaFilled(false);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorderPainted(true);
            button.setBorder(new LineBorder(customBlack));
            button.setOpaque(true);

            if(Arrays.asList(topSymbols).contains(buttonValue)) {
                
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                 button.setBackground(customOrange);
                 button.setForeground(Color.white);
            }
            else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if(buttonValue == "=") {
                            if(A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if(operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                }
                                else if(operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                }
                                else if(operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                }
                                else if(operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                            }
                        }
                        else if(("+-×÷").contains(buttonValue)) {
                            if(operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    else if(Arrays.asList(topSymbols).contains(buttonValue)) {
                        if(buttonValue == "AC") {
                            clearAll(); 
                            displayLabel.setText("0");
                        }
                        else if(buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if(buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                    else { //digits or.
                        if(buttonValue == ".") {
                            if(!displayLabel.getText().contains(buttonValue)) { // We only want 1 decimal point, this ensures that only when there is no "." we can add one to the display
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if("0123456789".contains(buttonValue)) {
                            if(displayLabel.getText() == "0") { // if display text is just 0, we dont want 05 when we click 5, we want 5 only. This is a special case.
                                displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue); // display text + new inputs
                            }
                        }
                        else if(buttonValue == "√") {
                            if(Double.parseDouble(displayLabel.getText()) >= 0) {
                                displayLabel.setText(removeZeroDecimal(Math.pow(Double.parseDouble(displayLabel.getText()),0.5)));
                            }
                        }
                    }

                }
            });
            frame.setVisible(true); //we do this last because slow computers will render weird/inconsistently, which messes up how calculator looks

        
        }
        
        }
        void clearAll() {
            A = "0";
            B = null;
            operator = null;
        }
        String removeZeroDecimal(double numDisplay) {
            if(numDisplay % 1 == 0) {
                return Integer.toString((int)numDisplay);
            }
            return Double.toString(numDisplay);
        }
}
