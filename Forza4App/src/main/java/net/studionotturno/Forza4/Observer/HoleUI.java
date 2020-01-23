package net.studionotturno.Forza4.Observer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Elemento grafico che rappresenta un foro sul tabelloone del gioco
 * @author Raimondi Federico
 *
 */
public class HoleUI extends Circle{

	private boolean selected;
	private Color color;

	public HoleUI() {
		super();
		this.setCenterX(10);
		this.setCenterY(10);
		this.setRadius(25.0f);
		this.selected=false;
		this.setStroke(Color.BLACK);
		this.color=Color.WHITE;
		this.setSelected(false);
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		if(selected==true)this.setFill(this.color);
		else this.setFill(Color.WHITE);
		this.selected = selected;
	}

	public void setColor(Color color) {
		this.color=color;
		this.setFill(color);
	}

	public Color getColor() {
		return this.color;
	}

	public void setWinnerColor(Color color) {
		if(color.equals(Color.YELLOW))
			this.setColor(Color.DARKGOLDENROD);
		else if(color.equals(Color.RED))
			this.setColor(Color.DARKRED);
	}
}
