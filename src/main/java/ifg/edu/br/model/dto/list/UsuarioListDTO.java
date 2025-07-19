package ifg.edu.br.model.dto.list;

import ifg.edu.br.model.entity.TipoUsuario;

public class UsuarioListDTO {

    //Atributos
    private Integer id;
    private String nome;
    private String email;
    private TipoUsuario tipo;

    //Construtores
    public UsuarioListDTO() {
    }

    public UsuarioListDTO(Integer id, String nome, String email, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    //Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipo() { return tipo; }

    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
}
