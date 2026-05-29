package model;

public enum Livello {
    LIVELLO1(1,"Attraversi la foresta nebbiosa e sconfiggi un lupo selvatico." ),
    LIVELLO2(2,"Trovi una caverna oscura e affronti uno scheletro guerriero."),
    LIVELLO3(3,"Raggiungi un villaggio in rovina assediato dai banditi."),
    LIVELLO4(4,"Sali sulle montagne ghiacciate e combatti un troll di pietra."),
    LIVELLO5(5,"Affronti il Signore delle Ombre nel suo castello maledetto!");

    private final int id;
    private final String introduzione;

    Livello(int id, String introduzione) {
        this.id = id;
        this.introduzione = introduzione;
    }


    public String getIntroduzione(){
        return introduzione;
    }

    public int getId() {
        return id;
    }

}