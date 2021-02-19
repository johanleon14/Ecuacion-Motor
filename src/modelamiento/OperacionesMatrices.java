/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelamiento;

/**
 *
 * @author Wanana
 */
public class OperacionesMatrices {

    /**
     * @param args the command line arguments
     */
   

    public double[][] multiplicarDosMatrices(double matriz[][], double matriz2[][]) {
        double aux1[][] = new double[matriz.length][matriz[0].length];
        double aux2[][] = new double[matriz2.length][matriz2[0].length];
        double auxr[][] = new double[matriz.length][matriz2[0].length];
         for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print (matriz[i][j]+" ");
                aux1[i][j] = matriz[i][j];
            }
             System.out.println("");
             
        }
         
        for (int i = 0; i < matriz2.length; i++) {
            for (int j = 0; j < matriz2[0].length; j++) {
                aux2[i][j] = matriz2[i][j];
                System.out.print(matriz2[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println(" ");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz2[0].length; j++) {
                for (int z = 0; z < matriz[0].length; z++) {
                    auxr[i][j] += aux1[i][z] * aux2[z][j];

                }
            }
        }

        return auxr;
    }

    public void mostrar(double matriz[][]) {
        System.out.println("");
        for (int x = 0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y = 0; y < matriz[x].length; y++) {
                System.out.print(matriz[x][y]);
                if (y != matriz[x].length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println("|");
        }
    }

    public double[][] inversa(double matriz[][]) {
        double aux[][] = new double[matriz[0].length][matriz.length];
        for (int i = 0; i < matriz[0].length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                aux[i][j] = matriz[i][j];
            }
        }
        double det = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
        matriz[0][0] = aux[1][1];
        matriz[0][1] = -1 * aux[1][0];
        matriz[1][0] = -1 * aux[0][1];
        matriz[1][1] = aux[0][0];
        //traspuesta
        for (int i = 0; i < matriz[0].length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                aux[i][j] = matriz[j][i] * (1 / det);
            }
        }
        return aux;
    }

    public double[][] matrizInversa(double[][] matriz) {
        double det = 1 / determinante(matriz);
        double[][] nmatriz = matrizAdjunta(matriz);
        multiplicarMatriz(det, nmatriz);
        return nmatriz;
    }

    public void multiplicarMatriz(double n, double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j] *= n;
            }
        }
    }

    public double[][] matrizAdjunta(double[][] matriz) {
        return matrizTranspuesta(matrizCofactores(matriz));
    }

    public double[][] matrizCofactores(double[][] matriz) {
        double[][] nm = new double[matriz.length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                double[][] det = new double[matriz.length - 1][matriz.length - 1];
                double detValor;
                for (int k = 0; k < matriz.length; k++) {
                    if (k != i) {
                        for (int l = 0; l < matriz.length; l++) {
                            if (l != j) {
                                int indice1 = k < i ? k : k - 1;
                                int indice2 = l < j ? l : l - 1;
                                det[indice1][indice2] = matriz[k][l];
                            }
                        }
                    }
                }
                detValor = determinante(det);
                nm[i][j] = detValor * (double) Math.pow(-1, i + j + 2);
            }
        }
        return nm;
    }

   public double[][] matrizTranspuesta(double[][] matriz) {
        double[][] nuevam = new double[matriz[0].length][matriz.length];
       
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                
                nuevam[j][i] = matriz[i][j];
            }
        }
        return nuevam;
    }

    public double determinante(double[][] matriz) {
        double det;
        if (matriz.length == 2) {
            det = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
            return det;
        }
        double suma = 0;
        for (int i = 0; i < matriz.length; i++) {
            double[][] nm = new double[matriz.length - 1][matriz.length - 1];
            for (int j = 0; j < matriz.length; j++) {
                if (j != i) {
                    for (int k = 1; k < matriz.length; k++) {
                        int indice = -1;
                        if (j < i) {
                            indice = j;
                        } else if (j > i) {
                            indice = j - 1;
                        }
                        nm[indice][k - 1] = matriz[j][k];
                    }
                }
            }
            if (i % 2 == 0) {
                suma += matriz[i][0] * determinante(nm);
            } else {
                suma -= matriz[i][0] * determinante(nm);
            }
        }
        return suma;
    }
}
