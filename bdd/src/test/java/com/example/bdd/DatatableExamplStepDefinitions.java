package com.example.bdd;

import io.cucumber.datatable.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.*;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;

import static org.junit.jupiter.api.Assertions.*;

public class DatatableExamplStepDefinitions {

    List<UserInfo> userInfos;

    @Given("user info data table")
    public void userInfoDataTable(List<UserInfo> userInfos) {
//        List<List<String>> lists = dataTable.asLists();
//        List<Map<String, String>> maps = dataTable.asMaps();
        this.userInfos = userInfos;
    }

    record UserInfo(
            String username,
            String email,
            boolean secretPanelAccess,
            LocalDate firstLogin,
            int priorityLevel
    ) {
    }

    @DataTableType
    public UserInfo userInfoTransform(Map<String, String> entry) {
        return new UserInfo(
                entry.get("username"),
                entry.get("email"),
                Boolean.parseBoolean(entry.get("secret panel access")),
                LocalDate.parse(entry.get("first login date"), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                Integer.parseInt(entry.get("priority level"))
        );
    }

    @Then("only {int} users {string} and {string} has access to secret panel")
    public void onlyUsersAndHasAccessToSecretPanel(int numberOfUsers, String firstUser, String secondUser) {
        List<String> list = this.userInfos.stream()
                .filter(e -> e.secretPanelAccess)
                .map(e -> e.username)
                .toList();
        assertEquals(numberOfUsers, list.size());
        assertEquals(list, List.of(firstUser, secondUser));
    }

    @And("{string} has invalid mail")
    public void hasInvalidMail(String username) {

        String actual = this.userInfos.stream()
                .filter(e -> !isValidEmail(e.email))
                .map(e -> e.username)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found user"));
        assertEquals(username, actual);

    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }
}
