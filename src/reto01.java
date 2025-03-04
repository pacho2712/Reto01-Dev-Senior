import java.util.Random;
import java.util.Scanner;

public class reto01 {

    // Definir variables globales que permitan la creación de métodos y su funcionalidad.
    static Scanner sc = new Scanner(System.in);
    static String[] planetasDestino = { "mercurio", "venus", "marte", "jupiter", "saturno", "urano", "neptuno" };
    static String[] descripcionPlanetas = {
            "el mas pequeño del sistema solar,cercano al sol, denso y rocoso, temperaturas entre -180°c y 430°c",
            "similar en tamaño,masa y composicion a la tierra,con volcanes activos,cubierto por una capa de nuves, temperatura media 463°c",
            "superficie polvorienta, desertica y fria, temperatura de -65°c",
            "el mas grande del sistema solar,no tiene superficie solida, temperatura media de -110°c",
            "el segundo mas grande,planeta gaseoso,compuesto por hidrogeno y helio, temperatura media entre -122°c y -185°c",
            "el mas frio del sistema solar, temperatura -224°c,un gigante de hielo sin superficie solida",
            "el mas lejano desde el sol, compuesto por hielo y gas que le dan color azul, temperatura media -218°c" };
    static long[] distancias = { 77000000L, 40000000L, 225000000L, 778000000L, 1426000000L, 2870000000L, 4496000000L }; // Distancia en millones de
                                                                                       // kilometros
    static String[] tipoNave = { "alfa", "beta", "omega" };
    static double[] velocidadNaves = { 10000.0, 12000.0, 15000.0 };// KM/Hora
    static double[] necesidadCombustible = { 1.0, 1.2, 1.5 }; // litros combustible por KM recorrido.
    static double[] necesidadOxigeno = { 0.5, 1.0, 1.7 }; // litros de oxigeno por hora.
    static double[] consumoOxigeno = new double[2]; // litros de oxigeno consumido y restante
    static double[] consumoCombustible = new double[2]; // litros de oxigeno consumido y restante

    // Definir constantes para códigos de color buscando dar formato en la ejecución del programa y aspectos variables según se vayan cumpliendo ciertas condiciones en la interacción del usuario.
    static final String GREEN = "\033[32m"; // Verde para barra de progreso
    static final String YELLOW = "\033[33m"; // Amarillo para barra de progreso
    static final String BLUE = "\033[34m"; // Azul para subtitulos
    static final String ORANGE = "\033[38;5;208m"; // Naranja en las recomendaciones
    static final String BOLD = "\033[1m"; // Negrita en subtitulos
    static final String RESET = "\033[0m"; // Resetea el color
    static final String BRIGHT_BLUE = "\033[94m"; // Azul brillante Titulos
    static final String BRIGHT_GREEN = "\033[92m"; // Verde brillante Soluciones
    static final String UNDERLINE = "\033[4m"; // Subrayado en titulos
    static final String LIGHT_GRAY = "\033[90m"; // Gris claro real (ANSI extendido)

    // Verificación de selección de nave, planeta, calculo de recursos y cambios en la selección de los métodos seleccionarNaveEspacial() y seleccionarPlaneta()
    static boolean isPlanetSelected = false; // Verifica si se ha seleccionado un planeta
    static boolean isShipSelected = false; // Verifica si se ha seleccionado una nave
    static boolean isCalculatedResources = false; // Verifica si se calcularon los recursos
    static int selectedShipIndex = -1; // Indice de nave seleccionada
    static int selectedPlanetIndex = -1; // Indice de planeta seleccionado
    static boolean planetSelectedChange = false; // Verifica si el planeta de destino cambió
    static boolean shipSelectedChange = false; // Verifica si cambiaron la nave inicialmente seleccionada para el viaje

