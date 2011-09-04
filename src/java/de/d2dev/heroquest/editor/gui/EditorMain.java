/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditorMain.java
 *
 * Created on 23.08.2011, 12:59:07
 */

package de.d2dev.heroquest.editor.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.d2dev.fourseasons.swing.SwingUtil;
import de.d2dev.heroquest.client.ClientApplication;
import de.d2dev.heroquest.editor.Editor;
import de.d2dev.heroquest.engine.files.Files;
import de.d2dev.heroquest.engine.files.HqMapFile;
import de.d2dev.heroquest.engine.game.Map;
import de.d2dev.heroquest.engine.rendering.Renderer;
import de.d2dev.heroquest.engine.rendering.quads.Java2DRenderWindow;
import de.d2dev.heroquest.engine.rendering.quads.JmeRenderer;
import de.d2dev.heroquest.engine.rendering.quads.RunJmeCanvasInSwing;

/**
 *
 * @author Batti
 */
public class EditorMain extends javax.swing.JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Editor editor;
	
	public Java2DRenderWindow java2DRenderWindow = null;
	private JFrame jmeRenderWindow = null;
	public JmeRenderer jmeRenderer = null;
	
    /** Creates new form EditorMain */
    public EditorMain(Editor editor) {
        initComponents();
        
        this.editor = editor;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        newEmptyMapMenuItem = new javax.swing.JMenuItem();
        newTemplateMapMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        saveMapMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        renderWindowsMenu = new javax.swing.JMenu();
        java2DRenderWindowMenuItem = new javax.swing.JMenuItem();
        jMonkeyRenderWindowMenuItem = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        playMapMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HeroQuest Editor");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenu1.setText("File");

        jMenu3.setText("New");

        jMenu4.setText("Map");

        newEmptyMapMenuItem.setText("Emtpy map");
        newEmptyMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newEmptyMapMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(newEmptyMapMenuItem);

        newTemplateMapMenuItem.setText("Run template");
        newTemplateMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTemplateMapMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(newTemplateMapMenuItem);

        jMenu3.add(jMenu4);

        jMenu1.add(jMenu3);
        jMenu1.add(jSeparator1);

        jMenuItem1.setText("Open Map");
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator2);

        saveMapMenuItem.setText("Save Map");
        saveMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMapMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveMapMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        renderWindowsMenu.setText("Render Windows");

        java2DRenderWindowMenuItem.setText("Java 2D Render Window");
        java2DRenderWindowMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                java2DRenderWindowMenuItemActionPerformed(evt);
            }
        });
        renderWindowsMenu.add(java2DRenderWindowMenuItem);

        jMonkeyRenderWindowMenuItem.setText("jMonkey Render Window");
        jMonkeyRenderWindowMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMonkeyRenderWindowMenuItemActionPerformed(evt);
            }
        });
        renderWindowsMenu.add(jMonkeyRenderWindowMenuItem);

        jMenuBar1.add(renderWindowsMenu);

        jMenu5.setText("Play");

        playMapMenuItem.setText("Play map");
        playMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playMapMenuItemActionPerformed(evt);
            }
        });
        jMenu5.add(playMapMenuItem);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void java2DRenderWindowMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_java2DRenderWindowMenuItemActionPerformed
    	if ( this.java2DRenderWindow == null ) {
    		this.java2DRenderWindow = new Java2DRenderWindow( this.editor.renderer.getRederTarget(), this.editor.resources.resourceFinder );
    		
    		// take window bounds from properties file
    		this.java2DRenderWindow.setBounds( Integer.valueOf( this.editor.properties.getProperty( Editor.JAVA_2D_WINDOW_X, "0" ) ),
    										   Integer.valueOf( this.editor.properties.getProperty( Editor.JAVA_2D_WINDOW_Y, "0" ) ), 
    										   Integer.valueOf( this.editor.properties.getProperty( Editor.JAVA_2D_WINDOW_WIDTH, "500" ) ), 
    										   Integer.valueOf( this.editor.properties.getProperty( Editor.JAVA_2D_WINDOW_HEIGHT, "500" ) ) );
    	}
    	
    	this.java2DRenderWindow.setVisible(true);
    }//GEN-LAST:event_java2DRenderWindowMenuItemActionPerformed

    private void jMonkeyRenderWindowMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMonkeyRenderWindowMenuItemActionPerformed
    	if ( (this.jmeRenderWindow == null) && (this.jmeRenderer == null)){
    		
    		this.jmeRenderWindow = new JFrame ("JME-Renderer");
    		this.jmeRenderWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		JPanel panel = new JPanel();
    		
    		this.jmeRenderer = new JmeRenderer (this.editor.renderer.getRederTarget(), this.editor.resources.resourceFinder);
    		
    		JmeRenderer jmeRenderer = this.jmeRenderer;
    		java.awt.EventQueue.invokeLater(new RunJmeCanvasInSwing<JmeRenderer> (panel, jmeRenderer));
    		this.jmeRenderWindow.add(panel);
    		this.jmeRenderWindow.pack();
    		this.jmeRenderWindow.setVisible(true);
    		this.jmeRenderWindow.setSize(640, 480);
    	}
    }//GEN-LAST:event_jMonkeyRenderWindowMenuItemActionPerformed

    private void newEmptyMapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newEmptyMapMenuItemActionPerformed
    	// show dialog
    	NewEmptyMapDialog dialog = new NewEmptyMapDialog( this, true );
    	dialog.setVisible( true );
    	
    	this.editor.map = new Map( dialog.width, dialog.height );
    	
    	this.editor.renderer = new Renderer( this.editor.map, this.editor.renderTarget, this.editor.resources.resourceFinder );
    	this.editor.renderer.render();
    	
    	// update render windows
    	if ( this.java2DRenderWindow != null )
    		this.java2DRenderWindow.setRenderModel( this.editor.renderer.getRederTarget() );
    	
    	if ( this.jmeRenderer != null )
    		this.jmeRenderer.setRenderModel( this.editor.renderer.getRederTarget() );
    	
    }//GEN-LAST:event_newEmptyMapMenuItemActionPerformed

    private void newTemplateMapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTemplateMapMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newTemplateMapMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    	// close the editor
    	try {
			this.editor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }//GEN-LAST:event_formWindowClosing

    private void saveMapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMapMenuItemActionPerformed
    	JFileChooser chooser = Files.createHqMapFileChooser();
    	int action = chooser.showSaveDialog( this );
    	
    	if ( action == JFileChooser.APPROVE_OPTION ) {
    		String path = chooser.getSelectedFile().getAbsolutePath();
    		
    		if ( !path.endsWith( '.' + HqMapFile.EXTENSION ) ) {	// append extension if not there
    			path += '.' + HqMapFile.EXTENSION;
    		}
    		
    		if ( !SwingUtil.confirmFileWriting( this, new File( path ) ) ) {	// show warning before we overwrite existing files 
    			return;
    		}
    		
    		try {
    			HqMapFile.createHqMapFile( path, this.editor.map );
			} catch (Exception e) {
				e.printStackTrace();	// TODO
				return;
			}
    	}
    }//GEN-LAST:event_saveMapMenuItemActionPerformed

    private void playMapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playMapMenuItemActionPerformed
    	try {
	    	// save the map to a temporary file
	    	File tmpFile = File.createTempFile( "map", "." + HqMapFile.EXTENSION );
	    	
	    	HqMapFile.createHqMapFile( tmpFile.getAbsolutePath(), this.editor.map ) ;
	    	
	    	ClientApplication app = new ClientApplication( new HqMapFile( tmpFile.getAbsolutePath() ), this.editor.resources.resourceFinder );
	    	app.init();
	    	app.run();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }//GEN-LAST:event_playMapMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMonkeyRenderWindowMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem java2DRenderWindowMenuItem;
    private javax.swing.JMenuItem newEmptyMapMenuItem;
    private javax.swing.JMenuItem newTemplateMapMenuItem;
    private javax.swing.JMenuItem playMapMenuItem;
    private javax.swing.JMenu renderWindowsMenu;
    private javax.swing.JMenuItem saveMapMenuItem;
    // End of variables declaration//GEN-END:variables

}
