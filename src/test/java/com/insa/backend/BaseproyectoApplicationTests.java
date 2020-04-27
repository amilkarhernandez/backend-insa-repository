package com.insa.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.insa.backend.dao.IIntegrantesDao;
import com.insa.backend.dao.IProyectoDao;
import com.insa.backend.models.Proyecto;
import com.insa.backend.services.IntegrantesServiceImp;
import com.insa.backend.services.ProyectoServiceImp;


@RunWith(SpringRunner.class)
@SpringBootTest
class BaseproyectoApplicationTests {

	//Inyectamos las dependencias, en este caso el Servicio
		@Autowired
		private ProyectoServiceImp proyectoServiceImp;
		
		@Autowired
		private IntegrantesServiceImp integranteServiceImp;
		
		//Se inyectan los Daos
		@MockBean
		private IProyectoDao proyectoDao;
		
		@MockBean
		private IIntegrantesDao integranteDao;
		
		// *** PRUEBAS PARA PROYECTOS **
		//Creamos el test para obtener el listado
			@Test
			public void getPryectosTest() {
				
				Proyecto proyecto = new Proyecto();
				proyecto.setId(Long.valueOf("1"));
				proyecto.setCodigoProyecto("64356-87655");
				proyecto.setProjNombre("Nueva Nombre de Proyecto");
				proyecto.setProjDescripcion("Nueva Descripcion Proyecto");
				proyecto.setTotalHoras(Long.valueOf("56"));
				proyecto.setCostoTotal(6658889.0);
				
				Proyecto proyecto2 = new Proyecto();
				proyecto2.setId(Long.valueOf("2"));
				proyecto2.setCodigoProyecto("99887-87676");
				proyecto2.setProjNombre("Nueva Nombre de Proyecto 2");
				proyecto2.setProjDescripcion("Nueva Descripcion Proyecto 2");
				proyecto2.setTotalHoras(Long.valueOf("34"));
				proyecto2.setCostoTotal(433455.0);
				
				when(proyectoDao.findAll()).thenReturn(Stream
						.of(proyecto, proyecto2).collect(Collectors.toList()));
			}
			
			@Test
			public void saveProyectoTest() {
				Proyecto proyecto = new Proyecto();
				proyecto.setId(Long.valueOf("1"));
				proyecto.setCodigoProyecto("64356-87655");
				proyecto.setProjNombre("Nueva Nombre de Proyecto");
				proyecto.setProjDescripcion("Nueva Descripcion Proyecto");
				proyecto.setTotalHoras(Long.valueOf("56"));
				proyecto.setCostoTotal(6658889.0);

				when(proyectoDao.save(proyecto)).thenReturn(proyecto);
				assertEquals(proyecto, proyectoServiceImp.save(proyecto));

			}
			
			@Test
			public void deleteProyectoByIdTest() {
				
				Proyecto proyecto = new Proyecto();
				proyecto.setId(Long.valueOf("1"));
				proyecto.setCodigoProyecto("64356-87655");
				proyecto.setProjNombre("Nueva Nombre de Proyecto");
				proyecto.setProjDescripcion("Nueva Descripcion Proyecto");
				proyecto.setTotalHoras(Long.valueOf("56"));
				proyecto.setCostoTotal(6658889.0);
				
				proyectoServiceImp.delete(proyecto.getId());
				verify(proyectoDao, times(1)).deleteById((proyecto.getId()));

			}

}
