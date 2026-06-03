package model;

public class Inventario {

    private Oggetto[] inventario;

    public Inventario(Oggetto[] inventario){
        this.inventario = inventario;
    }

    public Oggetto[] getInventario(){
        return inventario;
    }

    public void setInventario(Oggetto[] inventario){
        this.inventario = inventario;
    }

    //TODO
}