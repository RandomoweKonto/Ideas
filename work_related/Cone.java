package Ideas.work_related.cone;

import java.lang.Math;
import java.util.Scanner;

public class Cone {

    public static double WithBaseAngle(){
        System.out.println("Podaj kat tworzacy stozka (w stopniach).");
        Scanner scanner = new Scanner(System.in);

        double baseAngle = scanner.nextDouble();

        if(baseAngle<=0||baseAngle>=90){
            System.out.println("Podaj kat ostry!");
            WithBaseAngle();
        }
        return baseAngle*Math.PI/180;
    }
    public static double WithoutBaseAngle(double outerDiameter, double innerDiameter, double height, double thickness){
        double baseAngle = 0;
        for(double alfa=0; ((outerDiameter - innerDiameter)/2)*Math.tan(alfa) + thickness*Math.cos(alfa) - height < 0;alfa += 0.001){
            baseAngle = alfa;
        }
        if(baseAngle<=0 || baseAngle>Math.PI/2){
            System.out.println("Kat nieprawidlowy!");
            return 0;
        }
        return baseAngle;
    }

    public static double OuterDiameter(){
        System.out.println("Podaj zewnetrzna srednice stozka.");
        Scanner scanner = new Scanner(System.in);

        double outerDiameter = scanner.nextDouble();

        if(outerDiameter<=0){
            System.out.println("Podaj dodatnia wartosc!");
            OuterDiameter();
        }
        return outerDiameter;
    }
    public static double InnerDiameter(double outerDiameter){
        System.out.println("Podaj wewnetrzna srednice stozka.");
        Scanner scanner = new Scanner(System.in);

        double innerDiameter = scanner.nextDouble();

        if(innerDiameter<=0){
            System.out.println("Podaj dodatnia wartosc!");
            InnerDiameter(outerDiameter);
        }
        else if(innerDiameter>outerDiameter){
            System.out.println("Wewnetrzna srednica stozka musi byc mniejsza niz srednica zewnetrzna!");
            InnerDiameter(outerDiameter);
        }
        return innerDiameter;
    }

    public static double Height(){
        System.out.println("Podaj wysokosc stozka.");
        Scanner scanner = new Scanner(System.in);

        double height = scanner.nextDouble();

        if(height<=0){
            System.out.println("Podaj dodatnia wartosc!");
            Height();
        }
        return height;
    }

    public static double ThicknessWithBaseAngle(double baseAngle, double innerDiameter){
        System.out.println("Podaj grubosc blachy stozka.");
        Scanner scanner = new Scanner(System.in);

        double thickness = scanner.nextDouble();
        if(thickness<=0){
            System.out.println("Podaj dodatnia wartosc!");
            ThicknessWithBaseAngle(baseAngle, innerDiameter);
        }
        else if(innerDiameter<=2*thickness*Math.sin(baseAngle)){
            System.out.println("Podana grubosc blachy jest niepoprawna.");
            ThicknessWithBaseAngle(baseAngle,innerDiameter);
        }
        return thickness;
    }
    public static double ThicknessWithoutBaseAngle(double height){
        System.out.println("Podaj grubosc blachy stozka.");
        Scanner scanner = new Scanner(System.in);

        double thickness = scanner.nextDouble();

        if(thickness<=0){
            System.out.println("Podaj dodatnia wartosc!");
            ThicknessWithoutBaseAngle(height);
        }
        else if(thickness>height){
            System.out.println("Podana grubosc blachy jest niepoprawna.");
            ThicknessWithoutBaseAngle(height);
        }
        return thickness;
    }

    public static int ConeWithoutBaseAngle(){
        double outerDiameter = OuterDiameter();
        double innerDiameter = InnerDiameter(outerDiameter);
        double height = Height();
        double thickness = ThicknessWithoutBaseAngle(height);

        double baseAngle = WithoutBaseAngle(outerDiameter,innerDiameter,height,thickness);

        System.out.println("Kat tworzacy stozka wynoci "+String.format("%.4f",baseAngle)+" radianow ("+String.format("%.2f",baseAngle*180/Math.PI)+" stopni).");

        double centerOuterDiameter = outerDiameter - thickness*Math.sin(baseAngle);
        double centerInnerDiameter = innerDiameter - thickness*Math.sin(baseAngle);

        double laserOuterDiameter = 2*(centerOuterDiameter/2)/Math.cos(baseAngle);
        double laserInnerDiameter = 2*(centerInnerDiameter/2)/Math.cos(baseAngle);

        double laserCuttingOuterAlpha = (centerOuterDiameter/laserOuterDiameter)*2*Math.PI;
        double laserCuttingInnerAlpha = (centerInnerDiameter/laserInnerDiameter)*2*Math.PI;

        System.out.println("Sprawdzam poprawnosc oblcizen...");
        if(Math.abs(laserCuttingOuterAlpha-laserCuttingInnerAlpha)<=0.01){
            System.out.println("OK!");
        }
        else{
            System.out.println("Cos jest nie tak...");
        }

        System.out.println("Srednica zewnetrzna rozwiniecia wynosi "+String.format("%.2f",laserOuterDiameter)+" mm.");
        System.out.println("Srednica wewnetrzna rozwiniecia wynosi "+String.format("%.2f",laserInnerDiameter)+" mm.");
        System.out.println("Kat rozwiniecia stozka wynoci "+String.format("%.4f",laserCuttingOuterAlpha)+" radianow ("+String.format("%.2f",laserCuttingOuterAlpha*180/Math.PI)+" stopni).");

        double cuttingArea = Math.PI*(Math.pow(laserOuterDiameter/2000,2)-Math.pow(laserInnerDiameter/2000,2))*laserCuttingOuterAlpha/(2*Math.PI);
        System.out.println("Pole powierzchni rozwiniecia stozka wynosi "+String.format("%.4f",cuttingArea)+" metrow kwadratowych.");

        System.out.println("Podaj material, z jakiego wykonano stozek:");
        System.out.println("1. Stal nirdzewna");
        System.out.println("2. Stal konstrukcyjna (czarna).");
        System.out.println("3. Miedz.");

        double rho;

        Scanner scanner = new Scanner(System.in);
        int wybor = scanner.nextInt();
        switch (wybor){
            case 1:
                rho = 7900;
                break;
            case 2:
                rho = 7800;
                break;
            case 3:
                rho = 8960;
                break;
            default:
                System.out.println("Nie wiem z jakiego materialu jest stozek, przyjmuje wyjsciowo stal nierdzewna.");
                rho = 7900;
                break;
        }

        double cuttingMass = cuttingArea*thickness/1000*rho;
        System.out.println("Masa wycinka stozka wynosi "+String.format("%.3f",cuttingMass)+" kg.");

        return 0;
    }

