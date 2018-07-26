/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Eduardo
 * id INT PRIMARY KEY AUTO_INCREMENT,
id_wishlist INT,
id_usuario_shared INT
 */
public class Sharedwishlist {
    
    private int id;
    private int id_wishlist;
    private Wishlist wishlit;
    private int id_usuario;
    private Usuario usuario;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Usuario getUsuario_shared() {
        return usuario_shared;
    }

    public void setUsuario_shared(Usuario usuario_shared) {
        this.usuario_shared = usuario_shared;
    }
    private int id_usuario_shared;
    private Usuario usuario_shared;

    public Sharedwishlist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_wishlist() {
        return id_wishlist;
    }

    public void setId_wishlist(int id_wishlist) {
        this.id_wishlist = id_wishlist;
    }

    public Wishlist getWishlit() {
        return wishlit;
    }

    public void setWishlit(Wishlist wishlit) {
        this.wishlit = wishlit;
    }

    public int getId_usuario_shared() {
        return id_usuario_shared;
    }

    public void setId_usuario_shared(int id_usuario_shared) {
        this.id_usuario_shared = id_usuario_shared;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
