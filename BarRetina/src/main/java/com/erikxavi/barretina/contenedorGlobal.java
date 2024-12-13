package com.erikxavi.barretina;

public interface contenedorGlobal {
    String VARIABLE_GLOBAL = null;

    static void setVariableGlobal(String id){
        GlobalContainerHolder.globalIdComanda = id;
    }

    static String getVariableGlobal(){
        return GlobalContainerHolder.globalIdComanda;
    }

}

class GlobalContainerHolder {
    static String globalIdComanda;
}
