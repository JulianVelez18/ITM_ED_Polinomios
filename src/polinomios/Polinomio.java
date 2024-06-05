package polinomios;

import java.awt.Font;
import javax.swing.JLabel;

public class Polinomio {

    private Nodo cabeza;

    public Polinomio() {
        cabeza = null;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void agregar(Nodo n) {
        if (n != null) {
            if (cabeza == null) {
                cabeza = n;
            } else {
                Nodo apuntador = cabeza;
                Nodo predecesor = null;
                int encontrado = 0;
                while (apuntador != null && encontrado == 0) {
                    if (n.exponente == apuntador.exponente) {
                        encontrado = 1;
                    } else if (n.exponente < apuntador.exponente) {
                        encontrado = 2;
                    } else {
                        predecesor = apuntador;
                        apuntador = apuntador.siguiente;
                    }
                }
                if (encontrado == 1) {
                    double coeficiente = apuntador.coeficiente + n.coeficiente;
                    if (coeficiente == 0) {
                        //quitar el nodo
                        if (predecesor == null) {
                            cabeza = apuntador.siguiente;
                        } else {
                            predecesor.siguiente = apuntador.siguiente;
                        }
                    } else {
                        apuntador.coeficiente = coeficiente;
                    }

                } else {
                    insertar(n, predecesor);
                }
            }
        }
    }

    public void insertar(Nodo n, Nodo predecesor) {
        if (n != null) {
            if (predecesor == null) {
                n.siguiente = cabeza;
                cabeza = n;
            } else {
                n.siguiente = predecesor.siguiente;
                predecesor.siguiente = n;
            }
        }
    }

    public String[] obtenerTextos() {
        String[] lineas = new String[2];
        String espacio = " ";
        lineas[0] = "";
        lineas[1] = "";

        Nodo apuntador = cabeza;
        while (apuntador != null) {
            String texto = String.valueOf(apuntador.coeficiente) + " X";
            if (apuntador.coeficiente >= 0) {
                texto = "+" + texto;
            }
            lineas[0] += String.format("%0" + texto.length() + "d", 0).replace("0", espacio);
            lineas[1] += texto;

            texto = String.valueOf(apuntador.exponente);
            lineas[0] += texto;
            lineas[1] += String.format("%0" + texto.length() + "d", 0).replace("0", espacio);

            apuntador = apuntador.siguiente;
        }
        return lineas;
    }

    public void mostrar(JLabel lbl) {
        String[] lineas = obtenerTextos();
        String espacio = "&nbsp;";
        lineas[0] = lineas[0].replace(" ", espacio);
        lineas[1] = lineas[1].replace(" ", espacio);
        lbl.setFont(new Font("Courier New", Font.PLAIN, 12));
        lbl.setText("<html>" + lineas[0] + "<br>" + lineas[1] + "</html>");
    }

    public Nodo getMayorExponente() {
        if (cabeza != null) {
            Nodo apuntador = cabeza;
            while (apuntador.siguiente != null) {
                apuntador = apuntador.siguiente;
            }
            return apuntador;
        }
        return null;
    }

    public Polinomio getDerivada() {
        Polinomio d = new Polinomio();

        Nodo apuntador = cabeza;
        while (apuntador != null) {
            if (apuntador.exponente != 0) {
                Nodo n = new Nodo(apuntador.coeficiente * apuntador.exponente, apuntador.exponente - 1);
                d.agregar(n);
            }
            apuntador = apuntador.siguiente;
        }

        return d;
    }

    //*****************Métodos estáticos
    public static Polinomio sumar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null || apuntador2 != null) {
            Nodo n = null;
            if (apuntador1 != null && apuntador2 != null
                    && apuntador1.exponente == apuntador2.exponente) {
                if (apuntador1.coeficiente + apuntador2.coeficiente != 0) {
                    n = new Nodo(apuntador1.coeficiente + apuntador2.coeficiente, apuntador1.exponente);
                }
                apuntador1 = apuntador1.siguiente;
                apuntador2 = apuntador2.siguiente;
            } else if ((apuntador2 == null) || (apuntador1 != null && apuntador1.exponente < apuntador2.exponente)) {
                n = new Nodo(apuntador1.coeficiente, apuntador1.exponente);
                apuntador1 = apuntador1.siguiente;
            } else {
                n = new Nodo(apuntador2.coeficiente, apuntador2.exponente);
                apuntador2 = apuntador2.siguiente;
            }
            if (n != null) {
                pR.agregar(n);
            }
        }

