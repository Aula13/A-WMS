package org.wms.view.common;

import it.rmautomazioni.view.controls.DigitalClock;
import it.rmautomazioni.view.factories.AbstractAppStyleFactory;
import it.rmautomazioni.view.factories.RMColour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * This abstract class is the basis of the GUI interface. It provides the
 * scheleton of the graphical interface for SAPA WMS. It is possible, through
 * methods provided, to add/remove Buttons, add/remove (sub)menu voices and
 * insert useful information. Programmer can't change this structure.
 *
 * @version	1.0
 * @date	1st February, 2014
 * @param	npanel	panel provided for the interface title
 * @param	opanel	panel provided for the button bar
 * @param	spanel	panel provided for the status bar
 * @param	jf	main frame
 * @param	bmenu	menu bar
 * @param	jtf	interface title
 * @return	the scheleton of the GUI with population methods, not instantiable
 */
public abstract class ContentPane extends JFrame {

    protected final JPanel npanel = new JPanel();
    protected final JPanel opanel = new JPanel(new GridLayout(10, 1));
    protected final JPanel spanel = new JPanel(new GridLayout(1, 5, 5, 10));
    protected JMenuBar bmenu;
    protected final AbstractAppStyleFactory asf;
    
    
    private static final long serialVersionUID = 1793665641428583475L;        
    protected DigitalClock digitalClock;
    /**
     * Fundamental: this method "draws" the interface, sets the panels and the
     * bars It also prepares the spaces for the population.
     *
     * @author	Stefano Pessina, Daniele Ciriello
     * @version	1.0
     * @date	1st February, 2014
     * @param	npanel	panel provided for the interface title
     * @param	opanel	panel provided for the button bar
     * @param	spanel	panel provided for the status bar
     * @param	jf	main frame
     * @param	bmenu	menu bar
     * @return	void
     */
    public ContentPane(AbstractAppStyleFactory absf) {

        this.asf = absf;
        initUI();

    }

    /**
     * GUI initialization
     */
    private void initUI() {
        /*jframe setup*/
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        setSize(xSize, ySize);
        setBackground(RMColour.RM_LIGHT_GRAY);
        setForeground(RMColour.RM_WHITE);
        setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(new BorderLayout(1, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /*bmenu Initialization*/        
        bmenu = asf.getJMenuBar();
        setJMenuBar(bmenu);

        /*npanel initialization*/
        npanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        getContentPane().add(npanel, BorderLayout.NORTH);

        /*opanel initialization*/
        opanel.setBackground(RMColour.RM_LIGHT_GRAY);
        opanel.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
        getContentPane().add(opanel, BorderLayout.WEST);

        /*spanel initialization*/
        spanel.setBackground(RMColour.RM_LIGHT_GRAY);
        spanel.setForeground(RMColour.RM_WHITE);
        getContentPane().add(spanel, BorderLayout.SOUTH);
        digitalClock = asf.getDigitalClock();
        spanel.add(digitalClock);

    }
}
