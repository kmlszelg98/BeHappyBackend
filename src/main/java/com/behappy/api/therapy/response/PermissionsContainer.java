package com.behappy.api.therapy.response;

public class PermissionsContainer {

    private String role;
    private boolean creator;
    private boolean special;

    private PermissionsContainer(String role, boolean creator, boolean special) {
        this.role = role;
        this.creator = creator;
        this.special = special;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public static class Builder{
        private String role;
        private boolean creator;
        private boolean special;

        public Builder(){}

        public Builder role(String role){
            this.role = role;
            return this;
        }

        public Builder creator(boolean creator){
            this.creator = creator;
            return this;
        }

        public Builder special(boolean special){
            this.special = special;
            return this;
        }

        public PermissionsContainer createContainer(){
            return new PermissionsContainer(role, creator, special);
        }
    }
}
