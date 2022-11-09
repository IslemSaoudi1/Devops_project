package com.esprit.examen.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
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

import com.esprit.examen.entities.Produit;
import com.esprit.examen.repositories.ProduitRepository;
import com.esprit.examen.services.ProduitServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class Test_Class{

@Mock
ProduitRepository or;
@InjectMocks
ProduitServiceImpl osI;

Produit p = Produit.builder().idProduit((long) 7).libelleProduit("javel").codeProduit("adf8").build();

@Test
public void AddProduit() {
Produit p_add = new Produit();
p_add.setLibelleProduit("javel add");
p_add.setCodeProduit("adf8 add");

Mockito.when(or.save(ArgumentMatchers.any(Produit.class))).thenReturn(p_add);

Produit p_added = osI.addProduit(p_add);

assertEquals(p_add.getLibelleProduit(), p_added.getLibelleProduit());
assertEquals(p_add.getCodeProduit(), p_added.getCodeProduit());
verify(or).save(p_add);
}

@Test
public void RetrieveProduitById() {

Mockito.when(or.findById(Mockito.anyLong())).thenReturn(Optional.of(p));
Produit p_get = osI.retrieveProduit((long) 7);
assertNotNull(p_get);
verify(or).findById(Mockito.anyLong());
}

@Test
public void RetrieveAll() {
List<Produit> produits = new ArrayList<>();
produits.add(new Produit());

when(or.findAll()).thenReturn(produits);

List<Produit> expected = osI.retrieveAllProduits();

assertEquals(expected, produits);
verify(or).findAll();
}

@Test
public void DeleteProduit_ifFound() {
Produit p = new Produit();
p.setLibelleProduit("javel delete");
p.setIdProduit(1L);

when(or.findById(p.getIdProduit())).thenReturn(Optional.of(p));

osI.deleteProduit(p.getIdProduit());
verify(or).deleteById(p.getIdProduit());
}

@Test
public void DeleteException_ifnotFound() {
try {
Produit p = new Produit();
p.setIdProduit(2L);
p.setLibelleProduit("javeeel");

when(or.findById(anyLong())).thenReturn(Optional.ofNullable(null));
osI.deleteProduit(p.getIdProduit());
} catch (Exception e) {
String expectedMessage = "entity with id";
String actualMessage = e.getMessage();

assertTrue(actualMessage.contains(expectedMessage));
}
}

//@Test
//public void EditProduit_ifFound() {
//	Produit p_edit = new Produit();
//	p_edit.setIdProduit(3L);
//	p_edit.setLibelleProduit("javel edit");

//	Produit new_p_edit = new Produit();
//new_p_edit.setLibelleProduit("new javel edit");

//when(or.findById(p_edit.getIdProduit())).thenReturn(Optional.of(p_edit));
//p_edit = osI.updateProduit(new_p_edit);

//verify(or).save(p_edit);
//}

@Test
public void EditException_ifnotFound() {
try {
	Produit p_edit = new Produit();
	p_edit.setIdProduit(4L);
	p_edit.setLibelleProduit("javel edit");

Produit new_p_edit = new Produit();
new_p_edit.setIdProduit(5L);
new_p_edit.setLibelleProduit("new javel edit");

when(or.findById(anyLong())).thenReturn(Optional.ofNullable(null));
osI.updateProduit(new_p_edit);

} catch (Exception e) {
String expectedMessage = "entity with id";
String actualMessage = e.getMessage();

assertTrue(actualMessage.contains(expectedMessage));
}
}
}