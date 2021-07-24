package utils.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Images {
    public static Image personImage = new Image(".\\resources\\icons\\person.png");
    public static Image deleteImage = new Image(".\\resources\\icons\\delete.png");
    public static Image editImage = new Image(".\\resources\\icons\\edit.png");
    public static ImageView  deleteImageView = new ImageView(deleteImage);
    public static ImageView  editImageView = new ImageView(editImage);
}
