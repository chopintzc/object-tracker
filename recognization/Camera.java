package recognization;


/*
 * A camera in this case is represented by the colour changing dots on the GUI. This
 * is an object oriented way of maintaining and updating these dots
 * */

public class Camera {
	private double x;//coordinate
	private double y;
	private String name;
	private String status;
	private int camID;
	private String[] commonItems;
	private int count;
	
	public Camera(double x, double y, String s, int id, String[] items){
		this.x=x;
		this.y=y;
		name = s;
		camID=id;
		status = "";
		commonItems = items;
		count = 0;
	}
	
	public String getAmount(int id){
		return getStat()[1];
	}
	
	public void setStat(String st, int count, String type){
		this.status = st;
		this.count = count;
		this.name = type;	
	}
	
	public String[] getStat(){
		String[] returnArray = {this.status, this.count+""};
		return returnArray;
	}
	
	
	public String[] getCommonItems(){
		return commonItems;
	}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public String getName(){return name;}
	public int getID(){return camID;}
	public int getCount(){return count;}
}
