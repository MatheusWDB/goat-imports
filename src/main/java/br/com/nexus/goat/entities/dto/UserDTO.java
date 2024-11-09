package br.com.nexus.goat.entities.dto;

import br.com.nexus.goat.entities.User;

public class UserDTO extends User {
    private static final long serialVersionUID = 1L;

    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (newPassword == null) {
            if (other.newPassword != null)
                return false;
        } else if (!newPassword.equals(other.newPassword))
            return false;
        return true;
    }
}
