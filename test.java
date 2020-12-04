import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;

public class test {
    
    public static void main(String[] args) {
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(600, 600) , "Josh's Test" );
        window.setFramerateLimit(60);

        window.display();
    }

}
