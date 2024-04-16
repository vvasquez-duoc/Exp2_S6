package exp2_s6_victor_vasquez;

import java.util.Random;
import java.util.HashMap;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Exp2_S6_Victor_Vasquez {
    static String[] tipoEntradas = new String[3];
    static String[] precioEntradas = new String[3];
    
    static int descuentoEstudiante = 10;
    static int descuentoTerceraEdad = 15;
    static int edadTerceraEdad = 60;
    
    static HashMap<String, String[]> entradasPalco = new HashMap<>();
    static HashMap<String, String[]> entradasPA = new HashMap<>();
    static HashMap<String, String[]> entradasPB = new HashMap<>();
    
    static String[] letrasColumnas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
    static int cantFilas = 2;
    static int cantColumnas = 10;
    static String separador = "-------------------------------------------";
    
    static String asientoSeleccionado = "";
    static boolean asientoNoValido = false;
    static boolean asientoOcupado = false;
    static boolean asientoNoOcupado = false;
    static boolean hayAsientosVendidos = false; 
    
    public static void limpiaPantalla(){
        for(int i=0; i<30; i++){
            System.out.println("");
        }
    }
    
    public static Boolean validaEdad(String edad){
        int edadNum;
        
        if(edad == null || edad.isEmpty()) return false;
        
        try{
            edadNum = Integer.parseInt(edad);
        }catch(NumberFormatException e){
            return false;
        }
        
        return (edadNum >= 5);
    }
    
    public static Boolean validaRut(String rut){
        int rutNum;
        if(rut == null || rut.isEmpty() || rut.length() <= 1) return false;
        
        String R = rut.substring(0, rut.length() - 1);
        String DV = rut.substring(rut.length() - 1, rut.length());
        
        try{
            rutNum = Integer.parseInt(R);
        }catch(NumberFormatException e){
            return false;
        }
        
        return DV.toLowerCase().equals(dv(R));
    }
    
    public static String dv(String rut){
        Integer M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        
        return ( S > 0 ) ? String.valueOf(S-1) : "k";		
    }
    
    public static void seleccionaAsiento(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);

        imprimeMapaAsientos(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        
        System.out.println("(*) ASIENTOS VENDIDOS");
        if(asientoNoValido){
            System.out.println("-- EL ASIENTO "+asientoSeleccionado.toUpperCase()+" NO ES VÁLIDO --");
        }
        if(asientoOcupado){
            System.out.println("-- EL ASIENTO "+asientoSeleccionado.toUpperCase()+" YA ESTÁ VENDIDO --");
        }
        System.out.println("SELECCIONE EL ASIENTO QUE QUIERE COMPRAR");
        System.out.println("POR EJEMPLO: A2");
        
        asientoSeleccionado = teclado.nextLine();
        asientoNoValido = false;
        asientoOcupado = false;
        
        if(asientoSeleccionado.isEmpty() || asientoSeleccionado == null){
            limpiaPantalla();
            seleccionaAsiento(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }
        
        String columnaAux = asientoSeleccionado.substring(0,1);
        String filaAux = asientoSeleccionado.substring(1,2);
        int filaAsiento = Integer.parseInt(filaAux);
        filaAsiento -= 1;
        int columnaAsiento = buscaColumnaDesdeLetra(columnaAux, letrasColumnas);
        
        if(columnaAsiento < 0 || columnaAsiento > cantColumnas) asientoNoValido = true;
        else{
            if(opcion == 1){
                if(asientosPalco[filaAsiento][columnaAsiento] == 1) asientoOcupado = true;
                else asientosPalco[filaAsiento][columnaAsiento] = 1;
            }else if(opcion == 2){
                if(asientosPA[filaAsiento][columnaAsiento] == 1) asientoOcupado = true;
                else asientosPA[filaAsiento][columnaAsiento] = 1;
            }else if(opcion == 3){
                if(asientosPB[filaAsiento][columnaAsiento] == 1) asientoOcupado = true;
                else asientosPB[filaAsiento][columnaAsiento] = 1;
            }
        }
        
        if(asientoNoValido || asientoOcupado){
            limpiaPantalla();
            seleccionaAsiento(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }else{
            limpiaPantalla();
            otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
        }
        
    }
    
    public static void otrosDatosCompra(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB, String asientoSeleccionado){
        Scanner teclado = new Scanner(System.in);
        String edad;
        int edadNumerica = 0;
        int precioEntrada = 15000;
        int descuento = 0;
        String rut;
        String nombre;
        
        if(opcion == 2) precioEntrada = 10000;
        else if(opcion == 3) precioEntrada = 7000;
        edad = "";
        
        System.out.println("UBICACIÓN: "+ zonaTeatro);
        System.out.println("ASIENTO: "+asientoSeleccionado.toUpperCase());
        
        System.out.println("");
        System.out.println("INGRESE SU NOMBRE:");
        nombre = teclado.nextLine();
        if(nombre.isEmpty()){
            limpiaPantalla();
            System.out.println("-- ERROR: EL NOMBRE NO PUEDE ESTAR EN BLANCO --");
            System.out.println("-- INGRESE SUS DATOS NUEVAMENTE --");
            System.out.println("");
            otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
        }
        
        System.out.println("");
        System.out.println("INGRESE SU RUT SIN PUNTOS NI GUION:");
        System.out.println("POR EJEMPLO: 12345678K");
        rut = teclado.nextLine();
        if(!validaRut(rut)){
            limpiaPantalla();
            System.out.println("-- ERROR: EL RUT INGRERSADO NO ES VALIDO --");
            System.out.println("-- INGRESE SUS DATOS NUEVAMENTE --");
            System.out.println("");
            otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
        }
        
        System.out.println("");
        System.out.println("INGRESE SU EDAD:");
        edad = teclado.nextLine();
       
        if(!validaEdad(edad)){
            limpiaPantalla();
            System.out.println("-- ERROR: LA EDAD NO PUEDE SER MENOR A CINCO AÑOS (5) --");
            System.out.println("-- INGRESE SUS DATOS NUEVAMENTE --");
            System.out.println("");
            otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
        }
        
        edadNumerica = Integer.parseInt(edad);
        
        int precioFinal = precioEntrada;
        if(edadNumerica < 24){
            descuento = 10;
            precioFinal = precioEntrada - (precioEntrada * descuento / 100);
        }
        if(edadNumerica > 60){
            descuento = 15;
            precioFinal = precioEntrada - (precioEntrada * descuento / 100);
        }
        
        limpiaPantalla();
        resumenCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado, precioEntrada, descuento, precioFinal, nombre, rut);
    }
    
    public static void resumenCompra(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB, String asientoSeleccionado, int precioEntrada, int descuento, int precioFinal, String nombre, String rut){
        Scanner teclado = new Scanner(System.in);
        String otraCompra;
        String txtDescuento;
        otraCompra = "";
        
        String[] datosEntrada = new String[7];
        
        do{
            System.out.println("-- RESUMEN DE COMPRA --");
            System.out.println("UBICACIÓN: "+ zonaTeatro);
            System.out.println("ASIENTO: "+asientoSeleccionado.toUpperCase());
            System.out.println("PRECIO ENTRADA: "+precioEntrada);
            if(descuento == 0){
                txtDescuento = "SIN DESCUENTO";
            }else txtDescuento = (descuento == 10)? "ESTUDIANTE - " : "TERCERA EDAD - ";
            txtDescuento += (descuento == 0)? "" : descuento+"%";
            System.out.println("DESCUENTO: "+txtDescuento);
            System.out.println("PRECIO FINAL: "+precioFinal);
            System.out.println("NOMBRE COMPRADOR: "+nombre.toUpperCase());
            System.out.println("RUT COMPRADOR: "+rut.toUpperCase());
               
            System.out.println("");
            System.out.println("DESEA COMPRAR UNA NUEVA ENTRADA (S/N)");
            otraCompra = teclado.nextLine();
            if(!("N".equals(otraCompra.toUpperCase()) || "S".equals(otraCompra.toUpperCase()))){
                limpiaPantalla();
                System.out.println("-- OPCIÓN NO VÁLIDA. SÓLO SE PERMITE 'S' O 'N' --");
                System.out.println("");
            }
        }while(!("N".equals(otraCompra.toUpperCase()) || "S".equals(otraCompra.toUpperCase())));
        
        datosEntrada[0] = zonaTeatro;
        datosEntrada[1] = asientoSeleccionado.toUpperCase();
        datosEntrada[2] = String.valueOf(precioEntrada);
        datosEntrada[3] = txtDescuento;
        datosEntrada[4] = String.valueOf(precioFinal);
        datosEntrada[5] = nombre.toUpperCase();
        datosEntrada[6] = rut.toUpperCase();
        
        switch(zonaTeatro){
            case "ZONA A - PALCO":
                entradasPalco.put(asientoSeleccionado.toUpperCase(), datosEntrada);
                break;
            case "ZONA B - TRIBUNA BAJA":
                entradasPB.put(asientoSeleccionado.toUpperCase(), datosEntrada);
                break;
            case "ZONA C - TRIBUNA ALTA":
                entradasPA.put(asientoSeleccionado.toUpperCase(), datosEntrada);
                break;
        }
        
        if("N".equals(otraCompra.toUpperCase())){
            limpiaPantalla();
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }
        
        if("S".equals(otraCompra.toUpperCase())){
            limpiaPantalla();
            tipoAsiento(asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static int buscaColumnaDesdeLetra(String letra, String[] letrasColumnas){
        int pos = -1;
        for(int i=0; i<letrasColumnas.length; i++){
            if(letra.toUpperCase().equals(letrasColumnas[i])){
                pos = i;
                break;
            }
        }
        return pos;
    }
    
    public static void tipoAsiento(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        int opcion;
        String[] itemsMenu = new String[3];
        String zonaTeatro = "";
        
        opcion = 0;
        itemsMenu[0] = "ZONA A - PALCO";
        itemsMenu[1] = "ZONA B - TRIBUNA BAJA";
        itemsMenu[2] = "ZONA C - TRIBUNA ALTA";
        
        try{
            do{
                System.out.println("-- SELECCIONE EL TIPO DE ENTRADA --");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCIÓN:");
                for(int i=0; i<itemsMenu.length; i++){
                    String txtOpcion = (i + 1) + ".- " + itemsMenu[i];
                    if(i == 0) txtOpcion += " ($15.000)";
                    if(i == 1) txtOpcion += " ($10.000)";
                    if(i == 2) txtOpcion += " ($7.000)";
                    System.out.println(txtOpcion);
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.length){
                    limpiaPantalla();
                    System.out.println("-- LA OPCIÓN ("+opcion+") NO ES VÁLIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.length);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCIÓN INGRESADA NO ES UN NÚMERO");
            System.out.println("");
            tipoAsiento(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion >=1 && opcion <= itemsMenu.length){
            zonaTeatro = itemsMenu[(opcion - 1)];
            limpiaPantalla();
            seleccionaAsiento(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void menuPrincipal(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        int opcion;
        String[] itemsMenu = new String[4];
        
        opcion = 0;
        itemsMenu[0] = "COMPRAR ENTRADA";
        itemsMenu[1] = "VER PROMOCIONES";
        itemsMenu[2] = "IMPRIMIR BOLETA";
        itemsMenu[3] = "SALIR";
        
        try{
            do{
                System.out.println("BIENVENIDO A LA ADMINISTRACIÓN DEL TEATRO MORO");
                System.out.println("-- VENTA DE ENTRADAS --");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCIÓN");
                for(int i=0; i<itemsMenu.length; i++){
                    System.out.println((i + 1) + ".- " + itemsMenu[i]);
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.length){
                    limpiaPantalla();
                    System.out.println("-- LA OPCIÓN ("+opcion+") NO ES VÁLIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.length);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCIÓN INGRESADA NO ES UN NÚMERO");
            System.out.println("");
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 1){
            limpiaPantalla();
            tipoAsiento(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 2){
            limpiaPantalla();
            muestraPromociones(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 3){
            limpiaPantalla();
            impresionBoleta(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion > 3){
            limpiaPantalla();
            salir();
        }
    }
    
    public static void impresionBoleta(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        int opcion;
        String[] itemsMenu = new String[3];
        String zonaTeatro;
        
        opcion = 0;
        itemsMenu[0] = "ZONA A - PALCO";
        itemsMenu[1] = "ZONA B - TRIBUNA BAJA";
        itemsMenu[2] = "ZONA C - TRIBUNA ALTA";
        
        try{
            do{
                System.out.println("-- SELECCIONE EL TIPO DE ENTRADA --");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCIÓN:");
                for(int i=0; i<itemsMenu.length; i++){
                    System.out.println((i + 1) + ".- " + itemsMenu[i]);
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.length){
                    limpiaPantalla();
                    System.out.println("-- LA OPCIÓN ("+opcion+") NO ES VÁLIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.length);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCIÓN INGRESADA NO ES UN NÚMERO");
            System.out.println("");
            impresionBoleta(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion >=1 && opcion <= itemsMenu.length){
            zonaTeatro = itemsMenu[(opcion - 1)];
            limpiaPantalla();
            buscarAsientoParaImprimir(opcion, zonaTeatro,asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void imprimeMapaAsientos(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        String filaLetras = "  | ";
        String filaNumeros = "";
        
        if(opcion == 1){
            cantFilas = 2;
            cantColumnas = 10;
            separador = "-------------------------------------------";
        }
        if(opcion == 2){
            cantFilas = 5;
            cantColumnas = 12;
            separador = "---------------------------------------------------";
        }else if(opcion == 3){
            cantFilas = 3;
            cantColumnas = 8;
            separador = "-----------------------------------";
        }
        
        for(int i=0; i<cantColumnas; i++){
            filaLetras += letrasColumnas[i]+" | ";
        }
        
        hayAsientosVendidos = false;
        
        System.out.println("-- "+zonaTeatro+" --");
        System.out.println("");
        System.out.println(filaLetras);
        System.out.println(separador);
        
        for(int i=0; i<cantFilas; i++){
            filaNumeros = (i + 1)+" |";
            for(int j=0; j<cantColumnas; j++){
                if(opcion == 1){
                    if(asientosPalco[i][j] == 1){
                        filaNumeros += " * |";
                        hayAsientosVendidos = true;
                    }
                    else filaNumeros += "   |";
                }else if(opcion == 2){
                    if(asientosPA[i][j] == 1){
                        filaNumeros += " * |";
                        hayAsientosVendidos = true;
                    }
                    else filaNumeros += "   |";
                }else if(opcion == 3){
                    if(asientosPB[i][j] == 1){
                        filaNumeros += " * |";
                        hayAsientosVendidos = true;
                    }
                    else filaNumeros += "   |";
                }
            }
            System.out.println(filaNumeros);
            System.out.println(separador);
        }
    }
    
    public static void buscarAsientoParaImprimir(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        imprimeMapaAsientos(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        
        if(!hayAsientosVendidos){
            System.out.println("NO HAY ASIENTOS VENDIDOS EN LA "+zonaTeatro);
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            limpiaPantalla();
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }else{
            System.out.println("(*) ASIENTOS VENDIDOS");
            if(asientoNoValido){
                System.out.println("-- EL ASIENTO "+asientoSeleccionado.toUpperCase()+" NO ES VÁLIDO --");
            }
            if(asientoNoOcupado){
                System.out.println("-- EL ASIENTO "+asientoSeleccionado.toUpperCase()+" NO ESTÁ VENDIDO --");
            }
            System.out.println("SELECCIONE EL ASIENTO PARA IMPRIMIR LA BOLETA");
            System.out.println("POR EJEMPLO: D2");
        }
        asientoSeleccionado = teclado.nextLine();
        asientoNoValido = false;
        asientoNoOcupado = false;
        
        String columnaAux = asientoSeleccionado.substring(0,1);
        String filaAux = asientoSeleccionado.substring(1,2);
        int filaAsiento = Integer.parseInt(filaAux);
        filaAsiento -= 1;
        int columnaAsiento = buscaColumnaDesdeLetra(columnaAux, letrasColumnas);
        
        if(columnaAsiento < 0 || columnaAsiento > cantColumnas) asientoNoValido = true;
        else{
            if(opcion == 1){
                if(asientosPalco[filaAsiento][columnaAsiento] == 0) asientoNoOcupado = true;
            }else if(opcion == 2){
                if(asientosPA[filaAsiento][columnaAsiento] == 0) asientoNoOcupado = true;
            }else if(opcion == 3){
                if(asientosPB[filaAsiento][columnaAsiento] == 0) asientoNoOcupado = true;
            }
        }
        
        if(asientoNoValido || asientoNoOcupado){
            limpiaPantalla();
            buscarAsientoParaImprimir(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }else{
            limpiaPantalla();
            imprimeBoleta(opcion, asientoSeleccionado, asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void imprimeBoleta(int opcion, String asientoSeleccionado, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        String[] datosEntrada = null;
        String txtDescuentoEntrada = "";
        String txtTotalEntrada = "";
        String txtCodigoBarras = "";
        
        switch(opcion){
            case 1:
                datosEntrada = entradasPalco.get(asientoSeleccionado.toUpperCase());
                break;
            case 2:
                datosEntrada = entradasPA.get(asientoSeleccionado.toUpperCase());
                break;
            case 3:
                datosEntrada = entradasPB.get(asientoSeleccionado.toUpperCase());
                break;
        }
        
        int nroBoleta = generarNroBoleta(1000, 9999);
        int hashBoleta = generarNroBoleta(100000, 999999);
        
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String txtHoy = hoy.format(formatter);
        
        String descripcionEntrada = "ENTRADA "+datosEntrada[0]+" - "+datosEntrada[3];
        int espaciosAdicionales = 43 - datosEntrada[0].length() - datosEntrada[3].length();
        for(int i=0; i<espaciosAdicionales; i++) descripcionEntrada += " ";
        
        NumberFormat precio = NumberFormat.getInstance(new Locale("es", "CL"));
        precio.setGroupingUsed(true);
        String precioEntrada = (Integer.parseInt(datosEntrada[2]) < 10000)? "$ "+precio.format(Integer.parseInt(datosEntrada[2])) : "$"+precio.format(Integer.parseInt(datosEntrada[2]));
        
        String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
        espaciosAdicionales = 11 - descuentoEntrada.length();
        for(int i=0; i<espaciosAdicionales; i++) txtDescuentoEntrada += " ";
        txtDescuentoEntrada += "$"+descuentoEntrada;
        
        String totalEntrada = precio.format(Integer.parseInt(datosEntrada[4]));
        espaciosAdicionales = 78 - totalEntrada.length();
        for(int i=0; i<espaciosAdicionales; i++) txtTotalEntrada += " ";
        txtTotalEntrada += "$"+totalEntrada;
        
        String codigoBarra = codBarras(String.valueOf(hashBoleta));
        espaciosAdicionales = 19;
        for(int i=0; i<espaciosAdicionales; i++) txtCodigoBarras += " ";
        txtCodigoBarras += codigoBarra;
        
        System.out.println("*******************************************************************************");
        System.out.println("*    TEATRO MORO SA                                                           *");
        System.out.println("*   RUT "+formatoRut("732767800")+"                                          --------------- *");
        System.out.println("*  AV TOMAS MORO 1285                                         | BOLETA "+nroBoleta+" | *");
        System.out.println("* LAS CONDES. SANTIAGO                                        --------------- *");
        System.out.println("* TEL: +56 2 2233 4455                                                        *");
        System.out.println("*******************************************************************************");
        System.out.println("FECHA:        "+txtHoy);
        System.out.println("RAZON SOCIAL: "+datosEntrada[5]);
        System.out.println("RUT:          "+formatoRut(datosEntrada[6]));
        System.out.println("*******************************************************************************");
        System.out.println("CANT  DESCRIPCION                                            PRECIO   DESCUENTO");
        System.out.println("   1  "+descripcionEntrada+precioEntrada+txtDescuentoEntrada);        
        //printBarcode(String.valueOf(nroBoleta));
        System.out.println("");
        System.out.println("                                                                          TOTAL");
        System.out.println(txtTotalEntrada);
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(txtCodigoBarras);
        
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static void salir(){
        System.out.println("GRACIAS POR COMPRAR ENTRADAS EN TEATRO MORO");
    }
    
    public static void muestraPromociones(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        String correccionEspacios;
        
        tipoEntradas[0] = "ZONA A - PALCO";
        tipoEntradas[1] = "ZONA B - TRIBUNA BAJA";
        tipoEntradas[2] = "ZONA C - TRIBUNA ALTA";
        
        precioEntradas[0] = "$15.000";
        precioEntradas[1] = "$10.000";
        precioEntradas[2] = "$ 7.000";
        
        limpiaPantalla();
        System.out.println("-- PRECIOS DE ENTRADAS --");
        
        for(int i=0; i<tipoEntradas.length; i++){
            correccionEspacios = (i == 0)? ":        " : ": ";
            System.out.println(tipoEntradas[i]+correccionEspacios+precioEntradas[i]);
        }
        
        System.out.println("");
        System.out.println("-- PROMOCIONES DISPONIBLES --");
        
        System.out.println("DESCUENTO ESTUDIANTE:   "+descuentoEstudiante+"%");
        System.out.println("DESCUENTO TERCERA EDAD: "+descuentoTerceraEdad+"%");
        System.out.println("* Para acceder al descuento de 3ra edad, debe ser mayor de 60 años");
        
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
       
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static int generarNroBoleta(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    
    public static String formatoRut(String rut){
        int R = Integer.parseInt(rut.substring(0, rut.length() - 1));
        String DV = rut.substring(rut.length() - 1, rut.length());
        
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
        formato.setGroupingUsed(true);
        return formato.format(R)+"-"+DV;
    }

    public static String codBarras(String nroBoleta){
        String barra = "";
        for(int i=0; i<nroBoleta.length(); i++) {
            char digit = nroBoleta.charAt(i);
            barra += digitoABarra(digit);
        }
        return barra;
    }

    public static String digitoABarra(char digito){
        if(digito == '0') return "|||||";
        else if(digito == '1') return ":||||";
        else if(digito == '2') return "::|||";
        else if(digito == '3') return ":::||";
        else if(digito == '4') return "::::|";
        else if(digito == '5') return ":::::";
        else if(digito == '6') return "|::::";
        else if(digito == '7') return "||:::";
        else if(digito == '8') return "|||::";
        else return "||||:";
    }
    
    public static void main(String[] args) {
        int[][] asientosPalco = new int[2][10];
        int[][] asientosPA = new int[5][12];
        int[][] asientosPB = new int[3][8];

        /*Random random = new Random();
        for (int i=0; i<5; i++){
            int filaPalco = random.nextInt(2);
            int columnaPalco = random.nextInt(10);
            asientosPalco[filaPalco][columnaPalco] = 1;
        }
        
        for (int i=0; i<15; i++){
            int filaPA = random.nextInt(5);
            int columnaPA = random.nextInt(12);
            asientosPA[filaPA][columnaPA] = 1;
        }
        
        for (int i=0; i<10; i++){
            int filaPB = random.nextInt(3);
            int columnaPB = random.nextInt(8);
            asientosPB[filaPB][columnaPB] = 1;
        }*/
        
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
}