        return pR;
    }

    public static Polinomio restar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null || apuntador2 != null) {
            Nodo n = null;
            if (apuntador1 != null && apuntador2 != null
                    && apuntador1.exponente == apuntador2.exponente) {
                if (apuntador1.coeficiente - apuntador2.coeficiente != 0) {
                    n = new Nodo(apuntador1.coeficiente - apuntador2.coeficiente, apuntador1.exponente);
                }
                apuntador1 = apuntador1.siguiente;
                apuntador2 = apuntador2.siguiente;
            } else if ((apuntador2 == null) || (apuntador1 != null && apuntador1.exponente < apuntador2.exponente)) {
                n = new Nodo(apuntador1.coeficiente, apuntador1.exponente);
                apuntador1 = apuntador1.siguiente;
            } else {
                n = new Nodo(-apuntador2.coeficiente, apuntador2.exponente);
                apuntador2 = apuntador2.siguiente;
            }
            if (n != null) {
                pR.agregar(n);
            }
        }

        return pR;
    }

    public static Polinomio multiplicar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null) {
            while (apuntador2 != null) {
                Nodo n = new Nodo(apuntador1.coeficiente * apuntador2.coeficiente, apuntador1.exponente + apuntador2.exponente);
                pR.agregar(n);
                apuntador2 = apuntador2.siguiente;
            }
            apuntador1 = apuntador1.siguiente;
            apuntador2 = p2.getCabeza();
        }

        return pR;
    }

   private static boolean esDivisible(Nodo n1, Nodo n2) {
        if (n1 != null && n2 != null) {
            return n1.exponente >= n2.exponente && n1.coeficiente % n2.coeficiente == 0;
        } else {
            return false;
        }
    }

    public static Polinomio[] dividir(Polinomio p1, Polinomio p2) {

        Polinomio[] pR = new Polinomio[2];
        
        pR[0] = new Polinomio();
        pR[1] = p1;

        Nodo apuntador1 = pR[1].getMayorExponente();
        Nodo apuntador2 = p2.getMayorExponente();

        while (esDivisible(apuntador1, apuntador2)) {

            System.out.print("P: ");
            printPol(pR[1].getCabeza());
            System.out.println("");

            Nodo cociente = new Nodo(apuntador1.coeficiente / apuntador2.coeficiente, apuntador1.exponente - apuntador2.exponente);
            System.out.println("C: " + cociente.coeficiente + "x^" + cociente.exponente + " ");

            pR[0].agregar(cociente);
            pR[1] = restar(pR[1], multiNP(cociente, p2));

            //Si es una división exacta Residuo = 0
            if (pR[1].cabeza == null) {
                pR[1].cabeza = new Nodo(0, 0);
            }
            apuntador1 = pR[1].getMayorExponente();
        }
        return pR;
    }
    
    //Función recursiva para ir viendo cómo cambia el Residuo
    public static void printPol(Nodo n) {
        if (n != null) {
            System.out.print(n.coeficiente + "x^" + n.exponente + " ");
        }
        if (n.siguiente != null) {
            printPol(n.siguiente);
        }
    }

    //Multiplicación entre nodo (Monómio) y Polinómio
    public static Polinomio multiNP(Nodo mono, Polinomio poli) {
        Polinomio pR = new Polinomio();
        Nodo apuntador = poli.getCabeza();

        if (mono != null) {
            while (apuntador != null) {
                Nodo res = new Nodo(mono.coeficiente * apuntador.coeficiente, mono.exponente + apuntador.exponente);
                pR.agregar(res);
                apuntador = apuntador.siguiente;
            }
        }
        return pR;
    }
}
