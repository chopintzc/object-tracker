package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/*
 * This class is the background and logic of the placement of 
 * all that goes on the GUI
 * and handles all the logic for the changing numbers and colours needed
 * to show changes in the evironment
 * */

public class Base
{
	public Map m;
	
	Slider caminfo;
	ArrayList<String> itemInfo;
	ArrayList<String> colorArray;
	ArrayList<String> ids;
	ArrayList<String> numArray;
	ArrayList<String[]> whereami;
	ArrayList<String> locations;
	ImagePanel panel;
	Graphics currGraphs;
	JLabel cameraLabel;
	JLabel scrollLabel;
	JFrame mainFrame = new JFrame();
	JPanel middlePanel;
	JPanel cameraPane;
	Camera cam, cam1;
	
    public Base()
    {
    	m = new Map("image\store.png");
    	
    	double x = 170;
    	double y = 135;
    	String s ="Produce";
    	int id = 5;
    	String[] items = {"Oranges"};
    	cam = new Camera(x,y,s,id,items);


    	x = 475;
    	y = 133;
    	s ="Books";
    	id = 6;
    	String[] items1 = {"cook books"};
    	cam1 = new Camera(x,y,s,id,items1);
    	
    	m.addCamera(cam);
    	m.addCamera(cam1);
    	//this.go();
    }

    /*Main runnable program, makes the java application UI*/
    public void go()
    {
    	
    }
    
    public void updateWindow(){
    	ArrayList<String> colorArray;
    	ArrayList<String> numArray;
    	ArrayList<String[]> whereami;
    	ArrayList<String> locations;
		
		panel = new ImagePanel(new ImageIcon(m.getURL()).getImage());
		GridLayout gridLayout = new GridLayout(1,2);
        middlePanel = new JPanel(gridLayout);

        middlePanel = getPanel(Color.GREEN);
        cameraPane = getPanel(Color.GREEN);
        
        Dimension scrollSize = new Dimension(580,90);
        middlePanel.setPreferredSize(scrollSize);
        
        Dimension cameraSize = new Dimension(580,90);
        cameraPane.setPreferredSize(cameraSize);   
    	caminfo = m.checkCams();

		itemInfo = caminfo.getReport();
		colorArray = caminfo.getColors();
		numArray = caminfo.getNumbers();
		whereami = caminfo.getWhere();

		ids = caminfo.getCameraID();
		locations = caminfo.getXY();

    	
		//camera block, not yet complete
		cameraLabel = new JLabel("Will be the camera");
		cameraLabel.setFont(new Font("Verdana",1,20));
		cameraPane.add(cameraLabel);
		cameraPane.setBorder(new LineBorder(Color.BLACK)); // make it easy to see
		cameraLabel.setVisible(true);
    
		//scroll block
		scrollLabel = new JLabel("Will be a scroll of items and buttons to edit/reset");
		scrollLabel.setFont(new Font("Verdana",1,20));
		middlePanel.add(scrollLabel);
		middlePanel.setBorder(new LineBorder(Color.BLACK));
		scrollLabel.setVisible(true);
    	
    	/*This runs a query against the colour change method until the program ends */
		mainFrame.add(middlePanel, BorderLayout.SOUTH);
		mainFrame.add(panel, BorderLayout.CENTER);
		mainFrame.add(cameraPane, BorderLayout.NORTH);
    
		mainFrame.setVisible(true);
		middlePanel.setVisible(true);
		panel.setVisible(true);
		cameraPane.setVisible(true);

		currGraphs = panel.getGraphics();
		
		panel.paintCircle(currGraphs, locations, colorArray, numArray, whereami);
		mainFrame.pack();        

		caminfo.refreshCircles();
    }
    
    private JPanel getPanel(Color c)
    {
        JPanel result = new JPanel();
        result.setBorder(BorderFactory.createLineBorder(c));
        return result;
    }

}

class ImagePanel extends JPanel {
	 
	  private Image img;
	  private ArrayList<String> toDraw;
	  private ArrayList<String> neededColors;
	  private ArrayList<String> neededNumbers;
	  private ArrayList<String[]> whereami;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }
	 
	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }
	 
	  public void paintCircle(Graphics g, ArrayList<String> td, ArrayList<String> nc, ArrayList<String> nn, ArrayList<String[]> wa) {
		  toDraw = td;
		  neededColors = nc;
		  neededNumbers = nn;
		  whereami = wa;
		  try{ 
			  paintComponent(g); 
		  }
		 catch (Exception e){
			  System.out.println("Delay: Lost connection for a short time.");
		  }
		  
		  Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			  public void uncaughtException(Thread t, Throwable e){
				  System.out.println("Uncaught");
			
			  }
		  });
		  
	  }
	  
	  
	/*draws the colours dots over the camera locations depending on state*/  
	  @Override 
	  public void paintComponent(Graphics g) {
		    super.paintComponent(g);//2d);
		    
		    
		    g.drawImage(img, 0, 0, null);
            Graphics2D g2d = (Graphics2D)g;

            Field field;
            Color COLOR;

            for(int i = 0; i<toDraw.size(); i++){
            	
            	try{
            		field = Class.forName("java.awt.Color").getField(neededColors.get(i));
            		COLOR = (Color)field.get(null);
                }
            	catch(Exception e){
            		System.out.println(e);
            		COLOR = Color.BLACK;
            	}
                g2d.setColor(COLOR);
        	    g2d.fillOval( (int)Double.parseDouble(toDraw.get(i).split(" ")[0]), (int)Double.parseDouble(toDraw.get(i).split(" ")[1]),20,20);
            
            	try{
            		COLOR = Color.BLACK;
            		g2d.setColor(COLOR);
            		g.setFont(new Font("default", Font.BOLD, 32));
           		 	g.drawString(neededNumbers.get(i), (int)Double.parseDouble(whereami.get(i)[0])-60, (int)Double.parseDouble(whereami.get(i)[1])-60);
           		 
            	}
            	catch(Exception e){
            		System.out.println(e);
            		COLOR = Color.BLACK;
            	}
            }
      }
	  
}