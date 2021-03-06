/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

/**
 *
 * @author Hugoriquelme
 */
import java.util.Scanner;

public class Buscaminas {

    Scanner scan = new Scanner(System.in);

    public static void main(String[] arg) {

        Scanner scan = new Scanner(System.in);
        int filas;
        int columnas;
        int numMinas;
        int contador;
        int c = 0;
        EspacioBM[][] buscaminas;
        System.out.println("-" + "-" + "Buscaminas" + "-" + "-");
        System.out.print("Ingrese el numero de filas(5-50): ");
        filas = scan.nextInt();
        if (filas < 5 || filas > 50) {
            filas = 10;
            c = 1;
        }
        System.out.print("Ingrese el numero de columnas(5-50): ");
        columnas = scan.nextInt();
        if (columnas < 5 || columnas > 50) {
            columnas = 10;
            c++;
            if (c == 2) {
                System.out.println("-" + "Coordenadas fuera de rango tamaño predeterminado 10X10" + "-");
            }
        }
        numMinas = filas + columnas;
        contador = (filas * columnas) - (numMinas);
        System.out.println("-" + "El numero de minas es: " + numMinas + "-");
        System.out.println("-" + "A jugar!!!" + "-");
        buscaminas = new EspacioBM[columnas][filas];
        buscaminas = new Buscaminas().llenarTablero(buscaminas, 0, 0, columnas, filas);
        buscaminas = new Buscaminas().colocarMinas(buscaminas, numMinas, columnas, filas);
        buscaminas = new Buscaminas().minasAlrededor(buscaminas, 0, 0, columnas, filas);
        buscaminas = new Buscaminas().juego(buscaminas, columnas, filas, contador);
    }

    public EspacioBM[][] llenarTablero(EspacioBM[][] buscaminas, int i, int j, int c, int f) {

        if (j < f) {
            if (i < c) {
                buscaminas[i][j] = new EspacioBM();
                buscaminas = llenarTablero(buscaminas, ++i, j, c, f);
            } else {
                i = 0;
                buscaminas = llenarTablero(buscaminas, i, ++j, c, f);
            }
        }
        return buscaminas;
    }

    public EspacioBM[][] colocarMinas(EspacioBM[][] buscaminas, int n, int c, int f) {

        int azar1 = (int) (Math.random() * (c - 1));
        int azar2 = (int) (Math.random() * (f - 1));
        if (n > 0) {
            if (!buscaminas[azar1][azar2].verMina() ) {
                buscaminas[azar1][azar2].colocarMina();
                n--;
            }
            buscaminas = colocarMinas(buscaminas, n, c, f);
        }
        return buscaminas;
    }

    public EspacioBM[][] minasAlrededor(EspacioBM[][] buscaminas, int i, int j, int c, int f) {

        if (j < f) {
            if (i < c) {
                if (buscaminas[i][j].verMina()) {
                    if (i > 0) {
                        buscaminas[i - 1][j].aumentarMinas();
                        if (j > 0) {
                            buscaminas[i - 1][j - 1].aumentarMinas();
                        }
                        if (j < f - 1) {
                            buscaminas[i - 1][j + 1].aumentarMinas();
                        }
                    }
                    if (i < c - 1) {
                        buscaminas[i + 1][j].aumentarMinas();
                        if (j > 0) {
                            buscaminas[i + 1][j - 1].aumentarMinas();
                        }
                        if (j < f - 1) {
                            buscaminas[i + 1][j + 1].aumentarMinas();
                        }
                    }
                    if (j > 0) {
                        buscaminas[i][j - 1].aumentarMinas();
                    }
                    if (j < f - 1) {
                        buscaminas[i][j + 1].aumentarMinas();
                    }
                }
                buscaminas = minasAlrededor(buscaminas, ++i, j, c, f);
            } else {
                i = 0;
                buscaminas = minasAlrededor(buscaminas, i, ++j, c, f);
            }
        }
        return buscaminas;
    }

    public void imprimir(EspacioBM[][] buscaminas, int i, int j, int c, int f) {

        if (j < f) {
            if (i < c) {
                System.out.print(buscaminas[i][j] + " ");
                imprimir(buscaminas, ++i, j, c, f);
            } else {
                i = 0;
                System.out.println("");
                imprimir(buscaminas, i, ++j, c, f);
            }
        }
    }

    public EspacioBM[][] juego(EspacioBM[][] buscaminas, int columnas, int filas, int contador) {

        imprimir(buscaminas, 0, 0, columnas, filas);
        System.out.println("-" + "Ingrese el numero de fila y columna que desea explorar" + "-");
        System.out.print("Ingrese el numero de la fila: ");
        int f = scan.nextInt();
        System.out.print("Ingrese el numero de la columna: ");
        int c = scan.nextInt();
        if (f <= filas && c <= columnas) {
            if (!buscaminas[c - 1][f - 1].verRevelado()) {
                buscaminas[c - 1][f - 1].cambiarEstado();
                contador--;
            }
            if (contador == 0) {
                System.out.println("-" + "-" + "GANASTE!!!" + "-" + "-" + "FELICIDADES!!!");
            } else if (buscaminas[c - 1][f - 1].verMina()) {
                imprimir(buscaminas, 0, 0, columnas, filas);
                System.out.println("-" + "-" + "BOOOOOOOOM!!!!!" + "-" + "-" + "perdiste el juego!!!");
            } else {
                juego(buscaminas, columnas, filas, contador);
            }
        } else {
            juego(buscaminas, columnas, filas, contador);
        }
        return buscaminas;
    }
}
