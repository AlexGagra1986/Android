package ru.geekbrains.calculator;


import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {
    private int buttonOne;
    private int buttonTwo;
    private StringBuilder stringBuilder = new StringBuilder();
    private int choice;
    private Base base;

    protected Calculator(Parcel in) {
        buttonOne = in.readInt();
        buttonTwo = in.readInt();
        choice = in.readInt();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(buttonOne);
        dest.writeInt(buttonTwo);
        dest.writeInt(choice);
    }


    private enum Base {
        firstInput,
        operations,
        secondInput,
        result
    }

    public Calculator() {
        base = Base.firstInput;
    }

    public void onNumPressed(int buttonId) {

        if (base == Base.result) {
            base = Base.firstInput;
            stringBuilder.setLength(0);
        }

        if (base == Base.operations) {
            base = Base.secondInput;
            stringBuilder.setLength(0);
        }

        if (stringBuilder.length() < 9) {
            switch (buttonId) {
                case R.id.zero:
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append("0");
                    }
                    break;
                case R.id.btn1:
                    stringBuilder.append("1");
                    break;
                case R.id.btn2:
                    stringBuilder.append("2");
                    break;
                case R.id.btn3:
                    stringBuilder.append("3");
                    break;
                case R.id.btn4:
                    stringBuilder.append("4");
                    break;
                case R.id.btn5:
                    stringBuilder.append("5");
                    break;
                case R.id.btn6:
                    stringBuilder.append("6");
                    break;
                case R.id.btn7:
                    stringBuilder.append("7");
                    break;
                case R.id.btn8:
                    stringBuilder.append("8");
                    break;
                case R.id.btn9:
                    stringBuilder.append("9");
                    break;
            }
        }

    }

    public StringBuilder onActionPressed(int actionId) {
        if (stringBuilder.length() > 0 && base == Base.firstInput && actionId != R.id.equals) {
            buttonOne = Integer.parseInt(stringBuilder.toString());
            base = Base.operations;
            choice = actionId;}
        if (actionId == R.id.equals && base == Base.secondInput && stringBuilder.length() > 0) {
            buttonTwo = Integer.parseInt(stringBuilder.toString());
            base = Base.result;
            stringBuilder.setLength(0);

            if(choice==R.id.plus){
                stringBuilder.append(buttonOne + buttonTwo);
                return stringBuilder ;
            }
            if(choice == R.id.minus){
                stringBuilder.append(buttonOne - buttonTwo);
            }
            if(choice == R.id.multiply){
                stringBuilder.append(buttonOne * buttonTwo);
                return stringBuilder ;
            }
            if(choice == R.id.division){
                stringBuilder.append(buttonOne / buttonTwo);
                return stringBuilder ;

            }
        }
        return null;
    }

    public String getText() {
        StringBuilder str = new StringBuilder();
        switch (base) {
            default:
                return stringBuilder.toString();
            case operations:
                return str.append(buttonOne).append(' ')
                        .append(getOperationChar())
                        .toString();
            case secondInput:
                return str.append(buttonOne).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(stringBuilder)
                        .toString();
            case result:
                return str.append(buttonOne).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(buttonTwo)
                        .append(" = ")
                        .append(stringBuilder.toString())
                        .toString();
        }
    }

    private char getOperationChar() {
        switch (choice) {
            case R.id.plus:
                return '+';
            case R.id.minus:
                return '-';
            case R.id.multiply:
                return '*';
            case R.id.division:
            default:
                return '/';

        }
    }

    public void reset() {
        base = Base.firstInput;
        stringBuilder.setLength(0);
    }
}