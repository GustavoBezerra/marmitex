package com.les.marmitex.core.dominio;

import java.util.List;

public class Usuario extends EntidadeDominio {

	private String login;
	private String senha;
	private String telefone;
	private List<Endereco> endereco;

	public String getLogin() {
		if(login == null || login.equals("")){
			return null;
		}
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		if(senha == null || senha.equals("")){
			return null;
		}
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		if(telefone == null || telefone.equals("")){
			return null;
		}
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Endereco> getEndereco() {		
		if(endereco == null || endereco.isEmpty()){
			return null;
		}
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

	

}
