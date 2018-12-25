package logic.dataTypes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/* Each object of this class has the purpose of
 * representing a piece in the chess game.
 * In a different class, the objects are put into a GridPane
 * 
 * Each object consists of a type and a coordinate on the chess board
 */
public class ChessPiece extends ImageView {

	private final String[] IMAGE_NAMES = { "b_pawn.png", "b_rook.png", "b_bishop.png", "b_knight.png", "b_queen.png",
			"b_king.png", "w_pawn.png", "w_rook.png", "w_bishop.png", "w_knight.png", "w_queen.png", "w_king.png",
			"semi_transparent.png" };
	private String imageLinkRoot = "/icons/";

	private int type; // type of chess piece

	// Constructor
	// initializes the right image
	public ChessPiece(int type) {
		super();
		this.type = type;

		int index = getImageIndex();

		changeImage(index);

	}

	// Sets the image the user sees
	public void changeImage(int imageIndex) {
		String fullImageLink = imageLinkRoot + IMAGE_NAMES[imageIndex];
		if (imageIndex == 12) {
			this.setOpacity(0.01);
		}

		Image pic = new Image(getClass().getResourceAsStream(fullImageLink), 75, 75, true, true);
		super.setImage(pic);
	}

	// returns the index of the right image in IMAGE_NAMES based on this objects
	// type variable
	public int getImageIndex() {
		int index = type;
		if (index == 0 || index == 100) {
			return 12;
		} else {
			if (index < 0) {
				index *= -1;
				index += 5;
			} else
				index--;
			return index;
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public int[] getCoordinate() {
		int row = GridPane.getRowIndex(this);
		int column = GridPane.getColumnIndex(this);
		int[] coordinate = { column, row };

		return coordinate;
	}

	public int getArraylocation() {
		int[] coordinate = getCoordinate();

		int number = 0;

		number += coordinate[0];
		number += 10 * coordinate[1];

		return number;
	}

}
