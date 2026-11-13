import java.awt.*;

public class Distancia {
    // Crea la tarjeta provisional de distancia
    public Panel crearPanelDistancia() {
        Panel panelPrincipal = new Panel(new BorderLayout(10,10));
        Label titulo = new Label("Distancia entre 2 puntos", Label.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        TextField campoX1 = new TextField(8);
        TextField campoY1 = new TextField(8);
        TextField campoX2 = new TextField(8);
        TextField campoY2 = new TextField(8);

        Panel puntoA = new Panel(new FlowLayout(FlowLayout.LEFT));
        puntoA.add(new Label("Punto A - X1: "));
        puntoA.add(campoX1);
        puntoA.add(new Label("Y1: "));
        puntoA.add(campoY1);

        Panel puntoB = new Panel(new FlowLayout(FlowLayout.LEFT));
        puntoB.add(new Label("Punto B - X2: "));
        puntoB.add(campoX2);
        puntoB.add(new Label("Y2: "));
        puntoB.add(campoY2);

        Panel coordenadas = new Panel(new GridLayout(2,1,5,5));
        coordenadas.add(puntoA);
        coordenadas.add(puntoB);

        panelPrincipal.add(coordenadas, BorderLayout.CENTER);

        Button calcular = new Button("Calcular Distancia");
        TextField campoResultadoDistancia = new TextField(32);
        campoResultadoDistancia.setEditable(false);

        Panel panelResultados = new Panel(new FlowLayout(FlowLayout.CENTER));
        panelResultados.add(calcular);
        panelResultados.add(new Label("Resultado: "));
        panelResultados.add(campoResultadoDistancia);

        panelPrincipal.add(panelResultados, BorderLayout.SOUTH);

        calcular.addActionListener(e -> {
            try{
                double[] coordenadasParseadas = parsearCoordenadas(
                        campoX1.getText(),
                        campoX2.getText(),
                        campoY1.getText(),
                        campoY2.getText()
                );
                double distancia = calcularDistancia(coordenadasParseadas);
                campoResultadoDistancia.setText(Double.toString(distancia));
            } catch (NumberFormatException ex){
                campoResultadoDistancia.setText("Error en los valores proporcionados");
            }
        });
        return panelPrincipal;
    }

    private static double[] parsearCoordenadas(String x1, String x2, String y1, String y2){
        double dx1 = Double.parseDouble(x1);
        double dx2 = Double.parseDouble(x2);
        double dy1 = Double.parseDouble(y1);
        double dy2 = Double.parseDouble(y2);
        return new double[]{dx1, dx2, dy1, dy2};
    }

    private static double calcularDistancia(double[] coordenadas){
        double fx = coordenadas[0] - coordenadas[1];
        double fy = coordenadas[2] - coordenadas[3];

        fx = Math.pow(fx, 2);
        fy = Math.pow(fy, 2);

        double resultado = fy + fx;
        resultado = Math.sqrt(resultado);

        return resultado;
    }
}