package infolaby;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Case extends JPanel {
	
	private boolean state = false; // Etat de la case (accessible ou mur)
	private float done = 0, doneMax = 400;	// d�j� visit�e ou pas
	private String color;	// Couleur de la case
	private Case[] voisins = new Case[4];	// Voisins � la case
	private int Nrj = 0;	// Energie de la case
	private float r1 = 215, r2 = 2;
	private float v1 = 215, v2 = 0;
	private float b1 = 255, b2 = 83;
	
			public void paintComponent(Graphics g){	
			
			//
				if(color=="gris"){ // Si la couleur est grise on l'affiche en gris
					g.setColor(Color.gray);
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
				}
				else if(!state){	// Si pas de couleur donn�e et state = false on l'affiche en noir
					g.setColor(Color.black);	
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
				}
				
				if(done == 0 && color != "gris" && state){
					g.setColor(Color.white);
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
				}
				else if(color != "gris" && state){
					g.setColor(new Color(Math.round(((r2-r1)/this.doneMax)*this.done + r1), Math.round(((v2-v1)/this.doneMax)*this.done + v1),Math.round(((b2-b1)/this.doneMax)*this.done + b1)));
					//g.fillRect(this.getWidth()/4, this.getHeight()/4, this.getWidth()/2, this.getHeight()/2);
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
				}				
				
			}
			

public float getDoneMax() {
				return doneMax;
			}


public void setDoneMax(float doneMax) {
			this.doneMax = doneMax;
		}

/**
 * @return the state
 */
public boolean getState() {
	return state;
}

/**
 * @param state the state to set
 */
public void setState(boolean state) {
	this.state = state;
}

/**
 * @return the color
 */
public String getColor() {
	return color;
}

/**
 * @param color the color to set
 */
public void setColor(String color) {
	this.color = color;
}

/**
 * @return the voisins
 */
public Case[] getVoisins() {
	return voisins;
}

/**
 * @param voisins the voisins to set
 */
public void setVoisins(Case[] voisins) {
	this.voisins = voisins;
}


/**
 * @return the nrj
 */
public int getNrj() {
	return Nrj;
}


/**
 * @param nrj the nrj to set
 */
public void setNrj(int nrj) {
	Nrj = nrj;
}


/**
 * @return the done
 */
public float getDone() {
	return done;
}


/**
 * @param done the done to set
 */
public void setDone(float done) {
	this.done = done;
}

}

