package main.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Sprite {
    public static final int TRANSPARENT_COLOR = 0xff00ff00;
    protected Image image;
    //Tọa độ in ra màn hình.
    protected int x;
    protected int y;
    //Tọa độ trong ảnh.
    protected int xU = 0;
    protected int yU = 0;
    protected int width;
    protected int height;
    protected int rWidth = 800;
    protected int rHeight = 800;

    public Sprite(Image image, int x, int y, int xU, int yU, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.xU = xU;
        this.yU = yU;
        this.width = width;
        this.height = height;
    }

    public Sprite(Image image, int x, int y, int xU, int yU) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.xU = xU;
        this.yU = yU;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxU() {
        return xU;
    }

    public void setxU(int xU) {
        this.xU = xU;
    }

    public int getyU() {
        return yU;
    }

    public void setyU(int yU) {
        this.yU = yU;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getrWidth() {
        return rWidth;
    }

    public void setrWidth(int rWidth) {
        this.rWidth = rWidth;
    }

    public int getrHeight() {
        return rHeight;
    }

    public void setrHeight(int rHeight) {
        this.rHeight = rHeight;
    }

    // Crop image
    public Image cropImage() {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage(pixelReader, xU, yU, width, height);
        return writableImage;
    }

    // Load image
    public void load() {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage(rWidth, rHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < rHeight; y++) {
            for (int x = 0; x < rWidth; x++) {
                int argb = pixelReader.getArgb(x, y);
                if (argb != TRANSPARENT_COLOR) {
                    if (xU == 0 && yU == 0) {
                        pixelWriter.setArgb(x, y, argb);
                    } else {
                        if (x >= xU && x <= xU + width && y >= yU && y <= yU + height)
                            pixelWriter.setArgb(x, y, argb);
                    }
                } else {
                    pixelWriter.setArgb(x, y, 0);
                }
            }
        }

        ImageView imageView = new ImageView(writableImage);
        image = imageView.getImage();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    //Phóng to ảnh
    private Image resample(Image input, int scaleFactor) {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();
        final int S = scaleFactor;

        WritableImage output = new WritableImage(
                W * S,
                H * S
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb(x * S + dx, y * S + dy, argb);
                    }
                }
            }
        }

        return output;
    }
}