    //Método principal main donde se realiza la ejecución del programa
    public static void main(String[] args) {
        int opcion;

        do {
            showMenu();
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    seleccionarPlaneta();
                    break;
                case 2:
                    seleccionarNaveEspacial();
                    break;
                case 3:
                    calcularRecursos();
                    break;
                case 4:
                    iniciarViaje();
                    break;
                case 5:
                    System.out.println("Saliendo del programa.......");
                    break;
                default:
                    System.out.println("Opción no validad  !!!!!");
                    break;
            }
        } while (opcion != 5);
        sc.close();
    }

    //Método que muestra el menú principal del programa
    public static void showMenu() {
        System.out.println(BRIGHT_BLUE + UNDERLINE + BOLD + "\n\t=== SIMULADOR DE VAIJES INTERPLANETARIO ===\n" + RESET);
        if(isPlanetSelected){
            System.out.println(BLUE + BOLD + "1)." + RESET + UNDERLINE + " Planetas destino " + RESET);
        }else{
            System.out.println(BLUE + "1)." + RESET + LIGHT_GRAY + " Planetas destino");
        }
        if(isShipSelected){
            System.out.println(BLUE + BOLD + "2)." + RESET + UNDERLINE + " Oferta Naves disponibles " + RESET);
        }else{
            System.out.println(BLUE + "2)." + RESET + LIGHT_GRAY + " Oferta Naves disponibles");
        }
        if(isCalculatedResources && !planetSelectedChange && !shipSelectedChange){
            System.out.println(BLUE + BOLD + "3)." + RESET + UNDERLINE + " Calculo de recursos " + RESET);
        }else{
            System.out.println(BLUE + "3)." + RESET + LIGHT_GRAY + " Calculo de recursos");
        }
        if(isPlanetSelected && isShipSelected && isCalculatedResources && !planetSelectedChange && !shipSelectedChange){
            System.out.println(BLUE + BOLD + "4)." + RESET + " Inicio del Viaje " + RESET);
        }else{
            System.out.println(BLUE + "4)." + RESET + LIGHT_GRAY + " Inicio del Viaje");
        }
        System.out.println(BLUE + BOLD + "5)." + RESET + " Salir");
        System.out.print(BOLD + "Selecciones una opción: " + RESET);
    }

    // Selección de planeta: método que permite seleccionar un planeta de destino desde un array declarado en las variables globales.
    public static void seleccionarPlaneta() {
        if(isPlanetSelected){
            planetSelectedChange = true;
        }
        System.out.println(
                BOLD + BRIGHT_BLUE + UNDERLINE + "\n\tMenú Planetas y sus distancias desde la Tierra " + RESET + "\n");
        for (int i = 0; i < planetasDestino.length; i++) {
            System.out.println(BOLD + BRIGHT_BLUE + (i + 1) + "). " + RESET + planetasDestino[i] + BLUE + BOLD
                    + " - Distancia: " + distancias[i] + " millones de km." + RESET);
        }
        System.out.print(BOLD + "Elige el número de tu planeta destino: " + RESET);
        var seleccion = sc.nextInt();

        if (seleccion >= 1 && seleccion <= planetasDestino.length) {
            selectedPlanetIndex = seleccion - 1;
            System.out
                    .println(BRIGHT_GREEN + "\nHas seleccionado " + planetasDestino[selectedPlanetIndex] + "." + RESET);
            isPlanetSelected = true;
        } else {
            System.err.println(ORANGE + "Selección no válida. Por favor, intenta de nuevo." + RESET);
        }
    }

    // Selección de nave espacial: método que permite seleccionar una nave disponible desde un array declarado en las variables globales.
    public static void seleccionarNaveEspacial() {
        var salida = false;

        if(isShipSelected){
            shipSelectedChange = true;
        }

        if (!isPlanetSelected) {
            System.err.println(ORANGE + "Primero debes seleccionar un planeta destino." + RESET);
            return;
        } else {
            do {
                System.out.println("\n--- Seleccione una nave espacial de la lista ---");
                for (int i = 0; i < tipoNave.length; i++) {
                    System.out.printf("Nave (%d): %s%nVelocidad: %,.2f Km/h%n%n", i + 1, tipoNave[i],
                            velocidadNaves[i]);
                }
                System.out.print("Elige el número de nave que necesitas: ");
                var seleccion = sc.nextInt();

                if (seleccion > 0 && seleccion <= 3) {
                    selectedShipIndex = seleccion -1;
                    System.out.printf("\nHas seleccionado la nave: %s%nvelocidad: %,.2f Km/h%n%n",
                            tipoNave[selectedShipIndex], velocidadNaves[selectedShipIndex]);

                    System.out.print("Presiona (1) para confirmar (2) para seleccionar otra nave: ");
                    var confirmar = sc.nextInt();

                    switch (confirmar) {
                        case 1:
                            salida = true;
                            isShipSelected = true;
                            break;
                        case 2:
                            continue;
                        default:
                            System.out.println("Digitaste una opción inválida");
                    }
                } else {
                    System.out.println("Digitaste una opción inválida vuelve a intentar");
                }
            } while (!salida);
        }
    }

    //Metodo que implementa otros dos métodos para calcular los recursos necesario haciendo operaciones mátemáticas de acuerdo a la distancia y requerimientos de cómbustible y oxígeno.
    public static void calcularRecursos() {
        boolean salir = false;

        do{
            if(!isShipSelected){
                System.err.println("Aún no ha seleccionado una nave, debe hacerlo antes de calcular los recursos para el viaje");
                return;
            }else{
                System.out.printf("\nPlaneta de destino selecionado: (%s)%n",planetasDestino[selectedPlanetIndex]);
                System.out.printf("Nave selecionada: (%s)%n",tipoNave[selectedShipIndex]);
                System.out.printf("Distancia desde el planeta tierra: (%,d) millones de años%n",distancias[selectedPlanetIndex]);                
                System.out.printf("Velocidad de la nave: (%,.0f) km/h%n",velocidadNaves[selectedShipIndex]);
                System.out.print("Espere un momento estamos calculando los recursos");
                
                for(int i = 0; i<10; i++){
                    System.out.print(".");
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("%nEl oxígeno requerido para el viaje es: (%,.0f) litros%n",calculoOxigeno(distancias, velocidadNaves, necesidadOxigeno, selectedShipIndex));
                System.out.printf("El combustible requerido para el viaje es: (%,d) litros%n",calculoCombustible(distancias, necesidadCombustible, selectedShipIndex));
                isCalculatedResources = true;
                planetSelectedChange = false;
                shipSelectedChange = false;
                salir = true;
            }
        }while(!salir);
    }

    // Calcular la cantidad de oxigeno requerido durante el viaje de acuerdo a la nave seleccionada.
    public static double calculoOxigeno(long[] distancias, double[] velocidadNaves, double necesidadOxigeno[], int selectedShipIndex){
        long distancia = distancias[selectedShipIndex];
        double velocidad = velocidadNaves[selectedShipIndex];
        double oxigeno = necesidadOxigeno[selectedShipIndex];

        return (distancia/velocidad) / oxigeno;
    }

    // Calcular la cantidad de combustible requerido de acuerdo a la distancia del planeta seleccionado.
    public static long calculoCombustible(long[] distancias, double necesidadCombustible[], int selectedShipIndex){
        long distancia = distancias[selectedShipIndex];
        double combustible = necesidadCombustible[selectedShipIndex];

        return (long) (distancia*combustible);
    }

    //Permite el inicio del viaje una vez se ha cumplido la selección de nave, planeta y calculo de recursos, verifica si posterior al calculo de recursos se cambió el destino o la nave a utilizar para recalcular recursos, utilizando estructuras de control.
    public static void iniciarViaje() {
        if(planetSelectedChange){
            System.err.println("El planeta de destino fue cambiado se deben recalcular los recursos");
            return;
        }
        
        if(shipSelectedChange){
            System.err.println("Detectamos un cambio de nave se deben recalcular los recursos para el viaje");
            return;
        }

        if (!isPlanetSelected) {
            System.err.println(ORANGE + "Primero debes seleccionar un planeta destino." + RESET);
            return;
        }
        
        if (!isShipSelected) {
            System.err.println(ORANGE + "Primero debes seleccionar una de las naves espaciales." + RESET);
            return;
        }

        if (!isCalculatedResources) {
            System.err.println(ORANGE + "Primero debes calcular los recursos para el viaje." + RESET);
            return;
        }
 System.out.println(BRIGHT_GREEN + "Iniciando el viaje con destino a " + planetasDestino[selectedPlanetIndex] + "..." + RESET);
        int progresoViaje = 0;
        // Simular el progreso del viaje
        for (int i = 0; i <= 100; i += 10) {
            try {
                Thread.sleep(500); // Pausa de medio segundo para recrear el progreso
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i < 50) {
                for(int j = 0; j < progresoViaje; j++){
                    System.out.print(GREEN + "=" + RESET);
                }
            } else if (i < 80) {
                for(int j = 0; j < progresoViaje; j++){
                    System.out.print(YELLOW + "=" + RESET);
                }
            } else {
                for(int j = 0; j < progresoViaje; j++){
                    System.out.print(ORANGE + "=" + RESET);
                }
            }
            
            if(i>=10){
                progresoViaje = (int) (i*0.1);
                consumoOxigeno(progresoViaje, consumoOxigeno);
                consumoCombustible(progresoViaje);
                System.out.printf(" Recorrido: %s%%%nConsumo de oxigeno: %,.0f litros - Litros restantes: %,.0f%nConsumo de combustible: %,.0f litros - Litros restantes: %,.0f litros%n",i,consumoOxigeno[0], consumoOxigeno[1],consumoCombustible[0],consumoCombustible[1]);
            }

            if (Math.random() < 0.2) {
                simularSituacionesDePeligro();//Adopta el método para simular eventos inesperados.
            }
        }
        System.out.println(BRIGHT_GREEN + "\n¡Has llegado a " + planetasDestino[selectedPlanetIndex] + RESET);
        
    }

    //Consumo de oxigeno durante el viaje: mediante una operación matemática arroja la cantidad de oxígeno requerida para el viaje, según la distancia.
    public static void consumoOxigeno(int progresoViaje, double[] consumoOxigeno){
        var totalViaje = 10;
        var oxigeno = calculoOxigeno(distancias, velocidadNaves, necesidadOxigeno, selectedShipIndex);
        var divisionOxigeno = oxigeno/totalViaje;

        double oxigenoConsumido = divisionOxigeno * progresoViaje;
        double oxigenoRestante = oxigeno - oxigenoConsumido;

        consumoOxigeno[0] = oxigenoConsumido;
        consumoOxigeno[1] = oxigenoRestante;
    }

    //Consumo de combustible durante el viaje: calcula mediante una operación matemática el combustible requerido de acuerdo a la distancia.
    public static void consumoCombustible(int progresoViaje){
        var totalViaje = 10;
        var combustible = calculoCombustible(distancias, necesidadCombustible, selectedShipIndex);
        var divisionCombustible = combustible/totalViaje;

        double combustibleConsumido = divisionCombustible * progresoViaje;
        double combustibleRestante = combustible - combustibleConsumido;

        consumoCombustible[0] = combustibleConsumido;
        consumoCombustible[1] = combustibleRestante;
    }

    //Método simular situaciones de peligro: este método permite simular por medio de un Random algúnas situaciones de peligro aleatorias con una probabilidad del 20% de ocurrencia durante el viaje.
    public static void simularSituacionesDePeligro() {
        
        if (!isPlanetSelected) {
            System.err.println(ORANGE + "Selecciona un planeta de destino para continuar con el viaje." + RESET);
            return;
        }
        
        if (!isShipSelected) {
            System.err.println(ORANGE + "selecciona una de las naves espaciales." + RESET);
            return;
        }
        
        if (!isCalculatedResources) {
            System.err.println(ORANGE + "se debe hacer el calculo de recursos del viaje antes de continuar." + RESET);
            return;
        }
        
        System.out.println(BRIGHT_GREEN + "Simulando situaciones de peligro en el viaje interplanetario..." + RESET);
        
        // Simular las situaciones de peligro que se podrian presentar//

        String[] situacionesPeligro = { "Basura espacial detectada: la nave fue golpeada afectacion leve.", "Se ha detectado una tormenta geomagnetica: debemos cambiar el rumbo para evitarla, realizando calculos","Se ha detectado un asteroide cerca: reduciendo velocidad para evitar colision." };  

        int situacionesPeligroIndex = new Random().nextInt(situacionesPeligro.length);
        System.out.println(ORANGE + situacionesPeligro[situacionesPeligroIndex] + RESET);

        switch (situacionesPeligroIndex) {
            case 0:
                System.out.println(ORANGE + "Reinicie el sistema de comunicacion para conectar con la antena auxiliar." + RESET);
                break;
            case 1:
                System.out.println(ORANGE + "Recalculando el rumbo para evitar tormenta geomagnetica; enaproximadamente 3 horas se recobra la linea de desplazamiento." + RESET);
                break;
            case 2:
                System.out.println(ORANGE + "Reduciendo la velocidad para evitar la colision, una vez superado el asteroide aumento de velocidad en 90% durante 6 horas luego velocidad al 70% proyectado." + RESET);
                break;
            default:
                System.out.println(ORANGE + "Evento no identificado, aeronave sin afectacion." + RESET);
                break;
        }
    }
}