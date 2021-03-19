public class Partido implements Comparable<Partido>{
    private int numero_partido;
    private int votos_legenda;
    private String nome_partido;
    private String sigla_partido;
    private int votos_nominais;
    private int votos_total;
    private int cand_eleitos;
    private Candidato candidatos[];

    public Partido(int numero_partido, int votos_legenda, String nome_partido, String sigla_partido){
        setNumero_partido(numero_partido);
        setVotos_legenda(votos_legenda);
        setNome_partido(nome_partido);
        setSigla_partido(sigla_partido);
        setVotos_total(0);
        setVotos_nominais(0);
        setCand_eleitos(0);
    }

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
    
    public boolean comparaPartido(int numeroId){
        if(this.numero_partido == numeroId){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Partido part) {
        if(this.votos_total > part.getVotos_total()){
            return -1;
        }
        else{
            return 1;
        }
    }
    
    public String toString(){
        return this.sigla_partido + " - " + this.numero_partido + ", " + this.getVotos_total() + " votos (" + this.getVotos_nominais() + " nominais e " + this.getVotos_legenda() + " de legenda) " + ", " + this.getCand_eleitos() +" candidatos eleitos";
    }
    
}
