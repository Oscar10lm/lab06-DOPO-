package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Interfaz gráfica principal del bosque.
 * 
 * @author Oscar Lasso - Juan Diego Gaitan
 * @version 1.0
 */
public class ForestGUI extends JFrame {
    public static final int SIDE = 20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel controlPanel;
    private PhotoForest photo;
    private Forest theForest;
    private JMenuItem itemNuevo, itemAbrir, itemSalvarComo, itemImportar, itemExportarComo, itemSalir;

    /**
     * Constructor principal que inicializa el bosque y los componentes visuales.
     */
    private ForestGUI() {
        theForest = new Forest();
        SIZE = theForest.getSize();
        prepareElements();
        prepareActions();
    }

    /**
     * Prepara y configura los elementos visuales de la ventana principal.
     */
    private void prepareElements() {
        setTitle("Schelling Forest");
        photo = new PhotoForest(this);
        ticTacButton = new JButton("Tic-tac");
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(photo, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(ticTacButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        
        prepareElementsMenu();
        pack();
        setResizable(false);
        photo.repaint();
    }

    /**
     * Construye y configura el menú superior de la ventana.
     */
    private void prepareElementsMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);

        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemSalvarComo = new JMenuItem("Salvar como");
        itemImportar = new JMenuItem("Importar");
        itemExportarComo = new JMenuItem("Exportar como");
        itemSalir = new JMenuItem("Salir");

        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalvarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemImportar);
        menuArchivo.add(itemExportarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
    }

    /**
     * Configura los oyentes de eventos para los componentes principales.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ticTacButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ticTacButtonAction();
                    }
                });
        prepareActionsMenu();
    }

    /**
     * Acción ejecutada por el botón Tic-Tac para avanzar el estado del bosque.
     */
    private void ticTacButtonAction() {
        theForest.ticTac();
        photo.repaint();
    }

    /**
     * Enlaza los botones del menú con sus respectivas acciones.
     */
    private void prepareActionsMenu() {
        itemNuevo.addActionListener(e -> optionNew());
        itemAbrir.addActionListener(e -> optionOpen());
        itemSalvarComo.addActionListener(e -> optionSaveAs());
        itemImportar.addActionListener(e -> optionImport());
        itemExportarComo.addActionListener(e -> optionExportAs());
        itemSalir.addActionListener(e -> optionExit());
    }

    /**
     * Crea un bosque nuevo y actualiza la interfaz.
     */
    private void optionNew() {
        theForest = new Forest();
        photo.repaint();
    }

    /**
     * Permite seleccionar un archivo para abrir un bosque existente.
     */
    private void optionOpen() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theForest = theForest.open(fileChooser.getSelectedFile());
                photo.repaint();
            } catch (ForestException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Fatal Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Permite seleccionar dónde guardar el bosque actual.
     */
    private void optionSaveAs() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theForest.saveAs(fileChooser.getSelectedFile());
            } catch (ForestException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Fatal Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Permite seleccionar un archivo para importar una configuración.
     */
    private void optionImport() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theForest = theForest.importFile(fileChooser.getSelectedFile());
                photo.repaint();
            } catch (ForestException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Fatal Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Permite seleccionar dónde exportar el estado del bosque.
     */
    private void optionExportAs() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theForest.exportAs(fileChooser.getSelectedFile());
            } catch (ForestException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Fatal Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Cierra la aplicación.
     */
    private void optionExit() {
        System.exit(0);
    }

    /**
     * Obtiene la instancia actual del bosque.
     */
    public Forest gettheForest() {
        return theForest;
    }

    /**
     * Método principal para iniciar la aplicación.
     */
    public static void main(String[] args) {
        ForestGUI cg = new ForestGUI();
        cg.setVisible(true);
    }
}

class PhotoForest extends JPanel {
    private ForestGUI gui;

    /**
     * Constructor del panel fotográfico del bosque.
     */
    public PhotoForest(ForestGUI gui) {
        this.gui = gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE * gui.SIZE + 10, gui.SIDE * gui.SIZE + 10));
    }

    /**
     * Dibuja gráficamente el estado actual del bosque.
     */
    public void paintComponent(Graphics g) {
        Forest theForest = gui.gettheForest();
        super.paintComponent(g);

        for (int c = 0; c <= theForest.getSize(); c++) {
            g.drawLine(c * gui.SIDE, 0, c * gui.SIDE, theForest.getSize() * gui.SIDE);
        }
        for (int f = 0; f <= theForest.getSize(); f++) {
            g.drawLine(0, f * gui.SIDE, theForest.getSize() * gui.SIDE, f * gui.SIDE);
        }
        for (int f = 0; f < theForest.getSize(); f++) {
            for (int c = 0; c < theForest.getSize(); c++) {
                if (theForest.getThing(f, c) != null) {
                    g.setColor(theForest.getThing(f, c).getColor());
                    if (theForest.getThing(f, c).shape() == Thing.SQUARE) {
                        g.fillRoundRect(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2, 2, 2);
                    } else {
                        g.fillOval(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2);
                    }
                    if (theForest.getThing(f, c).isLivingThing()) {
                        g.setColor(Color.red);
                        if (((LivingThing) theForest.getThing(f, c)).getEnergy() >= 50) {
                            g.drawString("+", gui.SIDE * c + 6, gui.SIDE * f + 15);
                        } else {
                            g.drawString("~", gui.SIDE * c + 6, gui.SIDE * f + 17);
                        }
                    }
                }
            }
        }
    }
}