import java.util.Random;
import java.util.Scanner;

public class reto01 {

    // Definir variables globales
    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();

    
    static String[] planetasDestino = {"mercurio","venus","marte","jupiter","saturno","urano","neptuno"};
    static String[] descripcionPlanetas = {"el mas pequeño del sistema solar,cercano al sol, denso y rocoso, temperaturas entre -180°c y 430°c","similar en tamaño,masa y composicion a la tierra,con volcanes activos,cubierto por una capa de nuves, temperatura media 463°c","superficie polvorienta, desertica y fria, temperatura de -65°c","el mas grande del sistema solar,no tiene superficie solida, temperatura media de -110°c","el segundo mas grande,planeta gaseoso,compuesto por hidrogeno y helio, temperatura media entre -122°c y -185°c","el mas frio del sistema solar, temperatura -224°c,un gigante de hielo sin superficie solida","el mas lejano desde el sol, compuesto por hielo y gas que le dan color azul, temperatura media -218°c"};
    static double[] distancias = { 77.0, 40.0, 225.0, 778.0, 1426.0, 2870.0, 4496.0}; // Distancia en millones de kilometros
    static String[] tipoNave = {"alfa" , "beta", "omega"};
    static double[] velocidadNaves = {10000.0, 12000.0,15000.0};//KM/Hora
    static double[] necesidadCombustible = {1.0, 1.2, 1.5}; // litros combustible por KM recorrido.
    static double[] necesidadOxigeno = {0.5, 1.0, 1.7}; // litros de oxigeno por hora.


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
        System.out.println(BRIGHT_BLUE + UNDERLINE + BOLD+"\n\t=== SIMULADOR DE VAIJES INTERPLANETARIO ===\n" + RESET);
        System.out.println(BLUE+BOLD + "1)." + RESET + " Planetas destino");
        System.out.println(BLUE+BOLD + "2)." + RESET + " Naves disponible");
        System.out.println(BLUE+BOLD + "3)." + RESET + " Calcular recursos");
        System.out.println(BLUE+BOLD + "4)." + RESET + " Iniciar Viaje");
        System.out.println(BLUE+BOLD + "5)." + RESET + " Salir");
        System.out.print(BOLD + "Selecciones una opción: " + RESET);
    }

    // Selección de planeta
    public static void seleccionarPlaneta () {
        System.out.println(BOLD+BRIGHT_BLUE+UNDERLINE+"\n\tMenú Planetas y sus distancias desde la Tierra "+RESET+"\n");
        for (int i = 0; i < planetasDestino.length; i++) {
            System.out.println(BOLD+BRIGHT_BLUE+(i + 1) + "). " +RESET+ planetasDestino[i] +BLUE+BOLD+ " - Distancia: " + distancias[i] + " millones de km."+RESET);
        }
        System.out.print(BOLD+"Elige el número de tu planeta destino: "+RESET);
        var seleccion = sc.nextInt();

        if (seleccion >= 1 && seleccion <= planetasDestino.length) {
            selectedPlanetIndex = seleccion - 1;
            System.out.println(BRIGHT_GREEN+"\nHas seleccionado " + planetasDestino[selectedPlanetIndex] + "."+RESET);
            isPlanetSelected = true;
        } else {
            System.err.println(ORANGE+"Selección no válida. Por favor, intenta de nuevo."+RESET);
        }

    }

    // Selección de nave espacial
    public static void seleccionarNaveEspacial() {
        var salida = false;

        if (!isPlanetSelected) {
            System.err.println(ORANGE + "Primero debes seleccionar un planeta destino." + RESET);
            return;
        }else{
            do{
            System.out.println("\n--- Seleccione una nave espacial de la lista ---");
            for(int i = 0; i < tipoNave.length; i++){
                System.out.printf("Nave (%d): %s%nVelocidad: %,.2f Km/h%n%n",i+1,tipoNave[i],velocidadNaves[i]);
            }
            System.out.print("Elige el número de nave que necesitas: ");
            var seleccion = sc.nextInt();

            if(seleccion > 0 && seleccion <=3){
                selectedShipIndex = seleccion -1;
                System.out.printf("\nHas seleccionado la nave: %s%nvelocidad: %,.2f Km/h%n%n", tipoNave[selectedShipIndex], velocidadNaves[selectedShipIndex]);
                
                System.out.print("Presiona (1) para confirmar (2) para seleccionar otra nave: ");
                var confirmar = sc.nextInt();

                switch(confirmar){
                    case 1:
                        salida = true;
                        isShipSelected = true;
                        break;
                    case 2:
                        continue;
                    default:
                        System.out.println("Digitaste una opción inválida");    
                }
            }else{
                System.out.println("Digitaste una opción inválida vuelve a intentar");
            }
            }while(!salida);
        }
    }

    public static void calcularRecursos() {

    }

    public static void iniciarViaje() {

    }

}