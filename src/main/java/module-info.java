module checkers
{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;

    opens src.ui to javafx.fxml;

    /**
     * Contains the checkers terminal user-interface alongside the graphical user-interface
     */
    exports src.ui;


    exports src.core;
}