package br.com.erudio.rest_spring_java_erudio.math;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double sub(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multi(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo)  {
        return numberOne / numberTwo;
    }

    public Double mean(Double numberOne, Double numberTwo) {
        double media = (numberOne + numberTwo) / 2;
        return media;
    }

    public Double squareRoot(Double number){
        return Math.sqrt(number);
    }
}
