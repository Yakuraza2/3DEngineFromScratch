package src.fr.rhumain.worldcraft;

public enum Models {

    BLOCK(new Model());

    Model model;
    Models(Model model){
        this.model = model;
    }

    public static void init(){
        BLOCK.model.addQuad(0, 0, 0, 1, -1, 0,  new Vector(0, 0, -1));
        BLOCK.model.addQuad(0, 0, 1, 1, -1, 1,  new Vector(0, 0, 1));
        BLOCK.model.addQuad(0, 0, 0, 0, -1, 1,  new Vector(-1, 0, 0));
        BLOCK.model.addQuad(1, 0, 0, 1, -1, 1,  new Vector(1, 0, 0));
        BLOCK.model.addQuad(0, -1, 0, 1, -1, 1, new Vector(0, -1, 0));
        BLOCK.model.addQuad(0, 0, 0, 1, 0, 1,   new Vector(0, 1, 0));
    }

    public Model get(){ return model; }
}
