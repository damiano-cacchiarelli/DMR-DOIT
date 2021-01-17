package it.unicam.dmr.doit.iscritto.ruolo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.security.jwt.JwtTokenFilter;

@SpringBootTest(properties = {
		"spring.datasource.url = jdbc:mysql://localhost:3306/testing?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false",
		"spring.jpa.hibernate.ddl-auto = create-drop" })
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ControllerProgettistaTest {

	@Autowired
	private MockMvc mvc;

	private static JwtDto jwtProponente;
	private static JwtDto jwtProgettista;

	@Test
	void testGestisciRichiestePartecipazione() throws Exception {
		startDataTest();
		jwtProponente = DoitTest.getTokenAccesso(mvc, "proponente", "pass");
		jwtProgettista = DoitTest.getTokenAccesso(mvc, "progettista", "pass");
		richiestaDiPartecipazione(mvc, "progettista", 1);
		//TODO
	}

	private void startDataTest() throws Exception {
		DoitTest.generateProgettista(mvc, "progettista", "email1@mail.it");
		DoitTest.generateProponente(mvc, "proponente", "email2@mail.it");
		jwtProponente = DoitTest.getTokenAccesso(mvc, "proponente", "pass");
		DoitTest.generateProgetto(mvc, jwtProponente);
				
	}

	private void richiestaDiPartecipazione(MockMvc mvc, String identificativoProgettista, int idProgetto)
			throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/invito/invia").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest.asJsonString(
						new InvitoDto("contenuto invito", TipologiaInvito.RICHIESTA, List.of(identificativoProgettista), idProgetto))))
				.andExpect(status().isOk());
	}

}
