package rondanet.cfe.core.utils.numbers;

public class NumberValidator {

    public static Integer obtenerValorNumerico(String str) {
        Integer number = 0;
        try {
            number = Integer.valueOf(str);
        } catch (Exception ex) {
            //no hacer nada
        }
        return number;
    }
}
