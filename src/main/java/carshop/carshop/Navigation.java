package carshop.carshop;

import javafx.scene.Scene;
import java.util.Stack;


public class Navigation {

    private Stack<Scene> sceneStack = new Stack<>();

    public void pushScene (Scene scene) {
        sceneStack.push(scene);
    }

    public Scene popScene(){
        if(!sceneStack.isEmpty()){
            return sceneStack.pop();
        }
        return null;

    }
}

