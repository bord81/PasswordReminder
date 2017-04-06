/*
The purpose of this program is to play around with some simple Java FX functionality as well as Base64 encoding and SHA hashes.

You can create a file and store authentication information in it (URL, login, password).
Access to the file by the program is done upon entering a master password which itself is not stored anywhere (its SHA-256 hash version is used to do the auth check).
The URL/login/password entries are stored in the file in Base64 format and are decoded by the program upon user request.
You can add entries and delete existing ones.

However please remember, that this program is just for fun - don't use it for sensitive data!
 */

package fxMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PasswordFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Password Reminder 2.0");
        stage.setResizable(false);
        stage.show();   
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
