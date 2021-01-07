package fr;


import fr.model.account.Account;
import fr.model.traffic.Traffic;
import jdk.jfr.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountTest {

    @Autowired
    private StoreApi storeApi;

    @Autowired
    private TrafficApi trafficApi;

    @Autowired
    private Api api;

    @Autowired
    private AccountApi accountApi;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(accountApi).isNotNull();
    }

    @Test
    public void requestUsers() throws Exception{
        this.mockMvc.perform(get("/users/")).andExpect(status().isOk());
    }

    @Test
    public void createAccountTest() throws Exception {
        assertTrue(accountApi.createAccount("toto", "toto"));
        this.mockMvc.perform(get("/users/"))
            .andExpect(content().string(containsString("toto")))
            .andExpect(content().string(containsString("\"balanceAccount\":0")));
    }

    @Test
    public void connectionTest() throws Exception{
        accountApi.createAccount("Alice", "alice");
        String aliceId = (accountApi.connection("Alice", "alice")).toString();
        this.mockMvc.perform(get("/users/")).andExpect(content().string(containsString(aliceId)));
    }

    @Test
    public void createAccountAlreadyExists(){
        accountApi.createAccount("badAccount", "bad");
        assertFalse(accountApi.createAccount("badAccount", "bad"));
    }

    @Test
    public void connectionFailed(){
        assertEquals(accountApi.connection("doesntexist","nopaswd"), -1);
    }

    @Test
    public void balanceAccountTest(){
        String username = "testBalance";
        String password = "abcde";
        accountApi.createAccount(username, password);
        assertEquals(accountApi.getBalanceAccount(username, password), 0);
        double amountToAdd = 30;
        assertEquals(accountApi.incrementBalanceAccount(amountToAdd, username, password), amountToAdd);       
    }

}