package practica4;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        ClienteFTP clienteFTP = new ClienteFTP();

        System.out.println("Bienvenido al servidor TCP de Raúl Navarro!");
        // Conectarse al servidor con usuario y contraseña:
        boolean conectado = false;
        while (!conectado) {
            System.out.print("Ingresa tu usuario o accede como anónimo (usuario: anonimo) > ");
            String usuario = scanner.nextLine();
            String pass = "";
            //Si el usuario es anónimo no require contraseña:
            if (!usuario.equals("anonimo")){
                System.out.print("Contraseña > ");
                pass = scanner.nextLine();
            }
            //Pedir el usuario y la contraseña hasta introducir uno válido:
            conectado = clienteFTP.login(usuario, pass);
        }

        boolean salir = false;
        //Usar el programa hasta pulsar la opción 4:
        while (!salir) {
            switch (elegirMenu()) {
                case 1:
                    //Listar ficheros:
                    clienteFTP.listarFicheros();
                    break;
                case 2:
                    //Mostrar primero los ficheros para poder saber cuál descargar:
                    System.out.println("Lista de archivos disponibles:");
                    clienteFTP.listarFicheros();
                    System.out.print("Ingrese el nombre del archivo a descargar > ");
                    String remoteFile = scanner.nextLine();
                    System.out.print("Ingrese la ruta donde quieres descargar el archivo y el nombre final  > ");
                    String localFile = scanner.nextLine();
                    clienteFTP.descargarFichero(remoteFile, localFile);
                    break;
                case 3:
                    //Subir fichero local al servidor;
                    System.out.print("Ingrese la ruta del archivo a subir > ");
                    String fileToUpload = scanner.nextLine();
                    clienteFTP.subirFichero(fileToUpload);
                    break;
                case 4:
                    //Desconectar del servidor y terminar el programa:
                    clienteFTP.desconectar();
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    salir = true;
                    break;
                default:
                    //Pedir una opción del menu válida:
                    System.out.println("Opción no válida. Inténtalo nuevamente.");
            }
        }
    }

    /**
     * Mostrar el menu de opciones.
     *
     * @return Número de la acción seleccionada.
     */
    private static int elegirMenu() {
        System.out.println("\nMenú de opciones:");
        System.out.println("1. Listar archivos");
        System.out.println("2. Descargar archivo");
        System.out.println("3. Subir archivo");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción > ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner después de nextInt()
        return opcion;
    }
}