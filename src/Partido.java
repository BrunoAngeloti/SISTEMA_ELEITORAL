package src;

public class Partido implements Comparable<Partido>{
    private int numero_partido;
    private int votos_legenda;
    private String nome_partido;
    private String sigla_partido;
    private int votos_nominais;
    private int votos_total;
    private int cand_eleitos;

    public Partido(int numero_partido, int votos_legenda, String nome_partido, String sigla_partido){
        setNumero_partido(numero_partido);
        setVotos_legenda(votos_legenda);
        setNome_partido(nome_partido);
        setSigla_partido(sigla_partido);
        setVotos_total(0);
        setVotos_nominais(0);
        setCand_eleitos(0);
    }

    // --------------------- MÉTODOS DE GET E SET ------------------------

    public int getNumero_partido() {
        return numero_partido;
    }
    public void setNumero_partido(int numero_partido) {
        this.numero_partido = numero_partido;
    }
    public int getVotos_legenda() {
        return votos_legenda;
    }
    public void setVotos_legenda(int votos_legenda) {
        this.votos_legenda = votos_legenda;
    }
    public String getNome_partido() {
        return nome_partido;
    }
    public void setNome_partido(String nome_partido) {
        this.nome_partido = nome_partido;
    }
    public String getSigla_partido() {
        return sigla_partido;
    }
    public void setSigla_partido(String sigla_partido) {
        this.sigla_partido = sigla_partido;
    }
    public int getVotos_total() {
        return votos_total;
    }
    public void setVotos_total(int votos_total) {
        this.votos_total = this.votos_nominais + this.votos_legenda;
    }
    public int getVotos_nominais() {
        return votos_nominais;
    }
    public void setVotos_nominais(int votos_nominais) {
        this.votos_nominais = votos_nominais;
    }
    public int getCand_eleitos() {
        return cand_eleitos;
    }
    public void setCand_eleitos(int cand_eleitos) {
        this.cand_eleitos = cand_eleitos;
    }

    //------------------- MÉTODO DE COMPARAÇÃO PELO NÚMERO DO PARTIDO ------------------
    
    public boolean comparaPartido(int numeroId){
        if(this.numero_partido == numeroId){
            return true;
        }
        return false;
    }

    //------------------- MÉTODO DE COMPARAÇÃO PARA O ARRAYS.SORT ------------------

    @Override
    public int compareTo(Partido part) {
        if(this.votos_total > part.getVotos_total()){
            return -1;
        }
        else if(this.votos_total < part.getVotos_total()){
            return 1;
        }else{
            if(this.numero_partido < part.getNumero_partido()){
                return -1;
            }else{
                return 1;
            }
        }
    }
    
    //------------------- MÉTODO DE IMPRESSÃO TOSTRING ------------------

    public String toString(){
        String print = this.sigla_partido + " - " + this.numero_partido + ", " + Integer.toString(this.getVotos_total());
        StringBuilder result = new StringBuilder(print);
          
        if(this.getVotos_total() <= 1){
           result.append(" voto ("); // caso seja no singular
        }
        else{
            result.append(" votos ("); // caso seja no plural
        }

        result.append(Integer.toString(this.getVotos_nominais()));
        if(this.getVotos_nominais() <= 1){
            result.append(" nominal e "); // caso seja no singular
        }
        else{
            result.append(" nominais e "); // caso seja no plural
        }

        result.append(Integer.toString(this.getVotos_legenda()) + " de legenda), " + Integer.toString(this.getCand_eleitos()));
        if(this.getCand_eleitos() <= 1){
            result.append(" candidato eleito"); // caso seja no singular
        }
        else{
            result.append(" candidatos eleitos"); // caso seja no plural
        }
        print = result.toString();
        return print;
    }
    
}
