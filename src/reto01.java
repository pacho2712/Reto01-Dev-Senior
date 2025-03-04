import java.util.Random;
import java.util.Scanner;

public class reto01 {

    // Definir variables globales
    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();

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

    // Definir constantes para códigos de color
    static final String GREEN = "\033[32m"; // Verde para barra de progreso
    static final String YELLOW = "\033[33m"; // Amarillo para barra de progreso
    static final String BLUE = "\033[34m"; // Azul para subtitulos
    static final String ORANGE = "\033[38;5;208m"; // Naranja en las recomendaciones
    static final String BOLD = "\033[1m"; // Negrita en subtitulos
    static final String RESET = "\033[0m"; // Resetea el color
    static final String BRIGHT_BLUE = "\033[94m"; // Azul brillante Titulos
    static final String BRIGHT_GREEN = "\033[92m"; // Verde brillante Soluciones
    static final String UNDERLINE = "\033[4m"; // Subrayado en titulos

    // Verificación de selección de nave y planeta
    static boolean isPlanetSelected = false; // Verifica si se ha seleccionado un planeta
    static boolean isShipSelected = false; // Verifica si se ha seleccionado una nave
    static boolean isCalculatedResources = false; // Verifica si se calcularon los recursos
    static int selectedShipIndex = -1; // Indice de nave seleccionada
    static int selectedPlanetIndex = -1; // Indice de planeta seleccionado

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

    public static void showMenu() {
        System.out
                .println(BRIGHT_BLUE + UNDERLINE + BOLD + "\n\t=== SIMULADOR DE VAIJES INTERPLANETARIO ===\n" + RESET);
        System.out.println(BLUE + BOLD + "1)." + RESET + " Planetas destino");
        System.out.println(BLUE + BOLD + "2)." + RESET + " Oferta Naves disponibles");
        System.out.println(BLUE + BOLD + "3)." + RESET + " Calculo de recursos");
        System.out.println(BLUE + BOLD + "4)." + RESET + " Inicio del Viaje");
        System.out.println(BLUE + BOLD + "5)." + RESET + " Salir");
        System.out.print(BOLD + "Selecciones una opción: " + RESET);
    }

    // Selección de planeta
    public static void seleccionarPlaneta() {
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

    // Selección de nave espacial
    public static void seleccionarNaveEspacial() {
        var salida = false;

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

    public static void calcularRecursos() {
        boolean salir = false;

        do{
            if(!isShipSelected){
                System.out.println("Aún no ha seleccionado una nave, debe hacerlo antes de calcular los recursos para el viaje");
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
                salir = true;
            }
        }while(!salir);
    }

    // Calcular la cantidad de oxigeno requerido durante el viaje de acuerdo a la nave seleccionada
    public static double calculoOxigeno(long[] distancias, double[] velocidadNaves, double necesidadOxigeno[], int selectedShipIndex){
        long distancia = distancias[selectedShipIndex];
        double velocidad = velocidadNaves[selectedShipIndex];
        double oxigeno = necesidadOxigeno[selectedShipIndex];

        return (distancia/velocidad) / oxigeno;
    }

    // Calcular la cantidad de combustible requerido
    public static long calculoCombustible(long[] distancias, double necesidadCombustible[], int selectedShipIndex){
        long distancia = distancias[selectedShipIndex];
        double combustible = necesidadCombustible[selectedShipIndex];

        return (long) (distancia*combustible);
    }

    public static void iniciarViaje() {

        if (!isPlanetSelected) {
            System.err.println(ORANGE + "Primero debes seleccionar un planeta destino." + RESET);
            return;
        }
        
        if (!isShipSelected) {
            System.err.println(ORANGE + "Primero debes seleccionar una de las naves espaciales." + RESET);
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
        }
        System.out.println(BRIGHT_GREEN + "\n¡Has llegado a " + planetasDestino[selectedPlanetIndex] + RESET);
        
    }

    //Consumo de oxigeno durante el viaje
    public static void consumoOxigeno(int progresoViaje, double[] consumoOxigeno){
        var totalViaje = 10;
        var oxigeno = calculoOxigeno(distancias, velocidadNaves, necesidadOxigeno, selectedShipIndex);
        var divisionOxigeno = oxigeno/totalViaje;

        double oxigenoConsumido = divisionOxigeno * progresoViaje;
        double oxigenoRestante = oxigeno - oxigenoConsumido;

        consumoOxigeno[0] = oxigenoConsumido;
        consumoOxigeno[1] = oxigenoRestante;
    }

    //Consumo de combustible durante el viaje
    public static void consumoCombustible(int progresoViaje){
        var totalViaje = 10;
        var combustible = calculoCombustible(distancias, necesidadCombustible, selectedShipIndex);
        var divisionCombustible = combustible/totalViaje;

        double combustibleConsumido = divisionCombustible * progresoViaje;
        double combustibleRestante = combustible - combustibleConsumido;

        consumoCombustible[0] = combustibleConsumido;
        consumoCombustible[1] = combustibleRestante;
    }
    

    }


