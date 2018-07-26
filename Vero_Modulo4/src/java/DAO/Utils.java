package DAO;

import Connection.DB_Manager;
import Model.Bitacora_tarjeta;
import Model.Tarjeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Utils {

    DB_Manager db_manager;
    Connection connection;

    public Utils() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    /*
    * VERION 3.1 TARJETAS
     */
    public String InputStreamtoString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString().replaceAll("\n", "#");
    }

    public ArrayList<String> getTarjetas(String txt) {
        ArrayList<String> list = new ArrayList<>();
        String line = "";
        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            line = line + c;

            if (c == '#') {
                String tarjeta = line.replaceAll(" ", "&").replaceAll("#", "");
                list.add(tarjeta.replaceAll("[\n\r]", ""));
                line = "";
            }
        }
        return list;
    }

    public String[] getTarjetasValues(String ticket) {
        return ticket.split("&");
    }

    public ArrayList<Bitacora_tarjeta> getBitacoraTarjeta(ArrayList<String> tarjetas) {
        ArrayList<Bitacora_tarjeta> listBitacoras = new ArrayList<>();
        int $index = 1;

        for (String tarjeta : tarjetas) {

            String[] valuesTarjeta = getTarjetasValues(tarjeta);

            Bitacora_tarjeta bitacora = new Bitacora_tarjeta();
            System.err.println(valuesTarjeta.length);
            if (valuesTarjeta.length == 5) {
                if (validateCosto(valuesTarjeta[0]) && validateNoTarjeta(valuesTarjeta[1]) && validateEstado(valuesTarjeta[2]) && validatePuntos(valuesTarjeta[3]) && validateVigencia(valuesTarjeta[4])) {
                    if (!validateDuplicate(valuesTarjeta[1])) {
                        bitacora.setStatus(true);
                        Tarjeta t = new Tarjeta();
                        t.setCosto(Integer.parseInt(valuesTarjeta[0]));
                        t.setNoTarjeta(valuesTarjeta[1]);
                        t.setStatus(valuesTarjeta[2]);
                        t.setPuntos(Integer.parseInt(valuesTarjeta[3]));
                        t.setVigencia(valuesTarjeta[4]);
                        bitacora.setTarjeta(t);
                        bitacora.setMensaje("Tarjeta " + $index + " agregada con exito.");
                    } else {
                        bitacora.setMensaje("Tarjeta " + $index + " ya fue registrada");
                    }
                } else {
                    bitacora.setStatus(false);
                    bitacora.setTarjeta(null);
                    bitacora.setMensaje("Tarjeta " + $index + " error en algun atributo, verificar.");
                }

            } else {
                bitacora.setStatus(false);
                bitacora.setMensaje("Tarjeta " + $index + " faltan atributos, verificar");
            }
            listBitacoras.add(bitacora);

            $index++;
        }

        return listBitacoras;
    }

    public boolean validateDuplicate(String noTarjeta) {
        try {
            String SQL = "Select * from tarjeta where noTarjeta = '" + noTarjeta + "'";

            PreparedStatement id = connection.prepareCall(SQL);
            ResultSet cdr = id.executeQuery();

            if (cdr.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateCosto(String costo) {
        for (int i = 0; i < costo.length(); i++) {
            char cr = costo.charAt(i);
            if (!Character.isDigit(cr)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateNoTarjeta(String notarjeta) {
        if (notarjeta.length() != 16) {
            return false;
        }

        for (int i = 0; i < notarjeta.length(); i++) {
            char cr = notarjeta.charAt(i);
            if (!Character.isDigit(cr)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateEstado(String costo) {
        if (costo.equals("DISPONIBLE") || costo.equals("OCUPADO")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validatePuntos(String puntos) {
        for (int i = 0; i < puntos.length(); i++) {
            char cr = puntos.charAt(i);
            if (!Character.isDigit(cr)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateVigencia(String costo) {
        if (costo.length() != 10) {
            return false;
        }

        if (Character.isLetter(costo.charAt(0)) || Character.isLetter(costo.charAt(1)) || Character.isLetter(costo.charAt(2)) || Character.isLetter(costo.charAt(3))
                || costo.charAt(4) != '-' || Character.isLetter(costo.charAt(5)) || Character.isLetter(costo.charAt(6)) || costo.charAt(7) != '-'
                || Character.isLetter(costo.charAt(8)) || Character.isLetter(costo.charAt(9))) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Utils u = new Utils();
        System.out.println(u.validateNoTarjeta("10s0"));
        System.out.println(u.validateNoTarjeta("1010101010101010"));
        System.out.println(u.validateNoTarjeta("10101010101s1010"));
        System.out.println(u.validateNoTarjeta("10101010101010120"));
    }

    public boolean validateEstadoa(String estado) {
        if (estado.equals("ACTIVO") || estado.equals("INACTIVO")) {
            return true;
        }
        System.err.println("ERROR EN EL ESTADO " + estado);
        return false;
    }

    public boolean validateMonto(String monto) {
        for (int i = 0; i < monto.length(); i++) {
            char c = monto.charAt(i);
            if (Character.isLetter(c)) {
                System.err.println("ERROR EN EL MONTO " + monto);
                return false;
            }
            if (c == '.') {
                System.err.println("ERROR EN EL MONTO " + monto);
                return false;
            }
        }
        return true;
    }

}
