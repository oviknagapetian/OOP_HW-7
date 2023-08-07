package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Модель (Model) - класс, выполняющий арифметические операции с комплексными числами
class ComplexCalculatorModel {
    public ComplexNumber add(ComplexNumber num1, ComplexNumber num2) {
        double real = num1.getReal() + num2.getReal();
        double imaginary = num1.getImaginary() + num2.getImaginary();
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber multiply(ComplexNumber num1, ComplexNumber num2) {
        double real = num1.getReal() * num2.getReal() - num1.getImaginary() * num2.getImaginary();
        double imaginary = num1.getReal() * num2.getImaginary() + num1.getImaginary() * num2.getReal();
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber divide(ComplexNumber num1, ComplexNumber num2) {
        double denominator = num2.getReal() * num2.getReal() + num2.getImaginary() * num2.getImaginary();
        double real = (num1.getReal() * num2.getReal() + num1.getImaginary() * num2.getImaginary()) / denominator;
        double imaginary = (num1.getImaginary() * num2.getReal() - num1.getReal() * num2.getImaginary()) / denominator;
        return new ComplexNumber(real, imaginary);
    }
}

// Представление (View) - пользовательский интерфейс
class ComplexCalculatorView extends JFrame {
    private JTextField realField1;
    private JTextField imaginaryField1;
    private JTextField realField2;
    private JTextField imaginaryField2;
    private JComboBox<String> operatorComboBox;
    private JLabel resultLabel;
    private JButton calculateButton;

    public ComplexCalculatorView() {
        setTitle("Калькулятор комплексных чисел");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        realField1 = new JTextField(5);
        imaginaryField1 = new JTextField(5);
        realField2 = new JTextField(5);
        imaginaryField2 = new JTextField(5);
        operatorComboBox = new JComboBox<>(new String[]{"+", "*", "/"});
        resultLabel = new JLabel("Результат: ");
        calculateButton = new JButton("Вычислить");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String operator = (String) operatorComboBox.getSelectedItem();
                double real1 = Double.parseDouble(realField1.getText());
                double imaginary1 = Double.parseDouble(imaginaryField1.getText());
                double real2 = Double.parseDouble(realField2.getText());
                double imaginary2 = Double.parseDouble(imaginaryField2.getText());

                ComplexNumber num1 = new ComplexNumber(real1, imaginary1);
                ComplexNumber num2 = new ComplexNumber(real2, imaginary2);

                ComplexPresenter presenter = new ComplexPresenter(new ComplexCalculatorModel(), ComplexCalculatorView.this);
                presenter.onCalculate(operator, num1, num2);
            }
        });

        add(realField1);
        add(new JLabel(" + "));
        add(imaginaryField1);
        add(new JLabel("i"));

        add(operatorComboBox);

        add(realField2);
        add(new JLabel(" + "));
        add(imaginaryField2);
        add(new JLabel("i"));

        add(calculateButton);
        add(resultLabel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void displayResult(ComplexNumber result) {
        resultLabel.setText("Результат: " + result);
    }
}

// Презентер (Presenter) - связывает Model и View, обрабатывает пользовательские действия
class ComplexPresenter {
    private ComplexCalculatorModel model;
    private ComplexCalculatorView view;

    public ComplexPresenter(ComplexCalculatorModel model, ComplexCalculatorView view) {
        this.model = model;
        this.view = view;
    }

    public void onCalculate(String operator, ComplexNumber num1, ComplexNumber num2) {
        ComplexNumber result = null;
        switch (operator) {
            case "+":
                result = model.add(num1, num2);
                break;
            case "*":
                result = model.multiply(num1, num2);
                break;
            case "/":
                result = model.divide(num1, num2);
                break;
            default:
                break;
        }

        view.displayResult(result);
    }
}

// Класс, представляющий комплексное число
class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        return real + " + " + imaginary + "i";
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ComplexCalculatorView();
        });
    }
}
