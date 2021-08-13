module LifeSimRPG {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;

    opens org.dionthorn.lifesimrpg to javafx.graphics;
    exports org.dionthorn.lifesimrpg;
}