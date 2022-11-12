package com.esprit.examen.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.junit.Assert.assertNotNull;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.DetailFournisseur;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.services.FournisseurServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class FournisseurTest{

@Mock
FournisseurRepository or;
@InjectMocks
FournisseurServiceImpl osI;

Fournisseur p = Fournisseur.builder().idFournisseur((long) 7).code("AB26").libelle("FOULEN").build();



@Test
public void AddFournisseur() {
Fournisseur p_add = new Fournisseur();
p_add.setLibelle("Foulen add");
p_add.setCode("AB26 add");

Mockito.when(or.save(ArgumentMatchers.any(Fournisseur.class))).thenReturn(p_add);

Fournisseur p_added = osI.addFournisseur(p_add);

assertEquals(p_add.getLibelle(), p_added.getLibelle());
assertEquals(p_add.getCode(), p_added.getCode());
verify(or).save(p_add);
}

@Test
public void RetrieveFournisseurById() {

Mockito.when(or.findById(Mockito.anyLong())).thenReturn(Optional.of(p));
Fournisseur p_get = osI.retrieveFournisseur((long) 7);
assertNotNull(p_get);
verify(or).findById(Mockito.anyLong());
}

@Test
public void RetrieveAll() {
List<Fournisseur> Fournisseurs = new ArrayList<>();
Fournisseurs.add(new Fournisseur());

when(or.findAll()).thenReturn(Fournisseurs);

List<Fournisseur> expected = osI.retrieveAllFournisseurs();

assertEquals(expected, Fournisseurs);
verify(or).findAll();
}

@Test
public void DeleteFournisseur_ifFound() {
Fournisseur p = new Fournisseur();
p.setLibelle("FOULEN delete");
p.setIdFournisseur(1L);


when(or.findById(p.getIdFournisseur())).thenReturn(Optional.of(p));

osI.deleteFournisseur(p.getIdFournisseur());
verify(or).deleteById(p.getIdFournisseur());
}

@Test
public void DeleteException_ifnotFound() {
try {
Fournisseur p = new Fournisseur();
p.setIdFournisseur(2L);
p.setLibelle("FOULEN");

when(or.findById(anyLong())).thenReturn(Optional.ofNullable(null));
osI.deleteFournisseur(p.getIdFournisseur());
} catch (Exception e) {
String expectedMessage = "entity with id";
String actualMessage = e.getMessage();

assertTrue(actualMessage.contains(expectedMessage));
}
}
/*
@Test
public void EditFournisseur_ifFound() {
	Fournisseur p_edit = new Fournisseur();
p_edit.setIdFournisseur(3L);
	p_edit.setLibelle("FOULEN edit");
	Fournisseur new_p_edit = new Fournisseur();
new_p_edit.setLibelle("new FOULEN edit");
DetailFournisseur df =new DetailFournisseur((long) 1,"foulen@gmail.com","nabeul","225sa");
new_p_edit.setDetailFournisseur(df);
when(or.findById(p_edit.getIdFournisseur())).thenReturn(Optional.of(p_edit));
p_edit = osI.updateFournisseur(new_p_edit);

verify(or).save(p_edit);
}

@Test
public void EditException_ifnotFound() {
try {
	Fournisseur p_edit = new Fournisseur();
	p_edit.setIdFournisseur(4L);
	p_edit.setLibelle("FOULEN edit");

Fournisseur new_p_edit = new Fournisseur();
new_p_edit.setIdFournisseur(5L);
new_p_edit.setLibelle("new FOULEN edit");

when(or.findById(anyLong())).thenReturn(Optional.ofNullable(null));
osI.updateFournisseur(new_p_edit);

} catch (Exception e) {
String expectedMessage = "entity with id";
String actualMessage = e.getMessage();

assertTrue(actualMessage.contains(expectedMessage));
}
}*/
}
