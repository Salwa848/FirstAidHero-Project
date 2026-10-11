package com.example.firstaidhero;

public class Step {
    private int stepNumber;
    private String instructionText;

    // المُنشئ لاستقبال رقم الخطوة ونص الإرشادات
    public Step(int stepNumber, String instructionText) {
        this.stepNumber = stepNumber;
        this.instructionText = instructionText;
    }

    // دوال الاسترجاع
    public int getStepNumber() {
        return stepNumber;
    }

    public String getInstructionText() {
        return instructionText;
    }
}