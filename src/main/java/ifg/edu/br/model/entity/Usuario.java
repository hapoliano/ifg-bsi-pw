package ifg.edu.br.model.entity;

import ifg.edu.br.model.dto.UsuarioDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;
    @Transient
    private Boolean teste;


    //Construtores
    public Usuario() {
    }

    public Usuario(UsuarioDTO dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
        this.tipo = dto.getTipo();
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getTeste() {
        return teste;
    }

    public void setTeste(Boolean teste) {
        this.teste = teste;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}