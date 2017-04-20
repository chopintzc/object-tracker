package recognization;

import java.util.ArrayList;

/*
 * This class is an object-oriented take on the settings, background, text, and set-up
 * of the GUI. It keeps track of the status star location, background changes, and where the 
 * units of products are displayed
 */
public class Map{
	private ArrayList<Camera> cams = new ArrayList<Camera>();
    private String URL;
    
    public Map() {
    	URL ="";
    }
    public Map(String url) {
    	URL = url;
    }
    public String getURL(){
    	return URL;
    }
    
    //adds a status dot to the GUI
    public void addCamera(Camera c){
    	cams.add(c);
    }
     public ArrayList<Camera> getAllCameras(){
    	 return cams;
     }
     
     /*returns a set of all the camera's attributes
      * and is therefore used for formatting.
      * Does this by setting all the values in the slider object so that the data is up to date and in one location 
      * */
     public Slider checkCams(){
    	 Slider d = new Slider();
    	 Camera temp;
    	 for (int i =0; i<cams.size(); i++){
    		 temp = cams.get(i);
    		 
    		 d.addNums(temp.getAmount(temp.getID()));
    		     		 
    		 d.addCameraID(cams.get(i).getID());
    		 d.addXY(cams.get(i).getX()+" "+cams.get(i).getY());
    		 d.addToWhere(cams.get(i).getX()+"", cams.get(i).getY()+"");
    		 
    		 //chooses the states of the star status dot
    		 if(temp.getStat()[0].equals("MESS")){
    			 d.addToReport("Item out of place at "+temp.getName()+"\t");
    			 d.addDotArray("red");
    		 }
    		 else{
    			 d.addToReport("");
    			 d.addDotArray("white");
    		 }
    	 }
    	 return d;
     }
}