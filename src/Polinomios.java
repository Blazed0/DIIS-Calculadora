import org.w3c.dom.Text;

import java.awt.*;
import java.util.Locale;

public class Polinomios {
    // Crea la tarjeta provisional de bisección
    public Panel crearPanelBiseccion() {
        Panel panelPrincipal = new Panel(new BorderLayout(10,10));

        Label titulo = new Label("Raices de Polinomios por Bisección", Label.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        TextField polinomio= new TextField(26);

        Panel panelPolinomio = new Panel(new FlowLayout(FlowLayout.LEFT));
        panelPolinomio.add(new Label("Polinomio:"));
        panelPolinomio.add(polinomio);

        TextField limiteA = new TextField(8);
        TextField limiteB = new TextField(8);

        Panel panelIntervalo = new Panel(new FlowLayout(FlowLayout.LEFT));

        panelIntervalo.add(new Label("Extremo Inferior:"));
        panelIntervalo.add(limiteA);
        panelIntervalo.add(new Label("Extremo Superior:"));
        panelIntervalo.add(limiteB);

        Panel biseccion = new Panel(new GridLayout(2,1,5,5));
        biseccion.add(panelPolinomio);
        biseccion.add(panelIntervalo);

        panelPrincipal.add(biseccion, BorderLayout.CENTER);

        Button calcularRaiz = new Button("Calcular Raíz");
        TextField campoResultado = new TextField(32);
        campoResultado.setEditable(false);

        Panel panelResultados = new Panel(new FlowLayout(FlowLayout.CENTER));
        panelResultados.add(calcularRaiz);
        panelResultados.add(new Label("Resultado:"));
        panelResultados.add(campoResultado);

        panelPrincipal.add(panelResultados, BorderLayout.SOUTH);
        calcularRaiz.addActionListener(e -> {
            try{
                 double a = Double.parseDouble(limiteA.getText());
                 double b = Double.parseDouble(limiteB.getText());

                double calculoBiseccion = biseccionar(a, b, polinomio.getText());
                campoResultado.setText(String.valueOf(calculoBiseccion));
            }catch (IllegalStateException ex){
                campoResultado.setText("Error en el calculo: " + ex.getMessage());
            }
        });
        return panelPrincipal;
    }

    // Método para evaluar el polinomio
    private static double evaluarPolinomio(Double[] coeficientes, double x, String[] terminos){
        double resultado = 0;
        for (int i=0; i<coeficientes.length; i++){
            int exponente = extraerExponentes(terminos[i]);
            resultado += coeficientes[i] * Math.pow(x, exponente);
        }
        return resultado;
    }

    // Método para extraer los términos
    private static String[] extraerTerminos(String polinomio){
        String normalizado = polinomio
                .replace("−", "-")
                .replace(" ", "")
                .replace("-", "+-");

        return normalizado.split("\\+");
    }

    // Método para extraer los exponentes
    private static int extraerExponentes(String termino){
        String polinomioNormalizado = termino
                .toLowerCase()
                .replace("⁴", "^4")
                .replace("³", "^3")
                .replace("²", "^2");

        int posicionX = polinomioNormalizado.indexOf("x");
        if(posicionX == -1){
            return 0;
        }
        int exponente = polinomioNormalizado.indexOf("^");
        if(exponente != -1){
            String valorExponente = polinomioNormalizado.substring(exponente + 1);
            return Integer.parseInt(valorExponente);
        }

        return 1;
    }
    // Método para extraer Coeficientes
    private static double extraerCoeficientes(String termino){
        int posicionX = termino.toLowerCase().indexOf("x");

        if(posicionX == -1){
            return Double.parseDouble(termino);
        }

        String coeficiente = termino.substring(0, posicionX);

        if(coeficiente.isEmpty()){
            return 1;
        }
        if(coeficiente.equals("-")){
            return -1;
        }

        return Double.parseDouble(coeficiente);
    }

    // Método principal de bisección
    public double biseccionar(double a, double b, String polinomio){
        if(a >= b){
            throw new IllegalStateException("El limite superior no puede ser menor al limite inferior");
        }

        String [] terminos = extraerTerminos(polinomio);
        Double[] coeficientes = new Double[terminos.length];
        for(int i= 0; i < terminos.length; i++) {
            coeficientes[i] = extraerCoeficientes(terminos[i]);
        }

        double fM = 1;
        double fB = 0;
        double fA = 0;
        double m = 0;

        for(int i = 0; i<20; i++) {
            if (i == 0) {
                fB = evaluarPolinomio(coeficientes, b, terminos);
                fA = evaluarPolinomio(coeficientes, a, terminos);
                if (fA == 0) {
                    System.out.println("Raíz encontrada en el extremo inferior: " + fA);
                    return a;
                } else if (fB == 0) {
                    System.out.println("Raiz encontrada en el extremo superior: " + fB);
                    return b;
                }

                if (fA * fB > 0) {
                    throw new IllegalStateException("El rango actual no sirve para encontrar la raiz");
                }
            }
            m = a + (b - a)/ 2;
            fM = evaluarPolinomio(coeficientes, m, terminos);

            double medioAncho = (b-a)/2;
            double tolerancia = 0.000001;

            if(fM == 0){
                System.out.println("Raíz encontrada en " + m + " Exactamente igual a 0");
                return m;
            }

            if (medioAncho <= tolerancia) {
                System.out.println("Raiz encontrada en " + m + " Con una tolerancia de " + tolerancia);
                return m;
            }

            if (fA * fM < 0) {
                b = m;
                fB = fM;
            } else {
                a = m;
                fA = fM;
            }
        }
        throw new IllegalStateException("No se alcanzó la tolerancia mínima en el máximo de iteraciones");
    }
}
