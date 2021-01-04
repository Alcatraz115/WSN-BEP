package classes;

public class MathHelper {
    static Double getRandom(Double min,Double max){
        return (Math.random() * (max - min)) + min;
    }

    static Integer getRandomInteger(Integer min,Integer max){
        return MathHelper.getRandom(min.doubleValue(),max.doubleValue()).intValue();
    }

    static Double sigmaFunc(Double x){
        return 1.0 / (1.0 + Math.exp(-1.0 * x));
    }

    static Double sigmaPochodnaFunc(Double x){
        return MathHelper.sigmaFunc(x) * (1.0 - MathHelper.sigmaFunc(x));
    }
}
