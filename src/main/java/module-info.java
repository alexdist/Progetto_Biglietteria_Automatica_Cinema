module org.example.progetto_cinema {
  /*  requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.progetto_cinema to javafx.fxml;
    exports org.example.progetto_cinema;

    exports org.example.progetto_cinema.Sale to javafx.fxml;
    */

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens org.example.progetto_cinema to javafx.fxml;
    opens backend.Cinema_Infrastructure.Sala to javafx.base;
    opens backend.Cinema_Infrastructure.Film to javafx.base;
    exports org.example.progetto_cinema;
    exports org.example.progetto_cinema.Spettacoli;
    opens org.example.progetto_cinema.Spettacoli to javafx.fxml;
    exports org.example.progetto_cinema.Spettacoli.Utility_Classes;
    opens org.example.progetto_cinema.Spettacoli.Utility_Classes to javafx.fxml;
    exports org.example.progetto_cinema.Spettacoli.Service;
    opens org.example.progetto_cinema.Spettacoli.Service to javafx.fxml;
    exports org.example.progetto_cinema.Sale;
    opens org.example.progetto_cinema.Sale to javafx.fxml;
    exports org.example.progetto_cinema.Film;
    opens org.example.progetto_cinema.Film to javafx.fxml;
    exports org.example.progetto_cinema.General_Utility_Classes;
    opens org.example.progetto_cinema.General_Utility_Classes to javafx.fxml;
    // Apri il pacchetto a javafx.base
    opens org.example.progetto_cinema.Ricavi to javafx.base;
    exports org.example.progetto_cinema.Pagamento;
    opens org.example.progetto_cinema.Pagamento to javafx.fxml;
    exports org.example.progetto_cinema.Pagamento.Service;
    opens org.example.progetto_cinema.Pagamento.Service to javafx.fxml;
    exports org.example.progetto_cinema.Sale.Service;
    opens org.example.progetto_cinema.Sale.Service to javafx.fxml;
    exports org.example.progetto_cinema.Film.Service;
    opens org.example.progetto_cinema.Film.Service to javafx.fxml;

}


