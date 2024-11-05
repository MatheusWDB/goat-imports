package br.com.nexus.goat.models.dto;

import br.com.nexus.goat.models.User;

public class UserDTO extends User {
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String toString() {
        return "UserDTO [newPassword=" + newPassword + ", getName()=" + getName() + ", getSurname()=" + getSurname()
                + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone() + ", getDateForBirth()="
                + getDateForBirth() + ", getPassword()=" + getPassword() + ", getCreatedAt()=" + getCreatedAt() + "]";
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