    public static int ConeWithBaseAngle(){
        double outerDiameter = OuterDiameter();
        double innerDiameter = InnerDiameter(outerDiameter);
        double baseAngle = WithBaseAngle();
        double thickness = ThicknessWithBaseAngle(baseAngle, innerDiameter);

        double height = ((outerDiameter-innerDiameter)/2)*Math.tan(baseAngle)+thickness*Math.cos(baseAngle);

        System.out.println("Wysokosc stozka wynoci "+String.format("%.0f",height)+" mm");

        double centerOuterDiameter = outerDiameter - thickness*Math.sin(baseAngle);
        double centerInnerDiameter = innerDiameter - thickness*Math.sin(baseAngle);

        double laserOuterDiameter = 2*(centerOuterDiameter/2)/Math.cos(baseAngle);
        double laserInnerDiameter = 2*(centerInnerDiameter/2)/Math.cos(baseAngle);

        double laserCuttingOuterAlpha = (centerOuterDiameter/laserOuterDiameter)*2*Math.PI;
        double laserCuttingInnerAlpha = (centerInnerDiameter/laserInnerDiameter)*2*Math.PI;

        System.out.println("Sprawdzam poprawnosc obliczen...");
        if(Math.abs(laserCuttingOuterAlpha-laserCuttingInnerAlpha)<=0.01){
            System.out.println("OK!");
        }
        else{
            System.out.println("Cos jest nie tak...");
        }

        System.out.println("Srednica zewnetrzna rozwiniecia wynosi "+String.format("%.2f",laserOuterDiameter)+" mm.");
        System.out.println("Srednica wewnetrzna rozwiniecia wynosi "+String.format("%.2f",laserInnerDiameter)+" mm.");
        System.out.println("Kat rozwiniecia stozka wynoci "+String.format("%.4f",laserCuttingOuterAlpha)+" radianow ("+String.format("%.2f",laserCuttingOuterAlpha*180/Math.PI)+" stopni).");

        double cuttingArea = Math.PI*(Math.pow(laserOuterDiameter/2000,2)-Math.pow(laserInnerDiameter/2000,2))*laserCuttingOuterAlpha/(2*Math.PI);
        System.out.println("Pole powierzchni rozwiniecia stozka wynosi "+String.format("%.4f",cuttingArea)+" metrow kwadratowych.");

        System.out.println("Podaj material, z jakiego wykonano stozek:");
        System.out.println("1. Stal nirdzewna");
        System.out.println("2. Stal konstrukcyjna (czarna).");
        System.out.println("3. Miedz.");

        double rho;

        Scanner scanner = new Scanner(System.in);
        int wybor = scanner.nextInt();
        switch (wybor){
            case 1:
                rho = 7900;
                break;
            case 2:
                rho = 7800;
                break;
            case 3:
                rho = 8960;
                break;
            default:
                System.out.println("Nie wiem z jakiego materialu jest stozek, przyjmuje wyjsciowo stal nierdzewna.");
                rho = 7900;
                break;
        }

        double cuttingMass = cuttingArea*thickness/1000*rho;
        System.out.println("Masa wycinka stozka wynosi "+String.format("%.3f",cuttingMass)+" kg.");

        return 0;
    }

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Jaka metoda chcesz obliczyc stozek?");
        System.out.println("1. Mam wysokosc stozka, oblicz kat tworzacy.");
        System.out.println("2. Mam kat tworzacy stozka, oblicz wysokosc.");

        int wybor = scanner.nextInt();

        if(wybor==1){
            ConeWithoutBaseAngle();
        }
        else if(wybor==2){
            ConeWithBaseAngle();
        }
        else{
            System.out.println("Zly wybor!");
            System.exit(0);
            return;
        }
    }

}
