package fxMain;

import fstor.Entry;
import fstor.Table;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.FileMan;

public class FXMLDocumentController implements Initializable {

    private boolean pass_entered = false;
    private boolean enter_new_entry = false;
    private boolean new_master_pass = false;
    private File currentf;
    private final FileChooser fchoose = new FileChooser();
    private Stage stage;
    private Table table;
    private int pointer;
    private FileMan fileman = service.FileMan.getFileMan();

    @FXML
    private AnchorPane ancor;
    @FXML
    private Button addpass;
    @FXML
    private Button viewstor;
    @FXML
    private Button genrand;
    @FXML
    private Button ok;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private TextField url_field;
    @FXML
    private TextField login_field;
    @FXML
    private TextField pass_field;
    @FXML
    private Label label;
    @FXML
    private Pane gpass_pane;
    @FXML
    private Label gpass_label;
    @FXML
    private TextField gpass_field;
    @FXML
    private Label url_label;
    @FXML
    private Button delete;
    @FXML
    private Pane dentry_pane;
    @FXML
    private Label login_label;
    @FXML
    private Label pass_label;

    @FXML
    private void deleteHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            if (!table.getPasswords().isEmpty()) {
                controlsShow(false);
                dentry_pane.setVisible(true);
            }
        }
    }

    @FXML
    private void delYesHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            boolean isSaved;
            dentry_pane.setVisible(false);
            Table temp_table = objectCopy(table);
            table.getPasswords().remove(pointer);
            fileman.saveTable(currentf);
            isSaved = fileman.saveTable(table);
            if (isSaved) {
                if (!table.getPasswords().isEmpty()) {
                    if (pointer != 0) {
                        pointer--;
                        fieldsRefresh(table.getPasswords().get(pointer).getUrl(), table.getPasswords().get(pointer).getLogin(), table.getPasswords().get(pointer).getPass());
                    } else {
                        fieldsRefresh(table.getPasswords().get(pointer).getUrl(), table.getPasswords().get(pointer).getLogin(), table.getPasswords().get(pointer).getPass());
                    }
                } else {
                    pointer = 0;
                    fieldsRefresh("", "", "");
                }
            } else {
                label.setText("File write error");
                table = temp_table;
            }
            controlsShow(true);
        }
    }

    @FXML
    private void delNoHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            dentry_pane.setVisible(false);
            controlsShow(true);
        }
    }

    @FXML
    private void gpassHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            boolean isNumber = true;
            int countd = 1;
            String count;
            count = gpass_field.getText();
            try {
                countd = Integer.parseInt(count);
            } catch (NumberFormatException ex) {
                gpass_label.setText("Please enter a number");
                isNumber = false;
            }
            if (isNumber) {
                String pass;
                String tempUrl = url_field.getText();
                String tempLogin = login_field.getText();
                gpass_field.setText("");
                pass = service.RandService.getRandService().getRandom(countd);
                gpass_pane.setVisible(false);
                newEntryDisplay();
                url_field.setText(tempUrl);
                login_field.setText(tempLogin);
                pass_field.setText(pass);

            }
        }
    }

    @FXML
    private void okHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            if (!enter_new_entry) {
                mainMenuDisplay();
            } else if (url_field.getText().length() > 1 && login_field.getText().length() > 1 && pass_field.getText().length() > 1) {
                boolean isSaved;
                boolean typeCorrect = true;
                Entry newEntry = new Entry(url_field.getText(), login_field.getText(), pass_field.getText());
                try {
                    fileman.decodePreCheck(newEntry);
                } catch (IllegalArgumentException ex) {
                    typeCorrect = false;
                }
                if (typeCorrect) {
                    if (table.getPasswords().isEmpty()) {
                        table.getPasswords().add(0, newEntry);
                    } else {
                        table.getPasswords().add(newEntry);
                    }
                    fileman.saveTable(currentf);
                    isSaved = fileman.saveTable(table);
                    if (!isSaved) {
                        label.setText("File write error!");
                        table.getPasswords().remove(table.getPasswords().size() - 1);
                    } else {
                        pointer = table.getPasswords().size() - 1;
                        label.setText("File write success");
                    }
                    enter_new_entry = false;
                    prev.setVisible(true);
                    next.setVisible(true);
                    delete.setVisible(true);
                    genrand.setText("Add new entry");
                    ok.setText("Back to main menu");
                } else {
                    label.setText("Incompatible characters!");
                }
            } else {
                label.setText("Pls enter at least 2 chars in each field!");
            }
        }
    }

    @FXML
    private void genRandHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            if (!enter_new_entry) {
                enter_new_entry = true;
                newEntryDisplay();
            } else {
                String turl = url_field.getText();
                String tlogin = login_field.getText();
                mainDisplay(false);
                url_field.setText(turl);
                login_field.setText(tlogin);
                gpass_pane.setVisible(true);
                gpass_label.setText("Enter pass size (long one takes a while)");

            }
        }
    }

    @FXML
    private void prevHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            if (pointer > 0) {
                pointer--;
                fieldsRefresh(table.getPasswords().get(pointer).getUrl(), table.getPasswords().get(pointer).getLogin(), table.getPasswords().get(pointer).getPass());
            }
        }
    }

    @FXML
    private void nextHandler(MouseEvent event) {
        if (!pass_entered) {
            exit();
        } else {
            if (pointer < table.getPasswords().size()) {
                if ((pointer + 1) < table.getPasswords().size()) {
                    pointer++;
                    fieldsRefresh(table.getPasswords().get(pointer).getUrl(), table.getPasswords().get(pointer).getLogin(), table.getPasswords().get(pointer).getPass());
                }
            }
        }
    }

    @FXML
    private void passHandler(KeyEvent event) {
        if (!pass_entered) {
            String pass;
            boolean isSaved = false;
            if (!new_master_pass) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    pass = pass_field.getText();
                    if (!pass.isEmpty()) {
                        boolean incFileContents = false;
                        fileman.decodeTable(pass);
                        Table new_table = null;
                        try {
                            new_table = fileman.decodeTable(table);
                        } catch (IllegalArgumentException ex) {
                            incFileContents = true;
                        }
                        if (!incFileContents) {
                            if (new_table != null) {
                                if (!new_table.getPasswords().isEmpty()) {
                                    pass_entered = true;
                                    pointer = 0;
                                    mainDisplay(true);
                                    pass_field.setText(new_table.getPasswords().get(0).getPass());
                                    url_field.setText(new_table.getPasswords().get(0).getUrl());
                                    login_field.setText(new_table.getPasswords().get(0).getLogin());
                                    table = new_table;
                                } else {
                                    pass_entered = true;
                                    pointer = 0;
                                    mainDisplay(true);
                                    label.setText("No entries yet");
                                }
                            } else {
                                label.setText("Incorrect password, please re-enter");
                            }
                        } else {
                            enter_new_entry = false;
                            mainMenuDisplay();
                            label.setVisible(true);
                            label.setText("Incompatible file format");
                        }
                    } else {
                        label.setText("Please enter password!");
                    }
                }
            } else {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    pass = pass_field.getText();
                    if (!pass.isEmpty()) {
                        List<Entry> passwords = new LinkedList<>();
                        byte[] salt_mock = null;
                        table = new Table(pass, salt_mock, passwords);
                        fileman.saveTable(currentf);
                        isSaved = fileman.saveTable(table);
                        if (!isSaved) {
                            new_master_pass = true;
                            label.setText("File write error!");
                        } else {
                            pointer = 0;
                            new_master_pass = false;
                            pass_entered = true;
                            mainDisplay(true);
                        }
                    } else {
                        label.setText("Please enter password!");
                    }
                }
            }
        } else {
        }
    }

    @FXML
    private void viewHandler(MouseEvent ev) {
        while (true) {
            if (ancor.getScene() != null) {
                stage = (Stage) ancor.getScene().getWindow();
                break;
            }
        }
        currentf = fchoose.showOpenDialog(stage);
        if (currentf != null) {
            table = fileman.getTable(currentf);
            if (table != null) {
                viewstor.setVisible(false);
                addpass.setVisible(false);
                label.setVisible(true);
                label.setText("Type your master pass and press Enter");
                pass_field.setEditable(true);
                pass_field.setVisible(true);
                pass_label.setVisible(true);
            }
        }
    }

    @FXML
    private void addpassHanlder() {
        pass_entered = false;
        new_master_pass = true;
        fchoose.setInitialFileName("example.ser");
        while (true) {
            if (ancor.getScene() != null) {
                stage = (Stage) ancor.getScene().getWindow();
                break;
            }
        }
        currentf = fchoose.showSaveDialog(stage);
        if (currentf != null) {
            viewstor.setVisible(false);
            addpass.setVisible(false);
            label.setVisible(true);
            label.setText("Type your master pass and press Enter");
            pass_field.setEditable(true);
            pass_field.setVisible(true);
            pass_label.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void mainDisplay(boolean visible) {
        label.setText("This is entry #" + (pointer + 1));
        controlsShow(visible);
        url_field.setVisible(visible);
        login_field.setVisible(visible);
        pass_field.setVisible(visible);
        url_field.setText("");
        login_field.setText("");
        pass_field.setText("");
        pass_field.setPromptText("");
        url_field.setPromptText("");
        login_field.setPromptText("");
        url_field.setEditable(!visible);
        login_field.setEditable(!visible);
        pass_field.setEditable(!visible);
        url_label.setVisible(visible);
        login_label.setVisible(visible);
        pass_label.setVisible(visible);
        ok.setText("Back to main menu");
        genrand.setText("Add new entry");
        pass_field.setText("");
        ancor.requestFocus();
    }

    private void newEntryDisplay() {
        mainDisplay(true);
        label.setText("Please enter new data");
        prev.setVisible(false);
        next.setVisible(false);
        delete.setVisible(false);
        url_field.setPromptText("www/email address");
        login_field.setPromptText("login");
        pass_field.setPromptText("password");
        url_field.setEditable(true);
        login_field.setEditable(true);
        pass_field.setEditable(true);
        genrand.setText("Generate random password");
        ok.setText("Save entry");
    }

    private void mainMenuDisplay() {
        pass_entered = false;
        pointer = 0;
        mainDisplay(false);
        label.setText("");
        addpass.setVisible(true);
        viewstor.setVisible(true);
    }

    private Table objectCopy(Table t) {
        Table temp_table = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(t);
            out.flush();
            out.close();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            temp_table = (Table) in.readObject();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return temp_table;
    }

    private void fieldsRefresh(String u, String l, String p) {
        label.setText("This is entry #" + (pointer + 1));
        url_field.setText(u);
        login_field.setText(l);
        pass_field.setText(p);
    }

    private void controlsShow(boolean b) {
        label.setVisible(b);
        prev.setVisible(b);
        next.setVisible(b);
        genrand.setVisible(b);
        ok.setVisible(b);
        delete.setVisible(b);
    }

    private void exit() {
        Platform.exit();
        System.exit(0);
    }
}
