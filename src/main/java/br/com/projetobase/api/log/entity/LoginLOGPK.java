package br.com.projetobase.api.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Felipe
 */
public class LoginLOGPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private Date data;

	public LoginLOGPK() {
		
	}
	
	public LoginLOGPK(String login, Date data) {
		super();
		this.login = login;
		this.data = data;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginLOGPK other = (LoginLOGPK) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	

}
