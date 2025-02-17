package practica4;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClienteFTP {
    public static final String SERVIDOR_FTP = "127.0.0.1";
    private final FTPClient clienteFTP = new FTPClient();

    /**
     * Conecta al servidor FTP e intenta iniciar sesión con el usuario y contraseña proporcionado.
     *
     * @param user     Nombre de usuario para la autenticación.
     * @param password Contraseña del usuario.
     * @return true si la conexión y autenticación son correctas, false en caso contrario.
     */
    public boolean login(String user, String password) {
        try {
            //Conectar al servidor FTP
            clienteFTP.connect(SERVIDOR_FTP);

            //Comprobar login correcto:
            boolean login = clienteFTP.login(user, password);
            if (!login) {
                System.out.println("Usuario/contraseña incorrectos, inténtalo de nuevo.");
            } else {
                System.out.println("Conectado al servidor FTP como " + user + ".");
            }
            return login;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Listar los archivos disponibles en el servidor FTP.
     */
    public void listarFicheros() {
        try {
            System.out.println("Archivos en el servidor FTP:");
            //Guardar archivos en una lista para poder mostrarlos:
            String[] archivos = clienteFTP.listNames();
            if (archivos != null) {
                for (String archivo : archivos) {
                    System.out.println(archivo);
                }
            } else {
                System.out.println("No se encontraron archivos.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Descarga un archivo del servidor FTP y lo guarda en la ruta local especificada.
     *
     * @param remoteFile Nombre del archivo en el servidor FTP.
     * @param localFile  Ruta donde se guardará el archivo descargado.
     */
    public void descargarFichero(String remoteFile, String localFile) {
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            //Comprobar si el fichero se ha descargado correctamente
            boolean descargado = clienteFTP.retrieveFile(remoteFile, fos);
            if (descargado) {
                System.out.println("Archivo descargado: " + localFile);
            } else {
                System.out.println("Error al descargar el archivo.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sube un archivo al servidor FTP.
     *
     * @param localFilePath Ruta local del archivo que se desea subir.
     */
    public void subirFichero(String localFilePath) {
        File file = new File(localFilePath);
        if (!file.exists()) {
            System.out.println("El archivo a subir no existe: " + localFilePath);
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
            boolean subido = clienteFTP.storeFile(file.getName(), fis);
            if (subido) {
                System.out.println("Archivo subido correctamente: " + file.getName());
            } else {
                System.err.println("Error al subir el fichero. RECUERDA, el usuario anónimo no puede subir ficheros, solo puede descargarlos.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cierra la sesión y desconecta del servidor FTP.
     */
    public void desconectar() {
        try {
            clienteFTP.logout();
            clienteFTP.disconnect();
            System.out.println("Desconectado del servidor FTP.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
