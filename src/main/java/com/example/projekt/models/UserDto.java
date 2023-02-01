package com.example.projekt.models;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    // UserDao

    private Long id;

    @NotEmpty(message = "Nazwa użytkownika nie może być pusta.")
    @Size(min = 2, max = 100, message = "Nazwa użytkownika musi zawierać od 2 do 100 znaków.")
    private String username;

    @NotEmpty(message = "Hasło nie może być puste.")
    @Size(min = 2, max = 255, message = "Hasło musi zawierać od 2 do 100 znaków.")
    private String password;

    @NotEmpty(message = "Imię nie może być puste.")
    @Size(min = 2, max = 100, message = "Imię musi zawierać od 2 do 100 znaków.")
    @Pattern(regexp = "([A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+)", message="Imię musi zaczynać się wielką literą, może zawierać jedynie duże/małe litery.")
    private String firstname;

    @NotEmpty(message = "Nazwisko nie może być puste.")
    @Size(min = 2, max = 100, message = "Nazwisko musi zawierać od 2 do 100 znaków.")
    @Pattern(regexp = "([A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłńóśźż]+)", message="Nazwisko musi zaczynać się wielką literą, może zawierać jedynie duże/małe litery.")
    private String lastname;

    @NotNull(message = "Wiek nie może być pusty.")
    @Min(value = 16, message = "Wiek nie powinien być niższy niż 16 lat.")
    @Max(value = 120, message = "Wiek nie powinien być wyższy niż 120 lat.")
    private Integer age;

    @NotEmpty(message = "Numer telefonu nie może być pusty.")
    @Size(min = 9, max = 9, message = "Numer telefonu powinien zawierać dokładnie 9 znaków.")
    @Pattern(regexp = "[0-9]{9}", message="Numer telefonu powinien zawierać tylko cyfry z przedziału 0-9 oraz być podany w następującym formacie: XXXXXXXXX.")
    private String phoneNumber;

    @NotEmpty(message = "Adres E-mail nie może być pusty.")
    @Size(min = 2, max = 255, message = "Adres E-mail musi zawierać od 2 do 255 znaków.")
    @Email(message = "Adres E-mail musi być poprawny zgodnie ze wzorem np. jantestowy@przykladowadomena.com.")
    private String emailAddress;

    private Boolean archived;

    // AddressDao

    private String street;

    @NotEmpty(message = "Numer domu nie może być pusty.")
    @Size(min = 1, max = 8, message = "Numer domu musi zawierać od 1 do 8 znaków.")
    private String apartmentNumber;

    @NotEmpty(message = "Miasto nie może być puste.")
    @Size(min = 2, max = 100, message = "Miasto musi zawierać od 2 do 100 znaków.")
    private String city;

    @NotEmpty(message = "Kod pocztowy nie może być pusty.")
    @Size(min = 6, max = 6, message = "Kod pocztowy musi zawierać dokładnie 6 znaków.")
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message="Kod pocztowy powinien zawierać tylko cyfry z przedziału 0-9 oraz być podany w następującym formacie: XX-XXX.")
    private String zipCode;

    @NotEmpty(message = "Państwo nie może być puste.")
    @Size(min = 2, max = 100, message = "Państwo musi zawierać od 2 do 100 znaków.")
    private String state;

    private Long roleId;
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
