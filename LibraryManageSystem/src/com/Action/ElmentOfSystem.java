package com.Action;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ElmentOfSystem extends ImageIcon{
	private static String path = "bin\\com\\Action\\data\\images";
    private static ImageIcon logo =  new ImageIcon(path+"\\logo.jpg"); 
    
    public ElmentOfSystem(){
    
    }
    
    public static ImageIcon getIconOfSystem(){
    	return logo;
    }   
    
    public static String getPathofSystem(){
    	return path;
    }
}
