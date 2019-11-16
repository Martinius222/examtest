package no.kristiania.eksamenTest;

import java.util.Objects;

public class OperationMember {
    private long id;
    private String name;
    private String role; //private String mail;



    public OperationMember(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public OperationMember() {

    }


    public long getId() {
        return id;
    }


    public void setId(long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getRole() {

        return role;
    } // previously getMail

    public void setRole(String role) {

        this.role = role;
    } //previously setMail


    //public ProjectMember() {


    //}




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationMember member = (OperationMember) o;
        return id == member.id &&
                Objects.equals(name, member.name) &&
                Objects.equals(role, member.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role);
    }

    @Override
    public String toString() {
        return "OperationMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
