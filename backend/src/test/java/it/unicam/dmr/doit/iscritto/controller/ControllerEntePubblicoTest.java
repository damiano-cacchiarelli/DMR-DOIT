package it.unicam.dmr.doit.iscritto.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;

@SpringBootTest(properties = {
		"spring.datasource.url = jdbc:mysql://localhost:3306/testing?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false",
		"spring.jpa.hibernate.ddl-auto = create-drop" })
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ControllerEntePubblicoTest {

	@Autowired
	protected MockMvc mvc;

	@Test
	void testCrea() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new EnteDto("identif", "emailf3@al.it", "pass", "Italia", new Date()))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();
		// .andDo(MockMvcResultHandlers.print());

		mvc.perform(MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new EnteDto("identif", "emailf3@al.it", "pass", "Italia", new Date()))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest
						.asJsonString(new EnteDto("identifad", "emailf3@al.it", "pass", "Italia", new Date()))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new EnteDto("i", "emailnuovo@al.it", "pass", "Italia", new Date()))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
		mvc.perform(
				MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new EnteDto("iasdasdasdasddasdasasdasdasdsasd",
								"emailnuovo@al.it", "pass", "Italia", new Date()))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
	}

}
