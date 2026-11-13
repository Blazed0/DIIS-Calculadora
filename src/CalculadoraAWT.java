import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CalculadoraAWT extends Frame implements ActionListener {

    // Identificadores usados por CardLayout
    private static final String TARJETA_BASICA = "BASICA";
    private static final String TARJETA_DISTANCIA = "DISTANCIA";
    private static final String TARJETA_BISECCION = "BISECCION";

    // Componentes de la calculadora básica
    private TextField pantalla;
    private Button[] botones;

    private final String[] etiquetas = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
    };

    // Panel que contiene las tarjetas
    private final CardLayout cardLayout = new CardLayout();
    private final Panel panelContenido = new Panel(cardLayout);


    // Constructor
    public CalculadoraAWT() {
        configurarVentana();
        Distancia pitagoras = new Distancia();
        Polinomios biseccion = new Polinomios();

        Panel panelBasica = crearPanelBasico();
        Panel panelDistancia = pitagoras.crearPanelDistancia();
        Panel panelBiseccion = biseccion.crearPanelBiseccion();

        agregarTarjetas(panelBasica, panelDistancia, panelBiseccion);

        Panel panelNavegacion = crearPanelNavegacion();

        add(panelNavegacion, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);

        configurarCierre();

        // Se muestra la ventana cuando ya tiene su contenido.
        setVisible(true);
    }


    // Configura las propiedades generales de la ventana
    private void configurarVentana() {
        setTitle("Calculadora AWT");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
    }


    // Construye la tarjeta de la calculadora básica
    private Panel crearPanelBasico() {
        Panel panelBasico = new Panel(new BorderLayout());

        pantalla = new TextField();
        pantalla.setEditable(false);

        Panel panelTitulo = new Panel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(Color.LIGHT_GRAY);
        panelTitulo.add(new Label("Calculadora"));

        Panel panelSuperior = new Panel(new BorderLayout());
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(pantalla, BorderLayout.CENTER);

        Panel panelBotones = new Panel(new GridLayout(4, 4));
        botones = new Button[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            botones[i] = new Button(etiquetas[i]);
            botones[i].addActionListener(this);
            panelBotones.add(botones[i]);
        }

        panelBasico.add(panelSuperior, BorderLayout.NORTH);
        panelBasico.add(panelBotones, BorderLayout.CENTER);

        return panelBasico;
    }

    // Registra los paneles en CardLayout
    private void agregarTarjetas(
            Panel panelBasica,
            Panel panelDistancia,
            Panel panelBiseccion
    ) {
        panelContenido.add(panelBasica, TARJETA_BASICA);
        panelContenido.add(panelDistancia, TARJETA_DISTANCIA);
        panelContenido.add(panelBiseccion, TARJETA_BISECCION);

        cardLayout.show(panelContenido, TARJETA_BASICA);
    }


    // Crea los botones que permiten cambiar de tarjeta
    private Panel crearPanelNavegacion() {
        Panel panelNavegacion = new Panel(new FlowLayout(FlowLayout.CENTER));

        Button botonBasica = new Button("Básica");
        Button botonDistancia = new Button("Distancia");
        Button botonBiseccion = new Button("Bisección");

        botonBasica.addActionListener(
                e -> cardLayout.show(panelContenido, TARJETA_BASICA)
        );

        botonDistancia.addActionListener(
                e -> cardLayout.show(panelContenido, TARJETA_DISTANCIA)
        );

        botonBiseccion.addActionListener(
                e -> cardLayout.show(panelContenido, TARJETA_BISECCION)
        );

        panelNavegacion.add(botonBasica);
        panelNavegacion.add(botonDistancia);
        panelNavegacion.add(botonBiseccion);

        return panelNavegacion;
    }


    // Maneja el cierre de la ventana
    private void configurarCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    // Evalúa la expresión escrita en la pantalla
    private double evaluarExpresion(String expresion) {
        // Por ahora solo convierte un texto numérico a double.
        // No evalúa expresiones como "1+2".
        return Double.parseDouble(expresion);
    }


    // Atiende los botones numéricos y operadores
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("=")) {
            String expresion = pantalla.getText();

            try {
                double resultado = evaluarExpresion(expresion);
                pantalla.setText(Double.toString(resultado));
            } catch (ArithmeticException ex) {
                pantalla.setText("Error");
            }
        } else {
            pantalla.setText(pantalla.getText() + comando);
        }
    }


    // Punto de entrada del programa
    public static void main(String[] args) {
        new CalculadoraAWT();
    }
}