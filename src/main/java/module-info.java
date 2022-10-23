module com.example.criptoquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;



    requires org.bouncycastle.provider;
    requires org.apache.commons.lang3;
    requires java.desktop;
    opens com.example.criptoquiz to javafx.fxml;
    exports com.example.criptoquiz;
}